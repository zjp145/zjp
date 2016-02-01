package com.zhang.sqone;

import android.content.Context;

/**
 * 全局变量类  静态常量
 */

public class Globals {

    public static int maxDisplaySize, screenWidth, screenHeight,thumbnailMaxVal, thumbnailMinVal;
    public static Context context; // 数据上下文
    public static final String SPLASH_USERTYPE = "USERTYPE";
    public static final String DY_ID = "lm";// 社区子模块类型的id
    public static final String DY_LC = "lc";//社区子模块类型的名字
    public static final String DY_XG = "xg";//社区子模块是否显示图片
    public static final String WEB_URL = "url";
    public static final String KEY_URL = "par";
    public static final String ID_URL = "id";
    public static final String BM_URL = "bm";
    public static final String TITLE_URL = "title";
    public static final String LX_LX = "mlx";
    public static final String SHARED_LOCA = "SHARED_LOCA";
    public static final String SHARED_LOCA_CODE = "SHARED_LOCA_CODE";
    public static final String USER_PHONE = "userPhoene";
    public static final String USER_PASSWORD = "userpassword";
    public static final String USER_YD="userdl";
    public static final String USER_SH = "usersh";
    public static final String USER_NC = "usernc";
    public static String USER_TYPE = ""; // 账号类型 q表示求职者 z表示招聘者
    public static String LOCA = "北京市"; // 二级地区
    public static String LOCACODE = "110100"; // 二级地区代码
    public static final String PREFS = "PREFS";
    public static final String LOG_TAG = "RESPONSE";
    public static final String LOG_TAG2 = "RESPONSE2";

//    public static final String WS_URI = "http://192.168.1.161:8099/niuls/Index";
//    public static final String WS_URIT = "http://192.168.1.161:8099/niuls/Photo";
//    public static final String VERSION_XML = "http://192.168.1.161:8099/niuls/SoftUpdate/Update.xml";

//    public static final String WS_URI = "http://192.168.1.161:8088/niuls/Index";
//    public static final String WS_URIT = "http://192.168.1.161:8088/niuls/Photo";
//    public static final String VERSION_XML = "http://192.168.1.161:8088/niuls/SoftUpdate/Update.xml";

//    public static final String WS_URI = "http://192.168.1.161:8077/niuls/Index";
//    public static final String WS_URIT = "http://192.168.1.161:8099/niuls/Photo";
//    public static final String VERSION_XML = "http://192.168.1.161:8077/niuls/SoftUpdate/Update.xml";


//    public static final String WS_URI = "http://192.168.1.161:8090/niuls/Index";
//    public static final String WS_URIT = "http://192.168.1.161:8090/niuls/Photo";
//    public static final String VERSION_XML = "http://192.168.1.161:8090/niuls/SoftUpdate/Update.xml";
//    public static final String WS_URI_POTO = "http://192.168.1.161:8280/zhaoshi/Photo";
//    public static final String HELP = "http://192.168.1.161:8280/zhaoshi/app/help.html";

    public static final String WS_URI = "http://www.zhaoshi365.com/Index";
    public static final String WS_URI_POTO = "http://www.zhaoshi365.com/Photo";
    public static final String VERSION_XML = "http://www.zhaoshi365.com/SoftUpdate/Update.xml";
    public static final String HELP = "http://www.zhaoshi365.com/app/help.html";

    public static final String WS_POST_KEY = "id";
    public static final String SH_ID = "sqid";
    public static final String MIME_HTML = "text/html";
    public static final String ENCODING_UTF8 = "utf-8";
    public static final String EMPTY = "";
    public static final String DATE_FORMAT = "yyyy-MM-dd";
    public static final String JOB = "J";
    public static final String PROJECT_ID = "PID";
    public static final String LOCATION = "L";
    public static final String K_TYPE = "KTYPE";
    public static final String K_ID = "KID";
    public static final String MSG_QUERY_TITLE = "正在查询";
    public static final String MSG_QUERY_INFO = "请等待查询结果。";
    public static final String JSON_MODE_JOB = "JOB";
    public static final String JSON_MODE_ZZJX = "ZZJX";
    public static final String JSON_MODE_YJWC = "YJWC";
    public static final String JSON_MODE_SQXM = "SQXM";
    public static final String JSON_MODE_GZ = "GZ";


    public static final String PERSON_MODE_DETAIL = "DETAIL";
    public static final String PERSON_MODE_INFO = "INFO";
    public static final long TIME_COUNT = 1000;
    public static final String DL_PID = "5b96a815ca3a64f50f28ac2f4820c65d";
    public static final String MONEY = "chuanxisoft";
    public static Boolean FROM = false;
    public static Boolean FROMSELLOCA = false;

    /* Ver3 */
    public static final int TOAST_LONG = 1;
    public static final int TOAST_SHORT = 0;
    public static final String MD5 = "siqian";
    public static final String SER_ERROR = "服务器端发生异常，请稍后重试!";
    public static final String CACHE_FILE_NAME = "MyImg.png";
    public static final String BAIDU_MAP_KEY = "6yXylrBdaevLtg1okRCkiTkG";
    public static final String SDPATH = "qsz"; // 全身照
    public static final int QSZ = 3; // 全身照张数
    public static final String FILE_NAME_PREFIX = "-++++";
    public static final String WLCW = "网络错误";

    // intent
    public static final String AN_INTERVIEW = "inviteID";
    public static final String MAP_BAIDU_ADDRESS = "addressMap";
    public static final int COUNT = 50;
    //

    public static final Float ALPHA = 0.7f;
}
