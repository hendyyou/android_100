package cn.digione.yibaic.shop.util;

import android.content.Context;
import android.os.Build;
import android.telephony.TelephonyManager;

import java.util.Locale;

/**
 * Created with IntelliJ IDEA.
 * User: youzh
 * Date: 14-2-21
 * Time: 下午6:24
 */
public class Device {
    public static String BOARD;
    public static String BRAND;
    public static String BUILD_TYPE;
    public static String CARRIER;
    public static String CHANNEL_ID;
    public static String COUNTRY;
    public static String DEVICE;
    public static int DISPLAY_DENSITY = 0;
    public static int DISPLAY_HEIGHT = 0;
    public static String DISPLAY_RESOLUTION;
    public static int DISPLAY_WIDTH = 0;
    public static String HARDWARE;
    public static String IMEI;
    public static boolean IS_MIUI = false;
    public static boolean IS_NEW_USER = false;
    public static boolean IS_SYSTEM_SHOP = false;
    private static final String KEY_INSTALL_TIME = "installTime";
    public static String LANGUAGE;
    public static String MANUFACTURER;
    public static String MODEL;
    private static final long NEW_USER_TIME = 604800000L;
    public static String PACKAGE;
    public static String PRODUCT;
    public static String RELEASE;
    public static int SDK_VERSION = 0;
    public static int SHOP_VERSION = 0;
    public static String SHOP_VERSION_STRING;
    public static String SYSTEM_VERSION;
    private static final String TAG = "device";
    public static String UUID;

    private static void acquireSystemInfo(Context paramContext)
    {
        MODEL = Build.MODEL;
        DEVICE = Build.DEVICE;
        PRODUCT = Build.PRODUCT;
        BOARD = Build.BOARD;
        HARDWARE = Build.HARDWARE;
        MANUFACTURER = Build.MANUFACTURER;
        BRAND = Build.BRAND;
        BUILD_TYPE = Build.TYPE;
        RELEASE = Build.VERSION.RELEASE;
        SYSTEM_VERSION = Build.VERSION.INCREMENTAL;
        SDK_VERSION = Build.VERSION.SDK_INT;
        //IS_MIUI = isMiui(paramContext);
    }
    private static void acquireUserInfo(Context paramContext)
    {
        COUNTRY = Locale.getDefault().getCountry();
        LANGUAGE = Locale.getDefault().getLanguage();
        CARRIER = ((TelephonyManager)paramContext.getSystemService(Context.TELEPHONY_SERVICE)).getSimOperator();
    }
    public static String getClientInfoHash()
    {
        return "";
    }
    public static String getFullInfo()
    {
        return DISPLAY_RESOLUTION + DISPLAY_WIDTH + DISPLAY_HEIGHT + DISPLAY_DENSITY + MODEL + DEVICE + PRODUCT + BOARD + HARDWARE + MANUFACTURER + BRAND + BUILD_TYPE + SDK_VERSION + SYSTEM_VERSION + RELEASE + IS_MIUI + SHOP_VERSION + SHOP_VERSION_STRING + IS_SYSTEM_SHOP + COUNTRY + LANGUAGE + CARRIER + UUID + IMEI + CHANNEL_ID;
    }
}
