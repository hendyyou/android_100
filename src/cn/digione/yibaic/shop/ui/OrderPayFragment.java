package cn.digione.yibaic.shop.ui;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import cn.digione.yibaic.shop.R;
import cn.digione.yibaic.shop.bean.*;
import cn.digione.yibaic.shop.common.Constants;
import cn.digione.yibaic.shop.http.CustomerJsonHttpResponseHandler;
import cn.digione.yibaic.shop.http.HttpClient;
import cn.digione.yibaic.shop.util.ToastUtil;
import cn.digione.yibaic.shop.util.Utils;
import com.loopj.android.http.RequestParams;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

/**
 * Created with IntelliJ IDEA.
 * User: Simpson
 * Date: 2014/4/9
 * Time: 14:56
 */
public class OrderPayFragment extends StepsFragment {

    private IWXAPI api;
    private TextView tvJiaCoinRemain;
    private TextView tvOrderFeeRemain;
    private EditText evCoupon;
    private Button btnCoupon;
    private TextView tvCouponUsed;
    private EditText evJiaCoin;
    private Button btnJiaCoin;
    private TextView tvJiaCoinUsed;
    private boolean isUsedCoupon;
    private boolean isUsedJiaCoin;
    private OrderCouponJiaCoinOutBean orderCouponJiaCoinOutBean;
    private OrderDetailBean orderDetailBean;
    private long userJiaCoinRemain;
    private boolean isSentReq;

    private CustomerJsonHttpResponseHandler<JiaCoinBean> jiaCoinSearchResponseHandler;
    private CustomerJsonHttpResponseHandler<OrderCouponJiaCoinOutBean> useConCodeResponseHandler;
    private CustomerJsonHttpResponseHandler<OrderCouponJiaCoinOutBean> cancelConCodeResponseHandler;
    private CustomerJsonHttpResponseHandler<OrderCouponJiaCoinOutBean> useJiaCoinResponseHandler;
    private CustomerJsonHttpResponseHandler<OrderCouponJiaCoinOutBean> cancelJiaCoinResponseHandler;
    private CustomerJsonHttpResponseHandler<JsonNoneOutBean> payForJiaMoneyOrConCodeResponseHandler;
    private CustomerJsonHttpResponseHandler<TenpayReqBean> toWXPayForAppResponseHandler;

    private String getUsedCoupon() {
        Editable editable = evCoupon.getText();
        if (null != editable) {
            return editable.toString().trim();
        }
        return "";
    }

    private String getUsedJiaCoin() {
        Editable editable = evJiaCoin.getText();
        if (null != editable) {
            return editable.toString().trim();
        }
        return "";
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        api = WXAPIFactory.createWXAPI(getActivity(), Constants.WeiXinPay.APP_ID);
        api.registerApp(Constants.WeiXinPay.APP_ID);

        jiaCoinSearchResponseHandler = new CustomerJsonHttpResponseHandler<JiaCoinBean>(getActivity()) {
            @Override
            public void processCallSuccess(JiaCoinBean outBean, String msg) {
                if (null == outBean || outBean.getBalance() == 0) {
                    // 没有加币
                    userJiaCoinRemain = 0;
                    btnJiaCoin.setEnabled(false);
                } else {
                    // 有加币
                    userJiaCoinRemain = outBean.getBalance();
                    btnJiaCoin.setEnabled(true);
                }
                tvJiaCoinRemain.setText(getString(R.string.order_pay_jia_coin_remain, userJiaCoinRemain / 100));
            }
        };

        useConCodeResponseHandler = new CustomerJsonHttpResponseHandler<OrderCouponJiaCoinOutBean>(getActivity()) {
            @Override
            public void processCallSuccess(OrderCouponJiaCoinOutBean outBean, String msg) {
                isUsedCoupon = true;
                orderCouponJiaCoinOutBean = outBean;
                btnCoupon.setText(R.string.order_pay_coupon_btn_cancel);
                tvOrderFeeRemain.setText(getString(R.string.money_format, Utils.Money.MoneyToYuan(outBean.getOrderAmount())));
                tvCouponUsed.setVisibility(View.VISIBLE);
                tvCouponUsed.setText(
                        getString(R.string.order_pay_coupon_used, Utils.Money.MoneyToYuan(outBean.getPreferentialAmt())));
                setCouponAndJiaCoinControl();
            }
        };

        cancelConCodeResponseHandler = new CustomerJsonHttpResponseHandler<OrderCouponJiaCoinOutBean>(getActivity()) {
            @Override
            public void processCallSuccess(OrderCouponJiaCoinOutBean outBean, String msg) {
                isUsedCoupon = false;
                orderCouponJiaCoinOutBean = outBean;
                setCouponAndJiaCoinControl();
                btnCoupon.setText(R.string.order_pay_coupon_btn_use);
                tvOrderFeeRemain.setText(getString(R.string.money_format, Utils.Money.MoneyToYuan(outBean.getOrderAmount())));
                tvCouponUsed.setVisibility(View.GONE);
            }
        };

        useJiaCoinResponseHandler = new CustomerJsonHttpResponseHandler<OrderCouponJiaCoinOutBean>(getActivity()) {
            @Override
            public void processCallSuccess(OrderCouponJiaCoinOutBean outBean, String msg) {
                isUsedJiaCoin = true;
                orderCouponJiaCoinOutBean = outBean;
                setCouponAndJiaCoinControl();
                btnJiaCoin.setText(R.string.order_pay_jia_coin_btn_cancel);
                tvOrderFeeRemain.setText(getString(R.string.money_format, Utils.Money.MoneyToYuan(outBean.getOrderAmount())));
                tvJiaCoinUsed.setVisibility(View.VISIBLE);
                tvJiaCoinUsed.setText(getString(R.string.order_pay_jia_coin_used, Utils.Money.MoneyToYuan(outBean.getJiaCurrencyAmt())));
            }
        };

        cancelJiaCoinResponseHandler = new CustomerJsonHttpResponseHandler<OrderCouponJiaCoinOutBean>(getActivity()) {
            @Override
            public void processCallSuccess(OrderCouponJiaCoinOutBean outBean, String msg) {
                isUsedJiaCoin = false;
                orderCouponJiaCoinOutBean = outBean;
                setCouponAndJiaCoinControl();
                btnJiaCoin.setText(R.string.order_pay_jia_coin_btn_use);
                tvOrderFeeRemain.setText(getString(R.string.money_format, Utils.Money.MoneyToYuan(outBean.getOrderAmount())));
                tvJiaCoinUsed.setVisibility(View.GONE);
            }
        };


        payForJiaMoneyOrConCodeResponseHandler = new CustomerJsonHttpResponseHandler<JsonNoneOutBean>(getActivity()) {
            @Override
            public void processCallSuccess(JsonNoneOutBean outBean, String msg) {
                ToastUtil.show(getActivity(), msg);
                Utils.System.popBackStack(getActivity());
            }
        };

        toWXPayForAppResponseHandler = new CustomerJsonHttpResponseHandler<TenpayReqBean>(getActivity()) {
            @Override
            public void processCallSuccess(TenpayReqBean outBean, String msg) {
                PayReq req = new PayReq();
                req.appId = outBean.getAppId();
                req.partnerId = outBean.getPartnerId();
                req.prepayId = outBean.getPrepayId();
                req.nonceStr = outBean.getNonceStr();
                req.timeStamp = outBean.getTimeStamp();
                req.packageValue = outBean.getPackageValue();
                req.sign = outBean.getSign();

                // 在支付之前，如果应用没有注册到微信，应该先调用IWXMsg.registerApp将应用注册到微信
                api.sendReq(req);
                isSentReq = true;
            }
        };
    }

    private void setCouponAndJiaCoinControl() {
        if (isUsedCoupon) {
            evCoupon.setEnabled(false);
            btnCoupon.setEnabled(true);
        } else {
            if (orderCouponJiaCoinOutBean.getOrderAmount() == 0) {
                evCoupon.setEnabled(false);
                btnCoupon.setEnabled(false);
            } else {
                evCoupon.setEnabled(true);
                btnCoupon.setEnabled(true);
            }
        }
        if (isUsedJiaCoin) {
            evJiaCoin.setEnabled(false);
            btnJiaCoin.setEnabled(true);
        } else {
            if (orderCouponJiaCoinOutBean.getOrderAmount() == 0) {
                evJiaCoin.setEnabled(false);
                btnJiaCoin.setEnabled(false);
            } else {
                evJiaCoin.setEnabled(true);
                btnJiaCoin.setEnabled(true);
            }
        }

    }

    @Override
    public View onCreateView(LayoutInflater layoutinflater, ViewGroup viewgroup, Bundle bundle) {
        super.onCreateView(layoutinflater, viewgroup, bundle);

        View view = layoutinflater.inflate(R.layout.order_pay_fragment, viewgroup, false);
        if (null == view) {
            return null;
        }
        Bundle argBundle = getArguments();
        if (null == argBundle) {
            ToastUtil.show(getActivity(), getString(R.string.error_unknown, "没有传入订单信息"));
            return null;
        }
        orderDetailBean = (OrderDetailBean) argBundle.getSerializable(Constants.ArgName.UI.OrderPay.ORDER_DETAIL_BEAN);
        if (null == orderDetailBean) {
            ToastUtil.show(getActivity(), getString(R.string.error_unknown, "没有传入订单信息"));
            return null;
        }
        orderCouponJiaCoinOutBean = new OrderCouponJiaCoinOutBean(0L, 0L, orderDetailBean.getOrderAmount());

        isUsedCoupon = false;
        isUsedJiaCoin = false;
        isSentReq = false;

        TextView tvOrderNo = (TextView) view.findViewById(R.id.tv_order_pay_info_order_no);
        tvOrderNo.setText(orderDetailBean.getOrderNo());

        tvJiaCoinRemain = (TextView) view.findViewById(R.id.tv_order_pay_jia_coin_remain);
        tvOrderFeeRemain = (TextView) view.findViewById(R.id.tv_order_pay_fee);
        tvOrderFeeRemain.setText(getString(R.string.money_format, Utils.Money.MoneyToYuan(orderDetailBean.getOrderAmount())));

        evCoupon = (EditText) view.findViewById(R.id.ev_order_pay_coupon);
        btnCoupon = (Button) view.findViewById(R.id.btn_order_pay_coupon);
        tvCouponUsed = (TextView) view.findViewById(R.id.tv_order_pay_coupon_used);
        tvCouponUsed.setVisibility(View.GONE);

        evJiaCoin = (EditText) view.findViewById(R.id.ev_order_pay_jia_coin);
        btnJiaCoin = (Button) view.findViewById(R.id.btn_order_pay_jia_coin);
        tvJiaCoinUsed = (TextView) view.findViewById(R.id.tv_order_pay_jia_coin_used);
        tvJiaCoinUsed.setVisibility(View.GONE);

        btnJiaCoin.setEnabled(false);
        tvJiaCoinRemain.setText(getString(R.string.order_pay_jia_coin_remain, 0));

        Button payBtn = (Button) view.findViewById(R.id.btn_order_pay_go_to_pay);
        payBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (orderCouponJiaCoinOutBean.getOrderAmount() == 0) {
                    // 优惠券和加币完成支付
                    RequestParams params = new RequestParams();
                    params.put(Constants.ArgName.Request.PayForJiaCoinOrCoupon.ORDER_ID, orderDetailBean.getOrderId());
                    if (isUsedJiaCoin) {
                        params.put(Constants.ArgName.Request.PayForJiaCoinOrCoupon.JIA_CURRENCY_AMT,
                                   orderCouponJiaCoinOutBean.getJiaCurrencyAmt().toString());
                    }
                    if (isUsedCoupon) {
                        params.put(Constants.ArgName.Request.PayForJiaCoinOrCoupon.CON_CODE, getUsedCoupon());
                        params.put(Constants.ArgName.Request.PayForJiaCoinOrCoupon.PREFERENTIAL_AMT,
                                   orderCouponJiaCoinOutBean.getPreferentialAmt().toString());
                    }
                    HttpClient httpClient = HttpClient.getInstall(getActivity());
                    httpClient.postAsync(Constants.NetWorkRequest.PAY_FOR_JIA_MONEY_OR_COUPON_URL_051, params,
                                         payForJiaMoneyOrConCodeResponseHandler);
                } else {
                    // 优惠券和加币部分支付，微信完成支付
                    isSentReq = false;
                    if (!api.isWXAppSupportAPI()) {
                        ToastUtil.show(getActivity(), getString(R.string.not_support_wx_pay));
                        return;
                    }

                    RequestParams params = new RequestParams();
                    params.put(Constants.ArgName.Request.GenProductArgs.ORDER_ID, orderDetailBean.getOrderId());
                    if (isUsedJiaCoin) {
                        params.put(Constants.ArgName.Request.GenProductArgs.JIA_CURRENCY_AMT,
                                   orderCouponJiaCoinOutBean.getJiaCurrencyAmt().toString());
                    }
                    if (isUsedCoupon) {
                        params.put(Constants.ArgName.Request.GenProductArgs.CON_CODE, getUsedCoupon());
                        params.put(Constants.ArgName.Request.GenProductArgs.PREFERENTIAL_AMT,
                                   orderCouponJiaCoinOutBean.getPreferentialAmt().toString());
                    }
                    params.put(Constants.ArgName.Request.GenProductArgs.CLIENT_IP, Utils.Network.getLocalIpAddress());
                    HttpClient httpClient = HttpClient.getInstall(getActivity());
                    httpClient.postAsync(Constants.NetWorkRequest.TO_PAY_FOR_APP_URL_052, params, toWXPayForAppResponseHandler);
                }
            }
        });

        btnCoupon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestParams params = new RequestParams();
                params.put(Constants.ArgName.Request.UseOrCancelCoupon.ORDER_ID, orderDetailBean.getOrderId());
                if (isUsedJiaCoin) {
                    params.put(Constants.ArgName.Request.UseOrCancelCoupon.JIA_CURRENCY_AMT, getUsedJiaCoin());
                }
                HttpClient httpClient = HttpClient.getInstall(getActivity());
                if (isUsedCoupon) {
                    // 取消使用优惠券
                    httpClient.postAsync(Constants.NetWorkRequest.CANCEL_CON_CODE_URL_012, params, cancelConCodeResponseHandler);
                } else {
                    // 使用优惠券
                    String coupon = getUsedCoupon();
                    if (TextUtils.isEmpty(coupon)) {
                        evCoupon.setError(Utils.getErrorSpanString(R.string.error_empty_coupon, getActivity()));
                        evCoupon.requestFocus();
                        return;
                    }
                    params.put(Constants.ArgName.Request.UseOrCancelCoupon.CON_CODE, coupon);
                    httpClient.postAsync(Constants.NetWorkRequest.USE_CON_CODE_URL_039, params, useConCodeResponseHandler);
                }
            }
        });

        btnJiaCoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestParams params = new RequestParams();
                params.put(Constants.ArgName.Request.UseOrCancelJiaCoin.ORDER_ID, orderDetailBean.getOrderId());
                if (isUsedCoupon) {
                    params.put(Constants.ArgName.Request.UseOrCancelJiaCoin.CON_CODE, getUsedCoupon());
                }
                HttpClient httpClient = HttpClient.getInstall(getActivity());
                if (isUsedJiaCoin) {
                    // 取消使用加币
                    httpClient.postAsync(Constants.NetWorkRequest.CANCEL_JIA_MONEY_URL_041, params, cancelJiaCoinResponseHandler);
                } else {
                    // 使用加币
                    String jiaCoin = getUsedJiaCoin();
                    if (TextUtils.isEmpty(jiaCoin) || "0".equals(jiaCoin.trim())) {
                        evJiaCoin.setError(Utils.getErrorSpanString(R.string.error_empty_jia_coin, getActivity()));
                        evJiaCoin.requestFocus();
                    } else{
                        Integer inputJiaCoin = Integer.parseInt(jiaCoin) * 100;
                        if (inputJiaCoin > userJiaCoinRemain) {
                            evJiaCoin.setError(Utils.getErrorSpanString(R.string.error_exceed_jia_coin, getActivity()));
                            evJiaCoin.requestFocus();
                            return;
                        }
                        params.put(Constants.ArgName.Request.UseOrCancelJiaCoin.JIA_CURRENCY_USED_COUNT, jiaCoin);
                        httpClient.postAsync(Constants.NetWorkRequest.USE_JIA_MONEY_URL_040, params, useJiaCoinResponseHandler);
                    }
                }
            }
        });

        HttpClient httpClient = HttpClient.getInstall(getActivity());
        httpClient.postAsync(Constants.NetWorkRequest.JIA_BALANCE_URL_028, null, jiaCoinSearchResponseHandler);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isSentReq) {
            Utils.System.popBackStack(getActivity());
        }
    }

}
