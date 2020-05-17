package com.quanzi.bu.config;

/**
 * @author ChrisChen
 */
public interface QuanZiConstants {

    String jwtSecret = "";

    /** Constants for web security **/
    String REGISTER_URL = "/user/register";
    String LOGIN_URL = "/user/login";
    String AUTH_HEADER = "Authorization";
    String TOKEN_PREFIX = "Bearer ";
    String SECRET_KEY = "SecretKey_Quanzi";
}
