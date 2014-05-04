package cn.digione.yibaic.shop.ui;import android.content.Context;import android.os.Bundle;import android.telephony.TelephonyManager;import android.text.Editable;import android.text.SpannableStringBuilder;import android.text.TextUtils;import android.text.style.TextAppearanceSpan;import android.util.Patterns;import android.view.KeyEvent;import android.view.LayoutInflater;import android.view.View;import android.view.ViewGroup;import android.widget.EditText;import android.widget.RadioButton;import android.widget.RadioGroup;import android.widget.TextView;import cn.digione.yibaic.shop.R;import cn.digione.yibaic.shop.bean.ForgetPasswordBean;import cn.digione.yibaic.shop.common.Constants;import cn.digione.yibaic.shop.http.CustomerJsonHttpResponseHandler;import cn.digione.yibaic.shop.http.HttpClient;import cn.digione.yibaic.shop.util.Log;import cn.digione.yibaic.shop.util.Utils;import com.loopj.android.http.RequestParams;/** * Created with IntelliJ IDEA. * User: youzh * Date: 14-3-4 * Time: 下午4:20 */public class RegisterSelectAccountTypeFragment extends StepsFragment {    private static final int ERROR_EMAIL_USED = 2;    private static final int ERROR_NETWORK = 4;    private static final int ERROR_NETWORK_GW = 14;    private static final int ERROR_NETWORK_TIMEOUT_GW = 16;    private static final int ERROR_NO_SIM = 17;    private static final int ERROR_PHONE_USED = 1;    private static final int ERROR_SERVER = 5;    private static final int ERROR_SERVER_GW = 15;    private RadioGroup mAccountTypeRadios;    private View mPhonePanel;    private View mEmailPanel;    private RadioButton mPhoneRadioButton;    private RadioButton mEmailRadioButton;    private EditText mPhoneText;    private EditText mEmailText;    private int modeType;    private String register;    private SpannableStringBuilder getErrorSpanString(int i) {        String s = getActivity().getString(i);        TextAppearanceSpan textSpan = new TextAppearanceSpan(getActivity(), R.style.TextAppearance_Notice_Normal);        SpannableStringBuilder spanBuilder = new SpannableStringBuilder(s);        spanBuilder.setSpan(textSpan, spanBuilder.length() - s.length(), spanBuilder.length(), 33);        return spanBuilder;    }    public int getSelectedAccountTypeIndex() {        return mAccountTypeRadios.getCheckedRadioButtonId();    }    @Override    public void onCreate(Bundle savedInstanceState) {        super.onCreate(savedInstanceState);        // 忘记密码处理        mCustomerJsonHttpResponseHandler = new CustomerJsonHttpResponseHandler<ForgetPasswordBean>(getActivity()) {            @Override            public void processCallSuccess(ForgetPasswordBean outBean, String msg) {                if (outBean.getCode() == ForgetPasswordBean.EMAIL) {                    Bundle bundle = new Bundle();                    bundle.putInt(Constants.ArgName.UI.RegisterEmailSent.TITLE_NAME, R.string.title_forget_password);                    bundle.putString(Constants.ArgName.UI.RegisterEmailSent.EMAIL, register);                    RegisterEmailSentFragment registeremailsentfragment = new RegisterEmailSentFragment();                    registeremailsentfragment.setArguments(bundle);                    replaceToFragment(registeremailsentfragment, true);                    getActivity().setResult(-1);                }            }        };    }    public void onActivityCreated(Bundle bundle) {        super.onActivityCreated(bundle);        setButtonPreviousGone(true);    }    protected void onButtonNextClicked() {        Log.d("onButtonNextClicked()");        int accountType;        register = null;        Editable editable;        switch (getSelectedAccountTypeIndex()) {            case R.id.rb_account_type_phone:                editable = mPhoneText.getText();                if (null == editable) {                    mPhoneText.setError(getErrorSpanString(R.string.error_phone));                    return;                }                register = editable.toString();                if (!Utils.PhoneFormat.isPhone(register)) {                    mPhoneText.setError(getErrorSpanString(R.string.error_phone));                    return;                }                accountType = Constants.ArgValue.AccountType.PHONE;                break;            case R.id.rb_account_type_email:                editable = mEmailText.getText();                if (null == editable) {                    mEmailText.setError(getErrorSpanString(R.string.error_email));                    return;                }                register = editable.toString();                if (!Patterns.EMAIL_ADDRESS.matcher(register).matches()) {                    mEmailText.setError(getErrorSpanString(R.string.error_email));                    return;                }                accountType = Constants.ArgValue.AccountType.EMAIL;                break;            default:                return;        }        if (Constants.ArgValue.ModeType.REGISTER == modeType) {            Bundle bundle = new Bundle();            bundle.putString(Constants.ArgName.UI.RegisterPassword.REGISTER, register);            bundle.putInt(Constants.ArgName.UI.RegisterPassword.ACCOUNT_TYPE, accountType);            bundle.putInt(Constants.ArgName.UI.RegisterPassword.MODE_TYPE, Constants.ArgValue.ModeType.REGISTER);            RegisterPasswordFragment registerpasswordfragment = new RegisterPasswordFragment();            registerpasswordfragment.setArguments(bundle);            replaceToFragment(registerpasswordfragment, false);        } else if (Constants.ArgValue.ModeType.FORGET_PASSWORD == modeType) {            if (Constants.ArgValue.AccountType.PHONE == accountType) {                Bundle bundle = new Bundle();                bundle.putString(Constants.ArgName.UI.RegisterPassword.REGISTER, register);                bundle.putInt(Constants.ArgName.UI.RegisterPassword.MODE_TYPE, Constants.ArgValue.ModeType.FORGET_PASSWORD);                bundle.putInt(Constants.ArgName.UI.RegisterPassword.ACCOUNT_TYPE, Constants.ArgValue.AccountType.PHONE);                RegisterPasswordFragment registerpasswordfragment = new RegisterPasswordFragment();                registerpasswordfragment.setArguments(bundle);                replaceToFragment(registerpasswordfragment, false);                getActivity().setResult(-1);            } else {                // 调用忘记密码接口                RequestParams params = new RequestParams(Constants.ArgName.Request.ForgetPassword.LOGIN_STRING, register);                HttpClient httpClient = HttpClient.getInstall(getActivity());                httpClient.postAsync(Constants.NetWorkRequest.FORGET_PASSWORD_URL_010, params, mCustomerJsonHttpResponseHandler);            }        }    }    public View onCreateView(LayoutInflater layoutinflater, ViewGroup viewgroup, Bundle bundle) {		super.onCreateView(layoutinflater, viewgroup, bundle);        View view = layoutinflater.inflate(R.layout.register_select_type, viewgroup, false);        if (null == view) {            return null;        }        super.isUpdateShopCartData = false;        Bundle argBundle = getArguments();        modeType = argBundle.getInt(Constants.ArgName.UI.RegisterSelectAccountType.MODE_TYPE);        mAccountTypeRadios = (RadioGroup) view.findViewById(R.id.rg_account_types);        mPhoneRadioButton = (RadioButton) mAccountTypeRadios.findViewById(R.id.rb_account_type_phone);        mEmailRadioButton = (RadioButton) mAccountTypeRadios.findViewById(R.id.rb_account_type_email);        mPhonePanel = view.findViewById(R.id.ll_phone_input_panel);        mPhoneText = (EditText) view.findViewById(R.id.et_phone);        mPhoneText.setOnClickListener(new View.OnClickListener() {            public void onClick(View view1) {                mPhoneText.setError(null);            }        });        TelephonyManager telephonymanager = (TelephonyManager) getActivity().getSystemService(Context.TELEPHONY_SERVICE);        if (telephonymanager.getSimState() == TelephonyManager.SIM_STATE_READY &&            telephonymanager.getPhoneType() == TelephonyManager.PHONE_TYPE_GSM) {            String phoneNum = telephonymanager.getLine1Number();            if (!TextUtils.isEmpty(phoneNum)) {                mPhoneText.setText(phoneNum);            }        }        mEmailPanel = view.findViewById(R.id.ll_email_input_panel);        mEmailText = (EditText) view.findViewById(R.id.et_email);        mEmailText.setOnClickListener(new View.OnClickListener() {            public void onClick(View view1) {                mEmailText.setError(null);            }        });        mAccountTypeRadios.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {            public void onCheckedChanged(RadioGroup radiogroup, int checkedId) {                switch (checkedId) {                    case R.id.rb_account_type_phone:                        displaySoftInputIfNeed(mEmailText, false);                        mPhoneRadioButton.setBackgroundResource(R.drawable.radiobutton_up_bg);                        mEmailRadioButton.setBackgroundResource(R.drawable.radiobutton_bottom_bg);                        mPhonePanel.setVisibility(View.VISIBLE);                        mEmailPanel.setVisibility(View.GONE);                        mPhoneText.requestFocus();                        displaySoftInputIfNeed(mPhoneText, true);                        break;                    case R.id.rb_account_type_email:                        displaySoftInputIfNeed(mPhoneText, false);                        mPhoneRadioButton.setBackgroundResource(R.drawable.radiobutton_up_bg);                        mEmailRadioButton.setBackgroundResource(R.drawable.radiobottom_middle_noline_p);                        mPhonePanel.setVisibility(View.GONE);                        mEmailPanel.setVisibility(View.VISIBLE);                        mEmailText.requestFocus();                        displaySoftInputIfNeed(mEmailText, true);                        break;                }            }        });        mAccountTypeRadios.check(R.id.rb_account_type_phone);        TextView.OnEditorActionListener editorActionListener = new TextView.OnEditorActionListener() {            @Override            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {                if (actionId == 5) {                    triggerNextStep();                    return true;                }                return false;            }        };        mPhoneText.setOnEditorActionListener(editorActionListener);        mEmailText.setOnEditorActionListener(editorActionListener);        if (Constants.ArgValue.ModeType.REGISTER == modeType) {            mPhoneRadioButton.setText(R.string.register_phone_text);            mEmailRadioButton.setText(R.string.register_email_text);        } else if (Constants.ArgValue.ModeType.FORGET_PASSWORD == modeType) {            mPhoneRadioButton.setText(R.string.forget_password_phone_text);            mEmailRadioButton.setText(R.string.forget_password_email_text);        }        return view;    }    public void onResume() {        super.onResume();        switch (getSelectedAccountTypeIndex()) {            case R.id.rb_account_type_phone:                displaySoftInputIfNeed(mEmailText, false);                displaySoftInputIfNeed(mPhoneText, true);                break;            case R.id.rb_account_type_email:                displaySoftInputIfNeed(mPhoneText, false);                displaySoftInputIfNeed(mEmailText, true);                break;        }    }}