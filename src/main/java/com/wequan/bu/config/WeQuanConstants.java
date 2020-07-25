package com.wequan.bu.config;

/**
 * @author ChrisChen
 */
public interface WeQuanConstants {

    /** Constants for web security **/
    String REGISTER_URL = "/user/register";
    String LOGIN_URL = "/user/login";
    String REFRESH_TOKEN_URL = "/access_token/refresh";
    String EMAIL_CONFIRM_URL = "/user/e-confirm";
    String STRIPE_CALLBACK_URL = "/connect/oauth";
    String STRIPE_WEBHOOK_URL = "/webhook";
    String STRIPE_REFUND_URL = "/refund_webhook";
    String STRIPE_ACCOUNT_URL = "/account_webhook";
    String STRIPE_TRANSFER_URL = "/transfer_webhook";
    String AUTH_HEADER = "Authorization";
    String TOKEN_PREFIX = "Bearer ";

    /** Constants for token **/
    String ACCESS_TOKEN_PREFIX_IN_REDIS = "jwt_access_token_u_";
    String REFRESH_TOKEN_PREFIX_IN_REDIS = "jwt_refresh_token_u_";

    /** Constants for cache **/
    String SCHOOL_CACHE_KEY = "Common_Data_School";
    String TAG_CACHE_KEY = "Common_Data_Tag";
    String SUBJECT_CACHE_KEY = "Common_Data_Subject";
    String TOPIC_CACHE_KEY = "Common_Data_Topic";
    String DEGREE_CACHE_KEY = "Common_Data_Degree";

    /** Constants for AWS S3 **/
    String S3_USER_PATH = "user/";
}
