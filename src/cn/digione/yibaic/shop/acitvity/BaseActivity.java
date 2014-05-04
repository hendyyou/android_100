package cn.digione.yibaic.shop.acitvity;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.Menu;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import cn.digione.yibaic.shop.R;
import cn.digione.yibaic.shop.ShopApp;
import cn.digione.yibaic.shop.bean.ShoppingCartBean;
import cn.digione.yibaic.shop.common.Constants;
import cn.digione.yibaic.shop.http.CustomerJsonHttpResponseHandler;
import cn.digione.yibaic.shop.http.HttpClient;
import cn.digione.yibaic.shop.ui.LoginFragment;
import cn.digione.yibaic.shop.util.Log;
import cn.digione.yibaic.shop.util.ToastUtil;
import cn.digione.yibaic.shop.util.Utils;
import cn.digione.yibaic.shop.widget.BaseAlertDialog;
import com.loopj.android.http.RequestParams;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * Created with IntelliJ IDEA. User: youzh Date: 14-2-25 Time: 下午4:46
 */
public class BaseActivity extends FragmentActivity {
    private static long mShoppingCount = -1;
    private static long mLastTimeOfShoppingCountUpdate;
    protected Handler mHandler;
    protected View mHomeButton;
    protected TextView mMenuItemSwitch;
    protected TextView mMenuItemURL;
    protected TextView mMenuItemUpdate;
    protected PopupWindow mMenuWindow;
    protected View mShoppingStatusBar;
    private FrameLayout mContentContainer;
    private View mDecoratedView;
    private LinearLayout mTitleBottomLine;
    private boolean mHomeButtonEnable;
    private boolean mShoppingBarEnable;
    private TextView mShoppingCountView;
    private TextView mTitle;
    private View mTitleBarContainer;
    private boolean mTitleBarEnable;
    private AccountManager mAccountManager;
    private Set mRefreshListenerSet;
    private CustomerJsonHttpResponseHandler<ShoppingCartBean> mUpdateShopCountHttpResponse;
    private View.OnClickListener mClickListener = new View.OnClickListener() {
        public void onClick(View paramAnonymousView) {
            switch (paramAnonymousView.getId()) {
                case R.id.title_right_bar:

                case R.id.title_bar_custom_view:
                    if (isLogin()) {
                        gotoShoppingCart();
                    } else {

                        BaseActivity.this.gotoLogin();
                    }
                    // return;
                    // }

                    return;
                case R.id.title_bar_home:
                    onBackPressed();
                    return;
                default:
                    return;
            }
        }
    };

    private View.OnClickListener mMenuItemClick = new View.OnClickListener() {

        public void onClick(View view) {

        }
    };

    public Boolean isLogin() {
        Boolean isLogin = Utils.Preference.getBooleanPref(this, Constants.DataFile.DefaultKey.APP_LOGIN_FLAG, false);
        return isLogin;
    }

    private void requestHttp(String url, RequestParams params) {
        HttpClient client = HttpClient.getInstall(this);
        client.postAsync(url, params, mUpdateShopCountHttpResponse);
    }

    public void updateShoppingCountRequestHttp() {
        if (isLogin()) {
            String url = Constants.NetWorkRequest.SHOPPING_QUERY_CART_COUNT_URL_030;
            requestHttp(url, null);
        } else {
            updateShoppingCount(0);
        }
    }

    private void updateShoppingCountView() {
        if (mShoppingCount > 0) {
            mShoppingCountView.setVisibility(View.VISIBLE);
            mShoppingCountView.setText(String.valueOf(mShoppingCount));
        } else {
            mShoppingCountView.setVisibility(View.INVISIBLE);
        }
    }

    public void updateShoppingCount(int i) {
        mShoppingCount = i;
        if (mShoppingCount != -1) {
            mLastTimeOfShoppingCountUpdate = System.currentTimeMillis();
        }
        updateShoppingCountView();
    }

    public View getShoppingStatusBar() {
        return mShoppingCountView;
    }

    public View getTitleBarContainer() {
        return mTitleBarContainer;
    }

    public Fragment getFragmentByTag(String s) {
        return getSupportFragmentManager().findFragmentByTag(s);
    }

    public View getHomeButton() {
        return mHomeButton;
    }

    public void gotoLogin() {
        // ToastUtil.show(BaseActivity.this, R.string.login_before);
        Intent intent = new Intent(this, LoginActivity.class);
        startActivityForResult(intent, 3);
    }

    protected void gotoShoppingCart() {
        Intent intent = new Intent(this, ShoppingActivity.class);
        startActivity(intent);
    }

    protected Fragment newFragmentByTag(String s) {
        return null;
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        ShopApp.getInstance().addActivity(this);
        Log.i(this.getClass().getSimpleName() + "-----onCreate()");
        mHandler = new Handler();
        // BaseLayout baselayout = (BaseLayout) LayoutInflater.from(this).inflate(R.layout.base_activity, null);
        setContentView(R.layout.base_activity);
        mAccountManager = AccountManager.get(this);
        mTitleBottomLine = (LinearLayout) findViewById(R.id.ll_title_bottom_line);
        mContentContainer = (FrameLayout) findViewById(R.id.content_container);
        mTitleBarContainer = findViewById(R.id.title_bar_container);
        mShoppingStatusBar = findViewById(R.id.title_bar_custom_view);
        mShoppingStatusBar.setOnClickListener(mClickListener);
        mShoppingCountView = (TextView) findViewById(R.id.title_bar_shopping_count);
        mTitle = (TextView) findViewById(R.id.title_bar_title);
        mHomeButton = findViewById(R.id.title_bar_home);
        mHomeButton.setOnClickListener(mClickListener);
        mUpdateShopCountHttpResponse = new CustomerJsonHttpResponseHandler<ShoppingCartBean>(this, true, true) {
            @Override
            public void processCallSuccess(ShoppingCartBean outBean, String msg) {
                if (outBean != null) {
                    Utils.Preference.setIntPref(BaseActivity.this, Constants.DataFile.DefaultKey.SHOPPING_COUNT,
                                                outBean.getProductCount());
                    mShoppingCount = outBean.getProductCount();
                    updateShoppingCount(outBean.getProductCount());
                } else {
                    Utils.Preference.setIntPref(BaseActivity.this, Constants.DataFile.DefaultKey.SHOPPING_COUNT, 0);
                    mShoppingCount = 0;
                    updateShoppingCount(0);
                }
            }

            @Override
            public void processCallFailure(ShoppingCartBean outBean, String failureCode, String msg) {
                if (outBean == null) {
                    Utils.Preference.setIntPref(BaseActivity.this, Constants.DataFile.DefaultKey.SHOPPING_COUNT, 0);
                    mShoppingCount = 0;
                    updateShoppingCount(0);
                }
            }
        };

		/*
         * mMenuLayout = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.menu_list, null); mMenuItemSetting =
		 * (TextView) mMenuLayout.findViewById(2131296567); mMenuItemSetting.setOnClickListener(mMenuItemClick);
		 * mMenuItemOrders = (TextView) mMenuLayout.findViewById(2131296565);
		 * mMenuItemOrders.setOnClickListener(mMenuItemClick); mMenuItemRefresh = (TextView)
		 * mMenuLayout.findViewById(2131296564); mMenuItemRefresh.setOnClickListener(mMenuItemClick); mMenuItemSwitch =
		 * (TextView) mMenuLayout.findViewById(2131296568); mMenuItemSwitch.setOnClickListener(mMenuItemClick);
		 * mMenuItemUpdate = (TextView) mMenuLayout.findViewById(2131296566);
		 * mMenuItemUpdate.setOnClickListener(mMenuItemClick); mMenuItemURL = (TextView)
		 * mMenuLayout.findViewById(2131296563); mMenuItemURL.setOnClickListener(mMenuItemClick); //mMenuWindow = new
		 * MenuPopupWindow(this, mMenuLayout);
		 */
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(this.getClass().getSimpleName() + "-----onStart()");
    }

    protected void onResume() {
        super.onResume();
        Log.i(this.getClass().getSimpleName() + "-----onResume()");
        if (!Utils.Preference.getBooleanPref(BaseActivity.this, Constants.DataFile.DefaultKey.APP_LOGIN_FLAG, false)) {
            //handlerBaiduAccount();
        }
      /*  mShoppingCount = Utils.Preference.getIntPref(this, "shopping_count", -1);
        if (mShoppingCount != -1 && System.currentTimeMillis() - mLastTimeOfShoppingCountUpdate  > 180000L) {
            updateShoppingCountRequestHttp();
        } */
        /*
         * if (mShoppingBarEnable && LoginManager.getInstance().hasLogin()) { if (mShoppingCount != -1 &&
		 * System.currentTimeMillis() - sLastTimeOfShoppingCountUpdate <= 180000L) { updateShoppingCountView(); } else {
		 * updateShoppingCount(); } }
		 */
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(this.getClass().getSimpleName() + "-----onStop()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(this.getClass().getSimpleName() + "-----onPause()");
    }

    protected void setCustomContentView(int i) {
        View view = View.inflate(this, i, null);
        if (view != null) {
            if (mDecoratedView != null) {
                mContentContainer.removeView(mDecoratedView);
            }
            mContentContainer.addView(view);
            mDecoratedView = view;
        } else if (mDecoratedView != null) {
            mContentContainer.removeView(mDecoratedView);
            mDecoratedView = null;
        }
    }

    public void setHomeButtonEnable(boolean flag) {
        mHomeButtonEnable = flag;
        View view = mHomeButton;
        int i;
        if (mHomeButtonEnable) {
            i = View.VISIBLE;
        } else {
            i = View.GONE;
        }
        view.setVisibility(i);
    }

    public void setTitleBottomLineView(boolean flag) {
        if (mTitleBottomLine != null) {
            if (flag) {
                mTitleBottomLine.setVisibility(View.VISIBLE);
            } else {
                mTitleBottomLine.setVisibility(View.GONE);
            }
        }
    }

    public void setLeftView(View view) {
        LinearLayout linearlayout = (LinearLayout) findViewById(R.id.custom_container);
        linearlayout.setGravity(17);
        linearlayout.removeAllViewsInLayout();
        linearlayout.addView(view);
    }

    public void setRightView(View view) {
        LinearLayout linearlayout = (LinearLayout) findViewById(R.id.title_right_bar);
        linearlayout.removeAllViewsInLayout();
        linearlayout.addView(view);
    }

    public void setShoppingBarEnable(boolean flag) {
        mShoppingBarEnable = flag;
        View view = mShoppingStatusBar;
        int i;
        if (mShoppingBarEnable) {
            i = View.VISIBLE;
        } else {
            i = View.GONE;
        }
        view.setVisibility(i);
    }

    public void setTitle(int i) {
        if (mTitle != null) {
            mTitle.setText(i);
        }
    }

    public void setTitle(CharSequence charsequence) {
        if (mTitle != null) {
            mTitle.setText(charsequence);
        }
    }

    public void setTitleBarEnable(boolean flag) {
        mTitleBarEnable = flag;
        View view = mTitleBarContainer;
        int i;
        if (mTitleBarEnable) {
            i = View.VISIBLE;
        } else {
            i = View.GONE;
        }
        view.setVisibility(i);
    }

    public void showFragment(String tag, Bundle bundle, boolean isAddBakcStack) {
        if (mDecoratedView == null) {
            Log.w("mDecoratedView is NOT FOUND.");
            return;
        }
        if (mDecoratedView.getId() <= 0) {
            throw new IllegalArgumentException("The activity in xml layout MUST has argument 'id'.");
        }
        Fragment fragment = getFragmentByTag(tag);
        if (fragment == null) {
            fragment = newFragmentByTag(tag);
            if (bundle != null) {
                fragment.setArguments(bundle);
            }
        }
      /*  if (fragment.isAdded()) {
            return;
        }*/
        if (fragment == null) {
            Log.w("NO fragment found by tag: " + tag);
            return;
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmenttransaction = fragmentManager.beginTransaction();
        //  Log.i("fragmentManager: " + fragmentManager.toString() + "");
        fragmenttransaction.setTransition(4099);
        fragmenttransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out, android.R.anim.fade_in,
                                                android.R.anim.fade_out);
        fragmenttransaction.replace(mDecoratedView.getId(), fragment, tag);
        if (isAddBakcStack) {
            fragmenttransaction.addToBackStack(tag);
        }
        fragmenttransaction.commitAllowingStateLoss();
        //  Log.i("fragmentManager BackStackEntryCount()=" + fragmentManager.getBackStackEntryCount() + "");
    }


    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add("menu");
        return super.onCreateOptionsMenu(menu);
    }

    protected void onDestroy() {
        super.onDestroy();
        //Utils.Preference.removePref(this,"shopping_count");
    }

    public boolean onMenuOpened(int i, Menu menu) {
        if (mMenuWindow != null) {
            if (Log.getEnable()) {
                mMenuItemSwitch.setVisibility(View.VISIBLE);
                if (Utils.Preference.getBooleanPref(this, Constants.DataFile.DefaultKey.PREF_USER_DEBUG, true)) {
                    mMenuItemSwitch.setText(R.string.menu_switch_formal);
                } else {
                    mMenuItemSwitch.setText(R.string.menu_switch_test);
                }
                mMenuItemURL.setVisibility(View.VISIBLE);
            } else {
                mMenuItemSwitch.setVisibility(View.GONE);
                mMenuItemURL.setVisibility(View.GONE);
            }
            mMenuItemUpdate.setVisibility(View.GONE);
            if (!mMenuWindow.isShowing()) {
                mMenuWindow.showAtLocation(findViewById(R.id.popup_parent), 80, 0, 0);
            }
        }
        return false;
    }

    protected void onActivityResult(int i, int j, Intent intent) {
        //        if (j == -1) {
        //            String s = intent.getStringExtra("key_user_id");
        //            String s1 = intent.getStringExtra("key_service_token");
        //            String s2 = intent.getStringExtra("key_security");
        //            return;
        //        }
    }

    public void onLogin(String s, String s1, String s2) {
        // updateShoppingCount();
    }

    public void onLogout() {
        // updateShoppingCount(-1);
    }

    public void unregisterRefreshListener(OnRefreshListener onrefreshlistener) {
        if (mRefreshListenerSet != null) {
            mRefreshListenerSet.remove(onrefreshlistener);
        }
    }

    public void registerRefreshListener(OnRefreshListener onrefreshlistener) {
        if (mRefreshListenerSet == null) {
            mRefreshListenerSet = new HashSet();
        }
        mRefreshListenerSet.add(onrefreshlistener);
    }

    public boolean hasSystemBaiduAccount() {
        Account aaccount[] = mAccountManager.getAccountsByType(Constants.ACCOUNT_TYPE);
        boolean res = false;
        if (aaccount.length > 0) {
            res = true;
        }
        return res;
    }

    public String getSystemAccountId() {
        Account aaccount[] = mAccountManager.getAccountsByType(Constants.ACCOUNT_TYPE);
        if (aaccount.length > 0) {
            return aaccount[0].name;
        } else {
            return null;
        }
    }

    public String getSystemAccountPasswor() {
        Account aaccount[] = mAccountManager.getAccountsByType(Constants.ACCOUNT_TYPE);
        if (aaccount.length > 0) {
            return mAccountManager.getPassword(aaccount[0]);
        } else {
            return null;
        }
    }

    private String getMyUUID(String name) {
        if (name != null && !"".equals(name)) {
            UUID uuid = UUID.nameUUIDFromBytes(name.getBytes());
            String uniqueId = uuid.toString();
            Log.d("uuid=" + uniqueId);
            return uniqueId;
        }
        return null;
    }

    private void showSystemLoginDialog() {
        BaseAlertDialog basealertdialog = new BaseAlertDialog(this);
        basealertdialog.setTitle(R.string.autologin_title);
        Resources resources = getResources();
        basealertdialog.setMessage(resources.getString(R.string.autologin_summary, getSystemAccountId()));
        basealertdialog.setPositiveButton(R.string.autologin_ask_ok, new android.view.View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String uname = getSystemAccountId();
                String uuid = getMyUUID(uname);
                Log.i("account name=" + uname);
                if (!TextUtils.isEmpty(uname)) {
                    LoginFragment loginFragment = (LoginFragment) getFragmentByTag("login_fragment");
                    if (loginFragment == null) {
                        loginFragment = new LoginFragment(BaseActivity.this);
                    }
                    Account aaccount[] = mAccountManager.getAccountsByType(Constants.ACCOUNT_TYPE);
                    String password = getSystemAccountPasswor();
                    loginFragment.onStartBaiduLogin(uname, uuid);
                } else {
                    ToastUtil.show(R.string.login_system_failed);
                }
            }
        });
        basealertdialog.setNegativeButton(R.string.autologin_ask_cancel, new android.view.View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BaseActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
        basealertdialog.show();
    }

    private void handlerBaiduAccount() {
        final Handler handler = new Handler();
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if (!Utils.Preference
                        .getBooleanPref(BaseActivity.this, Constants.DataFile.DefaultKey.APP_LOGIN_FLAG, false)) {
                    Boolean token = hasSystemBaiduAccount();
                    if (token) {
                        showSystemLoginDialog();
                        //handler.removeCallbacks(runnable);
                    }
                }
            }
        };
        handler.postDelayed(runnable, 1000 * 30);
    }

    public static interface OnRefreshListener {

        public abstract void onRefresh();
    }
}
