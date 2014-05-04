package cn.digione.yibaic.shop.util;


import cn.digione.yibaic.shop.R;
import cn.digione.yibaic.shop.ShopApp;
import cn.digione.yibaic.shop.common.Constants;

/**
 * Created with IntelliJ IDEA.
 * User: youzh
 * Date: 13-12-12
 * Time: 下午4:16
 */
public final class Log {
    //是否打印Log
    private static Boolean isEnable = true;

    static {
        String debug = ShopApp.getInstance().getResources().getString(R.string.debug);
        if (debug.equalsIgnoreCase("true")) {
            isEnable = true;
        } else {
            isEnable = false;
        }
    }


    public static Boolean getEnable() {
        return isEnable;
    }

    public static void setEnable(Boolean enable) {
        isEnable = enable;
    }

    public static void v(String msg) {
        if (isEnable) {
            android.util.Log.v(Constants.TAG, msg);
        }
    }


    public static void v(String msg, Throwable tr) {
        if (isEnable) {
            android.util.Log.v(Constants.TAG, msg, tr);
        }
    }


    public static void d(String msg) {
        if (isEnable) {
            android.util.Log.d(Constants.TAG, msg);
        }
    }


    public static void d(String msg, Throwable tr) {
        if (isEnable) {
            android.util.Log.d(Constants.TAG, msg, tr);
        }
    }


    public static void i(String msg) {
        if (isEnable) {
            android.util.Log.i(Constants.TAG, msg);
        }
    }

    public static void i(String msg, Throwable tr) {
        if (isEnable) {
            android.util.Log.i(Constants.TAG, msg, tr);
        }
    }


    public static void w(String msg) {
        if (isEnable) {
            android.util.Log.w(Constants.TAG, msg);
        }
    }


    public static void w(String msg, Throwable tr) {
        if (isEnable) {
            android.util.Log.w(Constants.TAG, msg, tr);
        }
    }

    public static void e(String msg) {
        if (isEnable) {
            android.util.Log.e(Constants.TAG, msg);
        }
    }


    public static void e(String msg, Throwable tr) {
        if (isEnable) {
            android.util.Log.e(Constants.TAG, msg, tr);
        }
    }
}
