package com.wequan.bu.config;

/**
 * @author ChrisChen
 */
public interface WeQuanConstants {

    /** Constants for web security **/
    String REGISTER_URL = "/user/register";
    String LOGIN_URL = "/user/login";
    String EMAIL_CONFIRM_URL = "/user/e-confirm";
    String AUTH_HEADER = "Authorization";
    String TOKEN_PREFIX = "Bearer ";

    /** Constants for cache **/
    String SCHOOL_CACHE_KEY = "Common_Data_School";
    String TAG_CACHE_KEY = "Common_Data_Tag";
    String SUBJECT_CACHE_KEY = "Common_Data_Subject";
    String TOPIC_CACHE_KEY = "Common_Data_Topic";
}
