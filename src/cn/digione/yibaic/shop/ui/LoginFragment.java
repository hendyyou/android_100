package cn.digione.yibaic.shop.ui;import android.app.Activity;import android.content.Context;import android.content.Intent;import android.os.Bundle;import android.text.TextUtils;import android.view.*;import android.view.inputmethod.InputMethodManager;import android.widget.*;import cn.digione.yibaic.shop.R;import cn.digione.yibaic.shop.ShopApp;import cn.digione.yibaic.shop.acitvity.BaseActivity;import cn.digione.yibaic.shop.acitvity.RegisterAccountActivity;import cn.digione.yibaic.shop.bean.RequestErrorBean;import cn.digione.yibaic.shop.bean.UserBean;import cn.digione.yibaic.shop.common.Constants;import cn.digione.yibaic.shop.http.CustomerJsonHttpResponseHandler;import cn.digione.yibaic.shop.http.HttpClient;import cn.digione.yibaic.shop.util.ToastUtil;import cn.digione.yibaic.shop.util.Utils;import com.baidu.api.*;import com.baidu.api.AsyncBaiduRunner.RequestListener;import com.baidu.api.BaiduDialog.BaiduDialogListener;import com.loopj.android.http.RequestParams;import org.json.JSONException;import org.json.JSONObject;import java.io.IOException;import java.util.HashMap;import java.util.Map;/** * Created with IntelliJ IDEA. User: youzh Date: 14-3-4 Time: 上午11:19 */public class LoginFragment extends BaseFragment implements View.OnClickListener {    private final String HAVAED_LOGINED = "07";    private AutoCompleteTextView mAccountNameView;    private EditText mAccountPwdView;    private Button mButtonLogin;    private TextView mButtonBaiduLoginButton;    private LinearLayout mConstainer;    // 是否每次授权都强制登陆    private boolean isForceLogin = false;    private boolean isConfirmLogin = true;    private TextView mForgetPwdView;    private TextView mRegisterAccountView;    private Activity mContext;    private CustomerJsonHttpResponseHandler<UserBean> mLoginInHttpResponseHandler =            new CustomerJsonHttpResponseHandler<UserBean>(mContext, false, true) {                @Override                public void processCallSuccess(UserBean outBean, String msg) {                    loginSuceed(outBean, msg);                }                @Override                public void processCallFailure(UserBean outBean, String failureCode, String msg) {                    // 根据错误状态做相应的处理                    if (failureCode.equals(HAVAED_LOGINED)) {                        // 用户已经登录，无需再次进行登录，但是需要重置所有登录的客户端状态                        msg = getString(R.string.login_success);                        loginSuceed(outBean, msg);                    } else {                        resetUserLoginStatus();                        ToastUtil.show(mContext, msg, ToastUtil.LENGTH_SHORT);                    }                }                @Override                public void processHttpFailure(Throwable e, RequestErrorBean outBean) {                    resetUserLoginStatus();                    mButtonLogin.setEnabled(true);                    super.processHttpFailure(e, outBean);                }            };    private CustomerJsonHttpResponseHandler<UserBean> mLoginOutHttpResponseHandler =            new CustomerJsonHttpResponseHandler<UserBean>(mContext) {                @Override                public void processCallSuccess(UserBean outBean, String msg) {                    resetUserLoginStatus();                }            };    private UserBean currentUserBean;    private String loginReturnMsg;    public LoginFragment() {    }    public LoginFragment(Activity context) {        mContext = context;    }    private static void closeSoftInput(Activity activity) {        if (null == activity) {            return;        }        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);        if (inputMethodManager.isActive()) {            View currentFocusView = activity.getCurrentFocus();            if (null != currentFocusView) {                inputMethodManager.hideSoftInputFromWindow(currentFocusView.getWindowToken(), 0);            }        }    }    private void onStartLoginIn(String accountName, String accountPwd) {        closeSoftInput(mContext);        if (Utils.Network.isHasNetwork(mContext)) {            saveUserName(accountName);            HashMap<String, String> paramMap = new HashMap<String, String>();            paramMap.put("loginString", accountName);            paramMap.put("password", accountPwd);            paramMap.put("userType", 1 + "");            String url = Constants.NetWorkRequest.LOGIN_IN_URL_005;            mLoginInHttpResponseHandler.setContext(getParent());            httpRequest(url, paramMap, mLoginInHttpResponseHandler);        } else {            ToastUtil.show(mContext, getString(R.string.msg_network_fail), ToastUtil.LENGTH_SHORT);        }    }    private void httpRequest(String url, Map<String, String> paramMap, CustomerJsonHttpResponseHandler responseHandler) {        HttpClient client = HttpClient.getInstall(mContext);        RequestParams params = null;        if (paramMap != null) {            params = new RequestParams(paramMap);        }        client.postAsync(url, params, responseHandler);    }    private void onStartLoginOut(String accountName, String accountPwd) {        if (Utils.Network.isHasNetwork(mContext)) {            String url = Constants.NetWorkRequest.LOGIN_OUT_URL_007;            mLoginInHttpResponseHandler.setContext(getParent());            httpRequest(url, null, mLoginInHttpResponseHandler);        } else {            ToastUtil.show(mContext, getString(R.string.msg_network_fail), ToastUtil.LENGTH_SHORT);        }    }    public void onStartBaiduLogin(String uname, String uuid) {        if (Utils.Network.isHasNetwork(mContext)) {            String url = Constants.NetWorkRequest.BAIDU_LOGIN_URL_006;            HashMap<String, String> paramMap = new HashMap<String, String>();            paramMap.put("baiduId", uuid);            paramMap.put("baiduName", uname);            mLoginInHttpResponseHandler.setContext(getParent());            httpRequest(url, paramMap, mLoginInHttpResponseHandler);        } else {            ToastUtil.show(mContext, getString(R.string.msg_network_fail), ToastUtil.LENGTH_SHORT);        }    }    public String getUserName() {        return mAccountNameView.getText().toString();    }    public String getUserPwd() {        return mAccountPwdView.getText().toString();    }    public void onClick(View view) {        if (view == mButtonLogin) {            String accountName = mAccountNameView.getText().toString();            String accountPwd = mAccountPwdView.getText().toString();            boolean flag1 = false;            if (TextUtils.isEmpty(accountName)) {                flag1 = true;                mAccountNameView.setError(Utils.getErrorSpanString(R.string.login_error_empty_username, getActivity()));            }            if (TextUtils.isEmpty(accountPwd)) {                flag1 = true;                mAccountPwdView.setError(Utils.getErrorSpanString(R.string.login_error_empty_pwd, getActivity()));            }            if (!flag1) {                Utils.SoftInput.hide(getActivity(), mAccountNameView.getWindowToken());                onStartLoginIn(accountName, accountPwd);                mButtonLogin.setEnabled(false);            }        } else if (view == mButtonBaiduLoginButton) {// 使用百度账号登陆            mConstainer.setVisibility(View.GONE);            if(ShopApp.getInstance().getBaidu()==null){            	ShopApp.getInstance().setBaidu(new Baidu(Constants.BAIDU_API_KEY, view.getContext()));            }            ShopApp.getInstance().getBaidu()                   .authorize(getActivity(), isForceLogin, isConfirmLogin, new BaiduDialogListener() {                                  @Override                                  public void onComplete(Bundle values) {                                      ShopApp.getInstance().showDialog(mContext);                                	  AsyncBaiduRunner runner = new AsyncBaiduRunner(ShopApp.getInstance().getBaidu());                                      runner.request(Constants.NetWorkRequest.BaiduLoggedInUser_URL, null, "POST",                                                     new DefaultRequstListener());                                  }                                  @Override                                  public void onBaiduException(BaiduException e) {                                  }                                  @Override                                  public void onError(BaiduDialogError e) {                                  }                                  @Override                                  public void onCancel() {                                      Util.logd("cancel", "I am back");                                  }                              }                   );                    } else if (view == mForgetPwdView) {            Intent intent = new Intent(mContext, RegisterAccountActivity.class);            intent.putExtra(Constants.ArgName.UI.RegisterAccountActivity.TITLE_NAME, R.string.title_forget_password);            intent.putExtra(Constants.ArgName.UI.RegisterAccountActivity.MODE_TYPE,                            Constants.ArgValue.ModeType.FORGET_PASSWORD);            mContext.startActivity(intent);        } else if (view == mRegisterAccountView) {            Intent intent = new Intent(mContext, RegisterAccountActivity.class);            intent.putExtra(Constants.ArgName.UI.RegisterAccountActivity.TITLE_NAME, R.string.title_reg);            intent.putExtra(Constants.ArgName.UI.RegisterAccountActivity.MODE_TYPE, Constants.ArgValue.ModeType.REGISTER);            mContext.startActivity(intent);        }    }    private BaseActivity getParent() {        return (BaseActivity) getActivity();    }    @Override    public void onCreate(final Bundle savedInstanceState) {        super.onCreate(savedInstanceState);        if (getParent() != null) {            mContext = getParent();        }        super.isUpdateShopCartData = false;    }    @Override    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {        super.onCreateView(inflater, container, savedInstanceState);        View view = inflater.inflate(R.layout.login_fragment, container, false);        if (null == view) {            return null;        }        mConstainer = (LinearLayout) view.findViewById(R.id.ll_login_container) ;        mConstainer.setVisibility(View.VISIBLE);        mAccountNameView = (AutoCompleteTextView) view.findViewById(R.id.et_account_name);        mAccountPwdView = (EditText) view.findViewById(R.id.et_account_password);        mButtonLogin = (Button) view.findViewById(R.id.btn_login);        mForgetPwdView = (TextView) view.findViewById(R.id.tv_forget_pwd);        mRegisterAccountView = (TextView) view.findViewById(R.id.tv_reg_new);        mButtonBaiduLoginButton = (TextView) view.findViewById(R.id.btn_baidu_login);        mAccountPwdView.setOnEditorActionListener(new android.widget.TextView.OnEditorActionListener() {            public boolean onEditorAction(TextView textview, int i, KeyEvent keyevent) {                if (i == 6) {                    onStartLoginIn(mAccountNameView.getText().toString(), mAccountPwdView.getText().toString());                    return true;                } else {                    return false;                }            }        });        mButtonLogin.setOnClickListener(this);        mButtonBaiduLoginButton.setOnClickListener(this);        mForgetPwdView.setOnClickListener(this);        mRegisterAccountView.setOnClickListener(this);        String s = Utils.Preference.getStringPref(getActivity(), Constants.DataFile.DefaultKey.PREF_USER_NAMES, "");        if (!TextUtils.isEmpty(s)) {            ArrayAdapter arrayadapter = new ArrayAdapter(getActivity(), R.layout.login_username_list_item, s.split(","));            mAccountNameView.setThreshold(1);            mAccountNameView.setAdapter(arrayadapter);        }        view.setOnTouchListener(new View.OnTouchListener() {            @Override            public boolean onTouch(final View v, final MotionEvent event) {                closeSoftInput(mContext);                return false;            }        });        return view;    }    public void onResume() {        super.onResume();        /*         * getActivity().getWindow().setSoftInputMode(20); if (TextUtils.isEmpty(mAccountNameView.getText())) {		 * mAccountNameView.requestFocus(); } else { mAccountPwdView.requestFocus(); }		 */    }    private void loginSuceed(UserBean outBean, String msg) {           	currentUserBean = outBean;        loginReturnMsg = msg;        // ShopApp.getInstance().setLogin(true);        Utils.Preference.setBooleanPref(mContext, Constants.DataFile.DefaultKey.APP_LOGIN_FLAG, true);        Utils.Preference.setMapPref(mContext, userObjectToMap(outBean));               if (getParent() != null) {            mContext.finish();        }    }    private void resetUserLoginStatus() {        Utils.Preference.setBooleanPref(mContext, Constants.DataFile.DefaultKey.APP_LOGIN_FLAG, false);        Utils.Preference.removeMapPref(mContext, getUserMap());        ShopApp.getInstance().setUser(null);        mButtonLogin.setEnabled(true);        getBaseActivity().updateShoppingCountRequestHttp();    }    private void saveUserName(String s) {        String prefUserNames = Utils.Preference.getStringPref(mContext, Constants.DataFile.DefaultKey.PREF_USER_NAMES, "");        StringBuilder stringbuilder = new StringBuilder(prefUserNames);        if (!TextUtils.isEmpty(prefUserNames)) {            if (stringbuilder.indexOf(s) == -1) {                if (prefUserNames.split(",").length > 4) {                    stringbuilder.delete(0, 1 + stringbuilder.indexOf(","));                }                stringbuilder.append(",");                stringbuilder.append(s);            }        } else {            stringbuilder.append(s);        }        Utils.Preference.setStringPref(mContext, Constants.DataFile.DefaultKey.PREF_USER_NAMES, stringbuilder.toString());    }    private Map<String, String> userObjectToMap(UserBean userBean) {        Map<String, String> map = null;        if (userBean != null) {            map = getUserMap();            map.put(Constants.DataFile.DefaultKey.USER_ID, userBean.getId() + "");            map.put(Constants.DataFile.DefaultKey.USER_STATE, userBean.getState() + "");            map.put(Constants.DataFile.DefaultKey.USER_AREAID, userBean.getAreaId() + "");            map.put(Constants.DataFile.DefaultKey.USER_REMARK, userBean.getRemark());            map.put(Constants.DataFile.DefaultKey.USER_NICKNAME, userBean.getNickname());            map.put(Constants.DataFile.DefaultKey.USER_EMAIL, userBean.getEmail());            map.put(Constants.DataFile.DefaultKey.USER_USERNAME, userBean.getUserName());            map.put(Constants.DataFile.DefaultKey.USER_MOBILE, userBean.getMobile());        }        return map;    }    private Map<String, String> getUserMap() {        HashMap<String, String> map = new HashMap<String, String>();        map.put(Constants.DataFile.DefaultKey.USER_ID, null);        map.put(Constants.DataFile.DefaultKey.USER_STATE, null);        map.put(Constants.DataFile.DefaultKey.USER_AREAID, null);        map.put(Constants.DataFile.DefaultKey.USER_REMARK, null);        map.put(Constants.DataFile.DefaultKey.USER_NICKNAME, null);        map.put(Constants.DataFile.DefaultKey.USER_EMAIL, null);        map.put(Constants.DataFile.DefaultKey.USER_USERNAME, null);        map.put(Constants.DataFile.DefaultKey.USER_MOBILE, null);        return map;    }    public class DefaultRequstListener implements RequestListener {        /*         * (non-Javadoc)         * @see com.baidu.android.RequestListener#onBaiduException(com.baidu.android.BaiduException)         */        @Override        public void onBaiduException(BaiduException arg0) {        }        /*         * (non-Javadoc)         * @see com.baidu.android.RequestListener#onComplete(java.lang.String)         */        @Override        public void onComplete(final String value) {            getActivity().runOnUiThread(new Runnable() {                @Override                public void run() {                    try {                        JSONObject loginJsonObject = new JSONObject(value);                        String uid = loginJsonObject.getString("uid");                        String uname = loginJsonObject.getString("uname");                        saveUserName(uname);                        //  if (Utils.Network.isHasNetwork(getActivity())) {                        onStartBaiduLogin(uname, uid);                     /*   } else {                            ToastUtil.show(mContext, getString(R.string.msg_networkfail), ToastUtil.LENGTH_SHORT);                        }*/                    } catch (JSONException e) {                        e.printStackTrace();                    }                    // baidu.clearAccessToken();// 可清除百度登陆的会话                }            });        }        /*         * (non-Javadoc)         *         * @see com.baidu.android.RequestListener#onIOException(java.io.IOException)         */        @Override        public void onIOException(IOException arg0) {        }    }}