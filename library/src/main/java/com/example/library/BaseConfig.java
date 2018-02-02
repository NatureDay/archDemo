package com.example.library;

/**
 * @Description 全局库的配置文件，子项目可以继承该类做扩展
 * @Author summer
 * @Date 2014年4月2日 上午10:53:02
 */
public class BaseConfig {

    public static final boolean DEBUG = true;

    public static final String TAG = "AntServer";

    /**
     * http请求状态码
     */
    public static final int STATUS_SUCCESS = 20000;
    public static final int STATUS_ERROR = 40000;
    public static final int STATUS_MESSAGE = 41000;

    public static final String HTTP_RESPONSE_CODE = "code";
    public static final String HTTP_RESPONSE_DATA = "data";
    public static final String HTTP_RESPONSE_MSG = "message";

    public static final int REQUEST_PERMISSIONS = 0x110;

    /**
     * app base url
     */
    public static final String BASEURL = "http://admintest.qmant.com/";
//    public static final String BASEURL = "http://peifeiserver.qmant.com/";
//    public static final String BASEURL = "http://192.168.5.36:9082/";

    /**
     * 分页请求默认page size
     */
    public static final String LABEL_PAGE = "page";
    public static final String LABEL_PAGE_SIZE = "pageSize";
    public static final int PAGE_SIZE = 20;
    public static final int PAGE_SIZE_HALF = 10;

    public static final String USER_ACCOUNT = "USER_ACCOUNT";
    public static final String PASSWORD = "PASSWORD";

    public static final String USER_TOKEN = "USER_TOKEN";

    public static final String FILE_PROVIDER = "com.qianmo.servers.library.fileprovider";
}
