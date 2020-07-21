package com.wequan.bu.security.component;

import com.wequan.bu.config.properties.AppProperties;
import com.wequan.bu.controller.vo.Token;
import com.wequan.bu.security.AppUserDetails;
import com.wequan.bu.security.authentication.token.EmailPasswordAuthenticationToken;
import com.wequan.bu.security.authentication.token.UserNamePasswordAuthenticationToken;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author ChrisChen
 */
@Component
public class TokenProvider {

    private static final Logger log = LoggerFactory.getLogger(TokenProvider.class);

    private AppProperties appProperties;

    public TokenProvider(AppProperties appProperties) {
        this.appProperties = appProperties;
    }

    public Token createToken(Authentication authentication) {
        String userId;
        if (authentication instanceof EmailPasswordAuthenticationToken
                || authentication instanceof UserNamePasswordAuthenticationToken) {
            userId = authentication.getPrincipal().toString();
        } else {
            AppUserDetails appUserDetails = (AppUserDetails) authentication.getPrincipal();
            userId = appUserDetails.getId().toString();
        }
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + appProperties.getAuth().getTokenExpirationMsec());
        String token = Jwts.builder()
                            .setSubject(userId)
                            .setIssuedAt(now)
                            .setExpiration(expiryDate)
                            .signWith(SignatureAlgorithm.HS512, appProperties.getAuth().getTokenSecret())
                            .compact();
        return Token.builder().token(token).subject(userId).issueDate(now).expiryDate(expiryDate).build();
    }

    public Token createRefreshToken(Authentication authentication) {
        String userId = authentication.getPrincipal().toString();
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + appProperties.getAuth().getRefreshTokenExpirationMsec());
        String token = Jwts.builder()
                            .setSubject(userId)
                            .setIssuedAt(now)
                            .setExpiration(expiryDate)
                            .signWith(SignatureAlgorithm.HS256, appProperties.getAuth().getRefreshTokenSecret())
                            .compact();
        return Token.builder().token(token).subject(userId).issueDate(now).expiryDate(expiryDate).build();
    }

    public String getUserIdFromToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(appProperties.getAuth().getTokenSecret())
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

    public boolean validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(appProperties.getAuth().getTokenSecret()).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException ex) {
            log.error("Invalid JWT signature");
        } catch (MalformedJwtException ex) {
            log.error("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            log.error("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            log.error("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            log.error("JWT claims string is empty.");
        }
        return false;
    }

}
