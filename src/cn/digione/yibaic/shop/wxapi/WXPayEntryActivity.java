package cn.digione.yibaic.shop.wxapi;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import cn.digione.yibaic.shop.R;
import cn.digione.yibaic.shop.acitvity.BaseActivity;
import cn.digione.yibaic.shop.common.Constants;
import cn.digione.yibaic.shop.util.Log;
import com.tencent.mm.sdk.constants.ConstantsAPI;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

/**
 * Created with IntelliJ IDEA.
 * User: Simpson
 * Date: 2014/4/9
 * Time: 14:57
 */
public class WXPayEntryActivity extends BaseActivity implements IWXAPIEventHandler {

    private IWXAPI api;
    private TextView tvResult;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCustomContentView(R.layout.order_pay_result_activity);

        tvResult = (TextView) findViewById(R.id.wxpay_result_textview);
        Button btnFinished = (Button) findViewById(R.id.btn_wxpay_finish);
        btnFinished.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(-1);
                finish();
            }
        });

        api = WXAPIFactory.createWXAPI(this, Constants.WeiXinPay.APP_ID);
        api.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq req) {
    }

    @Override
    public void onResp(BaseResp resp) {
        Log.d("WXPayEntryActivity onPayFinish, errCode = " + resp.errCode + ", transaction=" + resp.transaction + ", " +
              "errStr=" + resp.errStr + ", openId=" + resp.openId);
        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            int errCode = resp.errCode;
            if (errCode < 0) {
                errCode *= -1;
            }
            String wxPayResult[] = getResources().getStringArray(R.array.wx_pay_result);
            if (wxPayResult.length <= errCode) {
                errCode = BaseResp.ErrCode.ERR_COMM * -1;
            }
            tvResult.setText(getString(R.string.wx_pay_result_callback_msg, wxPayResult[errCode]));
        }
    }
}
