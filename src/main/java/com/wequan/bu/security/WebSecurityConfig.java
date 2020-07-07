package com.wequan.bu.security;

import com.wequan.bu.config.WeQuanConstants;
import com.wequan.bu.security.authentication.provider.EmailPasswordAuthenticationProvider;
import com.wequan.bu.security.authentication.provider.UserNamePasswordAuthenticationProvider;
import com.wequan.bu.security.component.RestApiWebSecurity;
import com.wequan.bu.security.component.TokenAuthenticationEntryPoint;
import com.wequan.bu.security.filter.TokenAuthenticationFilter;
import com.wequan.bu.security.oauth2.CustomOAuth2UserService;
import com.wequan.bu.security.oauth2.HttpCookieOAuth2AuthorizationRequestRepository;
import com.wequan.bu.security.oauth2.OAuth2AuthenticationFailureHandler;
import com.wequan.bu.security.oauth2.OAuth2AuthenticationSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;

/**
 * @author ChrisChen
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String DEV_PROFILE_NAME = "dev";

    @Value("${spring.profiles.active}")
    private String activeProfile;
    @Autowired
    private TokenAuthenticationEntryPoint tokenAuthenticationEntryPoint;
    @Autowired
    private TokenAuthenticationFilter tokenAuthenticationFilter;
    @Autowired
    private CustomOAuth2UserService customOAuth2UserService;
    @Autowired
    private OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;
    @Autowired
    private OAuth2AuthenticationFailureHandler oAuth2AuthenticationFailureHandler;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .authenticationProvider(emailPasswordAuthenticationProvider())
                .authenticationProvider(userNamePasswordAuthenticationProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        if (!DEV_PROFILE_NAME.equals(activeProfile)) {
            http
                    .cors()
                        .and()
                    .csrf()
                        .disable()
                    .formLogin()
                        .disable()
                    .httpBasic()
                        .disable()
                    .exceptionHandling()
                        .authenticationEntryPoint(tokenAuthenticationEntryPoint)
                        .and()
                    .authorizeRequests()
                        .antMatchers("/",
                                "/favicon.ico",
                                "/**/*.png",
                                "/**/*.gif",
                                "/**/*.svg",
                                "/**/*.jpg",
                                "/**/*.html",
                                "/**/*.css",
                                "/**/*.js").permitAll()
                        .antMatchers(HttpMethod.POST, WeQuanConstants.REGISTER_URL, WeQuanConstants.LOGIN_URL).permitAll()
                        .antMatchers(WeQuanConstants.EMAIL_CONFIRM_URL, "/oauth2/**").permitAll()
                        .anyRequest()
                            .authenticated()
                        .and()
                    .oauth2Login()
                        .authorizationEndpoint()
                            .baseUri("/oauth2/authorize")
                            .authorizationRequestRepository(cookieAuthorizationRequestRepository())
                            .and()
                        .redirectionEndpoint()
                            .baseUri("/oauth2/callback/*")
                            .and()
                        .userInfoEndpoint()
                            .userService(customOAuth2UserService)
                            .and()
                        .successHandler(oAuth2AuthenticationSuccessHandler)
                        .failureHandler(oAuth2AuthenticationFailureHandler)
                        .and()
                    .addFilterBefore(tokenAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        } else {
            http
                    .cors()
                        .and()
                    .csrf()
                        .disable()
                    .formLogin()
                        .disable()
                    .httpBasic()
                        .disable()
                    .exceptionHandling()
                        .authenticationEntryPoint(tokenAuthenticationEntryPoint)
                        .and()
                    .authorizeRequests()
                    .antMatchers(HttpMethod.GET, "/user/{id}/*").access("@restApiWebSecurity.checkUserId(authentication, request, #id)")
                    .anyRequest()
                        .permitAll()
                        .and()
                    .oauth2Login()
                        .authorizationEndpoint()
                            .baseUri("/oauth2/authorize")
                            .authorizationRequestRepository(cookieAuthorizationRequestRepository())
                            .and()
                        .redirectionEndpoint()
                            .baseUri("/oauth2/callback/*")
                            .and()
                        .userInfoEndpoint()
                            .userService(customOAuth2UserService)
                            .and()
                        .successHandler(oAuth2AuthenticationSuccessHandler)
                        .failureHandler(oAuth2AuthenticationFailureHandler)
                        .and()
                    .addFilterBefore(tokenAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        }
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public UserNamePasswordAuthenticationProvider userNamePasswordAuthenticationProvider() {
        return new UserNamePasswordAuthenticationProvider();
    }

    @Bean
    public EmailPasswordAuthenticationProvider emailPasswordAuthenticationProvider() {
        return new EmailPasswordAuthenticationProvider();
    }

    @Bean
    public RestApiWebSecurity restApiWebSecurity() {
        return new RestApiWebSecurity();
    }

    @Bean
    public HttpCookieOAuth2AuthorizationRequestRepository cookieAuthorizationRequestRepository() {
        return new HttpCookieOAuth2AuthorizationRequestRepository();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Collections.singletonList("*"));
        configuration.setAllowedMethods(Arrays.asList("OPTIONS", "GET", "POST", "PUT", "DELETE"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}