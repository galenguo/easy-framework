package com.efun.core.context;

/**
 * <p>Constants
 * <p>define some contants name, such as servlet context path.
 *
 * @author Galen
 * @since 2016/05/30
 */
public abstract class Constants {

    /**
     * context path（系统逻辑根地址，例如：efun-web）
     */
    public static final String CONTEXT_PATH = "ctx";

    /**
     * resource path（存放js和css的地址）
     */
    public static final String RESOURCE_PATH = "rsc";

    /**
     * configuration context（配置上下文）
     */
    public static final String CONFIG_CONTEXT = "cfg";

    /**
     * app version
     */
    public static final String APP_VERSION = "appVersion";

    /**
     * application environment
     */
    public static final String APP_EVN = "appEvn";

    /**
     * host name
     */
    public static final String HOST = "host";

    public static final String USERNAME = "username";

    public static final String PASSWORD = "password";

    public static final String URL = "url";

    /**
     * separator comma
     */
    public static final String SEPARATOR_COMMA = ",";

    /**
     * separator colon
     */
    public static final String SEPARATOR_COLON= ":";

    /**
     * separator underline
     */
    public static final String SEPARATOR_UNDERLINE= "_";

    /**
     * default pattern time
     */
    public static final String DEFAULT_PATTERN_TIME = "hh:mm:ss";

    /**
     * default pattern date
     */
    public static final String DEFAULT_PATTERN_DATE = "yyyy-MM-dd";

    /**
     * default pattern date time
     */
    public static final String DEFAULT_PATTERN_DATETIME = "yyyy-MM-dd hh:mm:ss";

    /**
     * default pattern date timestamp
     */
    public static final String DEFAULT_PATTERN_DATETIMESTAMP = "yyyy-MM-dd hh:mm:ss.SSS";

    /**
     * default encoding
     */
    public static final String ENCODING = "UTF-8";

    /**
     * current user
     */
    public static final String CURRENT_USER = "currentUser";

    /**
     * current user name
     */
    public static final String CURRENT_USER_NAME = "currentUserName";

    /**
     * previous page url
     */
    public static final String BACK_URL = "backURL";

    /**
     * current page url
     */
    public static final String CURRENT_URL = "currentURL";

    public static final String DEFAULT_LOG_LEVEL = "info";

}
