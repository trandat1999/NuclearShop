package com.tranhuudat.nuclearshop.util;

public class ConstUtil {
    //system_role
    public static final String ADMIN_ROLE = "ADMIN";
    public static final String SALE_ROLE = "SALE";
    public static final String STAFF_WAREHOUSE_ROLE = "STAFF_WAREHOUSE";
    public static final String SHIPPER_ROLE = "SHIPPER";
    public static final String ACCOUNTANT_ROLE = "ACCOUNTANT";
    public static final String USER_ROLE = "USER";
    //system_information
    public static final String ADMIN_USERNAME = "admin";
    public static final String ADMIN_PASSWORD = "admin";
    public static final String ADMIN_EMAIL = "nuclearshop123@gmail.com";
    //system_properties
    public static final String PATH_FILE_IMAGE_PROPERTY = "file.path.image";
    //regex
    public static final String REGEX_PHONE_NUMBER = "((84|0|'+'84)[3|5|7|8|9])+([0-9]{8})";
    public static int TIME_MINUTES_EXPIRED_VERIFICATION_TOKEN = 5;
    public static String HOST_URL = "http://localhost:8080";
    public static String API = "api";
    public static String SLASH = "/";
    public static String URL_VERIFICATION_TOKEN = "auth/accountVerification";
    public static long TIMEOUT_TOKEN = 300L;
    public static long TIMEOUT_REFRESH_TOKEN = 1800L;
    public static String FILE_PATH_IMAGE = "";
}
