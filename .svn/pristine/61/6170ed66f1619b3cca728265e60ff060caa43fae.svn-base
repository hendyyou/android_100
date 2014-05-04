package cn.digione.yibaic.shop.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import cn.digione.yibaic.shop.R;
import cn.digione.yibaic.shop.acitvity.BaseActivity;
import cn.digione.yibaic.shop.acitvity.ShoppingActivity;
import cn.digione.yibaic.shop.bean.JsonNoneOutBean;
import cn.digione.yibaic.shop.common.Constants;
import cn.digione.yibaic.shop.http.CustomerJsonHttpResponseHandler;
import cn.digione.yibaic.shop.http.HttpClient;
import cn.digione.yibaic.shop.util.ToastUtil;
import cn.digione.yibaic.shop.util.Utils;
import com.loopj.android.http.RequestParams;

import java.util.HashMap;
import java.util.Map;

public class FMaBuyFragment extends BaseFragment {
    private Activity mContext;
    private EditText mInputFMaET;
    private Button mFMaSubmitBtn;
    private TextView mFMaObtainTV;
    private String mProductId;

    public BaseActivity getParent() {
        return (BaseActivity) getActivity();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getParent();
        mProductId = getArguments().getString("jcode_product_id");
        mCustomerJsonHttpResponseHandler = new CustomerJsonHttpResponseHandler<JsonNoneOutBean>(getActivity()) {

            @Override
            public void processCallSuccess(JsonNoneOutBean outBean, String msg) {
                // if (outBean != null) {
                Intent intent = new Intent(getActivity(), ShoppingActivity.class);
                intent.putExtra("JCODE", mInputFMaET.getText().toString());
                intent.putExtra("jcode_product_id", mProductId);
                intent.setAction("index_select_product");
                startActivity(intent);
                // }
            }

            @Override
            public void processCallFailure(JsonNoneOutBean outBean, String failureCode, String msg) {
                super.processCallFailure(outBean, failureCode, msg);
                ToastUtil.show(msg);
            }
        };
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fmabuy_fragment, container, false);
        mInputFMaET = (EditText) view.findViewById(R.id.et_f_ma_input);
        mFMaObtainTV = (TextView) view.findViewById(R.id.tv_f_ma_obtain);
        mFMaSubmitBtn = (Button) view.findViewById(R.id.btn_f_ma_submit);
        String fmaObtainStr = getString(R.string.f_ma_obtain_hint);
        Spanned fmaObtainSpn = Html.fromHtml(fmaObtainStr);
        mFMaObtainTV.setText(fmaObtainSpn);
        mFMaSubmitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mInputFMaET.getText() == null || mInputFMaET.getText().length() <= 0) {
                    mInputFMaET.setError(Utils.getErrorSpanString(R.string.must_fill, getActivity()));
                    return;
                }

                if (Utils.Network.isHasNetwork(getActivity())) {
                    HttpClient client = HttpClient.getInstall(getActivity());
                    client.setTimeOut(30000);//加码校验超时设置为30秒
                    String url = Constants.NetWorkRequest.VERIFICATION_JCODE_URL;
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("jcode", mInputFMaET.getText().toString());
                    RequestParams params = new RequestParams(map);
                    client.postAsync(url, params, mCustomerJsonHttpResponseHandler);
                } else {
                    ToastUtil.show(getActivity(), getString(R.string.network_cannot_access), ToastUtil.LENGTH_SHORT);
                }
            }

        });
        return view;

    }

}
