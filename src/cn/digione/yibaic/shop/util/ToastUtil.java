package cn.digione.yibaic.shop.util;

import android.content.Context;
import android.widget.Toast;
import cn.digione.yibaic.shop.ShopApp;

/**
 * Created with IntelliJ IDEA.
 * User: youzh
 * Date: 14-2-25
 * Time: 下午4:59
 */
public class ToastUtil {

    private static Toast lastToast = null;

    public final static  int LENGTH_SHORT = Toast.LENGTH_SHORT;
    public final static  int LENGTH_LONG = Toast.LENGTH_LONG;

    public static void clear() {
        if (lastToast != null) {
            lastToast.cancel();
        }
    }

    public static void show(int i) {
        show(ShopApp.getInstance().getContext(), i);
    }

    public static void show(Context context, int i) {
        show(context, context.getString(i), 0);
    }

    public static void show(Context context, int i, int j) {
        show(context, context.getString(i), j);
    }

    public static void show(Context context, CharSequence charsequence) {
        show(context, charsequence, 0);
    }

    public static void show(Context context, CharSequence charsequence, int i) {
        if (lastToast != null) {
            lastToast.cancel();
        }
        Toast toast = Toast.makeText(context, charsequence, i);
        lastToast = toast;
        toast.show();
    }

    public static void show(CharSequence charsequence) {
        show(ShopApp.getInstance().getContext(), charsequence);
    }


}