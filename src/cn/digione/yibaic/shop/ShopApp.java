package cn.digione.yibaic.shop;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import cn.digione.yibaic.shop.bean.UserBean;
import cn.digione.yibaic.shop.http.DisplayImageOptionsForCustom;
import cn.digione.yibaic.shop.util.Log;
import cn.digione.yibaic.shop.widget.CustomProgressDialog;
import com.baidu.api.Baidu;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.Stack;

/**
 * Created with IntelliJ IDEA. User: youzh Date: 14-2-24 Time: 上午11:04
 */
public class ShopApp extends Application {
	private static ShopApp instanceApp;
	private static int dialogCount;
    private static Stack<Activity> activityStack;
    private Context appContext;
    private boolean isCookiesInited;
    private CustomProgressDialog dialog;
	private Baidu baidu = null;
	// 是否登录标识
	// private boolean isLogin;
	// 用户
	private UserBean user;

	public ShopApp() {

	}

	public static ShopApp getInstance() {
		if (instanceApp == null) {
			instanceApp = new ShopApp();
		}
		return instanceApp;
	}

	public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            if (null != listItem) {
                listItem.measure(0, 0);
                totalHeight += listItem.getMeasuredHeight();
            }
        }
        totalHeight += totalHeight / listAdapter.getCount(); //结果值偏小，补充一个单元高度
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        if (null != params) {
            params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
            listView.setLayoutParams(params);
        }
    }

    public static void initImageLoader(Context context) {
        // 获取应用缓存大小
        int memClass = ((ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE)).getMemoryClass();
        memClass = memClass > 32 ? 32 : memClass;
        // 使用可用内存的1/8作为图片缓存
        final int cacheSize = 1024 * 1024 * memClass / 8;
        Log.d("memClass:" + memClass + ",cacheSize=" + cacheSize);
        DisplayImageOptions defaultOptions = DisplayImageOptionsForCustom.getDisplayImageOptions();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context).denyCacheImageMultipleSizesInMemory()
                                                                                       .threadPoolSize(memClass / 3)
                                                                                       .discCacheFileNameGenerator(
                                                                                               new Md5FileNameGenerator())
                                                                                       .memoryCache(new WeakMemoryCache())
                                                                                       .memoryCacheSize(cacheSize)
                                                                                       .defaultDisplayImageOptions(
                                                                                               defaultOptions).build();

        ImageLoader.getInstance().init(config);
    }

	/**
     * 显示进度条
     *
     * @param context
     */

    public void showDialog(Context context) {

        if (dialog == null || !dialog.isShowing()) {
            dialog = new CustomProgressDialog(context, R.style.CustomProgressDialog);
            dialog.setCancelable(false);
            dialog.show();
        }
        while (!dialog.isShowing()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        dialogCount++;

	}

    /**
     * 关闭所有进度条
     */
    public void dismissDialog() {
        dialogCount = 0;
        if (dialog != null) {
        	try {
        		 dialog.dismiss();
			} catch (Exception e) {
				// TODO: handle exception
			}           
            dialog = null;
        }
    }

    /**
     * 关闭一个进度条
     */
    public void dismissSingleDialog() {
        dialogCount--;
        if (dialogCount <= 0 && dialog != null) {
            dialog.dismiss();
            dialog = null;
        }
    }

    public Context getContext() {
        return appContext;
    }

    public int getMaxMemory() {
        return (int) (Runtime.getRuntime().maxMemory() / 1024);
    }

    public void onCreate() {
        super.onCreate();
        appContext = getApplicationContext();
        instanceApp = this;
        initImageLoader(getApplicationContext());
    }

	public UserBean getUser() {
		return user;
	}

	public void setUser(final UserBean user) {
		this.user = user;
	}

    public void exitApp() {
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }

	public Baidu getBaidu() {
		return baidu;
	}

	public void setBaidu(Baidu baidu) {
		this.baidu = baidu;
    }

    /**
     * 添加Activity到堆栈
     */
    public void addActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new Stack<Activity>();
        }
        activityStack.add(activity);
    }

    /**
     * 获取当前Activity（堆栈中最后一个压入的）
     */
    public Activity currentActivity() {
        Activity activity = activityStack.lastElement();
        return activity;
    }

    /**
     * 结束当前Activity（堆栈中最后一个压入的）
     */
    public void finishActivity() {
        Activity activity = activityStack.lastElement();
        finishActivity(activity);
    }

    /**
     * 结束指定的Activity
     */
    public void finishActivity(Activity activity) {
        if (activity != null) {
            activityStack.remove(activity);
            activity.finish();
            activity = null;
        }
    }

    /**
     * 结束指定类名的Activity
     */
    public void finishActivity(Class<?> cls) {
        for (Activity activity : activityStack) {
            if (activity.getClass().equals(cls)) {
                finishActivity(activity);
            }
        }
    }

    /**
     * 结束所有Activity
     */
    public void finishAllActivity() {
        for (int i = 0, size = activityStack.size(); i < size; i++) {
            if (null != activityStack.get(i)) {
                activityStack.get(i).finish();
            }
        }
        activityStack.clear();
    }

    /**
     * 退出应用程序
     */
    public void appExit(Context context) {
        try {
            finishAllActivity();
            ActivityManager activityMgr = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            activityMgr.killBackgroundProcesses(context.getPackageName());
            System.exit(0);
        } catch (Exception e) {	}
    }
}
