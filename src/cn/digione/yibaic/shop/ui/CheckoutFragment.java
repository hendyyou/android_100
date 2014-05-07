package cn.digione.yibaic.shop.ui;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import cn.digione.yibaic.shop.R;
import cn.digione.yibaic.shop.acitvity.AddressManagementActivity;
import cn.digione.yibaic.shop.acitvity.ShoppingActivity;
import cn.digione.yibaic.shop.bean.*;
import cn.digione.yibaic.shop.common.Constants;
import cn.digione.yibaic.shop.common.SerializableMap;
import cn.digione.yibaic.shop.http.CustomerJsonHttpResponseHandler;
import cn.digione.yibaic.shop.http.HttpClient;
import cn.digione.yibaic.shop.util.ToastUtil;
import com.loopj.android.http.RequestParams;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA. User: youzh Date: 14-3-12 Time: 下午5:52
 */
public class CheckoutFragment extends BaseFragment {

    private TextView mAddressBottom;
    private TextView mAddressMiddle;
    private TextView mAddressUp;
    private RelativeLayout mCheckoutAddressEmpty;
    private RelativeLayout mCheckoutAddress;
    private TextView mCheckoutHint;
    private LinearLayout mCheckoutHintContainer;
    private LinearLayout mCheckoutInfoContainer;
    private ScrollView mContainer;
    private RadioGroup mDelivertimeGroup;
    private RadioGroup mInvoiceGroup;
    private EditText mInvoiceTitleET;
    private LinearLayout mInvoiceTitleBgLL;
    private EditText mBuyAddr;
    private EditText mBuyConsignee;
    private EditText mBuyTel;
    private TextView mNext;
    private LinearLayout mWrongLL;
    private TextView mWrongTV;
    private RadioGroup mPaymentGroup;
    private RadioGroup mShipmentGroup;
    private ShoppingCartOrderBean mShoppingCartOrderBean;
    private Map<String, String> mParamMap;
    private String mJcodeProductId = null;
    private String mJcode = null;
    private View mView;
    private String mAddressId = null;
    private String mIsPrepaid = null;

    public ShoppingActivity getParent() {
        return (ShoppingActivity) getActivity();
    }

    private void onNext() {
        String invoceType = mParamMap.get(Constants.ShopCartOrder.ORDER_ISINVOICE);
        if (TextUtils.equals("1", invoceType)) {
            String invoiceTitle = mInvoiceTitleET.getText().toString();
            if (invoiceTitle != null && !invoiceTitle.trim().equals("")) {
                mParamMap.put(Constants.ShopCartOrder.ORDER_INVOICETITLE, invoiceTitle);
            } else {
                ToastUtil.show(R.string.input_invoice_title_hint);
                return;
            }
        }

        if (mAddressId == null || "".equals(mAddressId.trim())) {
            ToastUtil.show(R.string.input_select_address_hint);
            return;
        }
        Bundle bundle = new Bundle();
        SerializableMap serializableMap = new SerializableMap();
        serializableMap.setMap(mParamMap);
        bundle.putSerializable("submit_order_param", serializableMap);
        bundle.putSerializable("submit_order_cartobj", mShoppingCartOrderBean);
        if (mJcode != null && mJcodeProductId != null) {
            bundle.putString("JCODE", mJcode);
            bundle.putString("jcode_productId", mJcodeProductId);
        }

        if ("is_prepaid".equals(mIsPrepaid) && mJcodeProductId != null) {
            bundle.putString("is_prepaid", "is_prepaid");
            bundle.putString("jcode_productId", mJcodeProductId);
        }
        getParent().showFragment("order_submit_fragment", bundle, true);
    }

    private void openAddressActivity() {
        Intent intent = new Intent(getActivity(), AddressManagementActivity.class);
        intent.putExtra("isSelect", true);
        startActivity(intent);
    }

    private void removeData() {
        mCheckoutAddress.setTag("");
        mAddressUp.setText("");
        mAddressMiddle.setText("");
        mAddressBottom.setText("");
        mPaymentGroup.removeAllViews();
        mShipmentGroup.removeAllViews();
        mInvoiceGroup.removeAllViews();
        mDelivertimeGroup.removeAllViews();
    }

    public void requestHttp(String url, RequestParams params) {
        HttpClient client = HttpClient.getInstall(getParent());
        client.postAsync(url, params, mCustomerJsonHttpResponseHandler);
    }

    private void checkOrderHttpRequest() {
        String url = Constants.NetWorkRequest.SHOPPING_CART_ORDER_CONFIRM_URL_036;
        RequestParams params = null;
        if (mJcode != null && mJcode.trim().length() > 0 && mJcodeProductId != null) {
            Map<String, String> map = new HashMap<String, String>();
            map.put("jcode", mJcode);
            map.put("productId", mJcodeProductId);
            /*
             * map.put("productVO.proClass",mJcodeProductId.getProClass()+"");
			 * map.put("productVO.proBrand",mJcodeProductId.getProBrand());
			 * map.put("productVO.proModel",mJcodeProductId.getProModel());
			 * map.put("productVO.proColor",mJcodeProductId.getProColor());
			 * map.put("productVO.proRom",mJcodeProductId.getProRom());
			 * map.put("productVO.proStandardName",mJcodeProductId.getProStandardName());
			 */
            params = new RequestParams(map);
        }

        if ("is_prepaid".equals(mIsPrepaid) && mJcodeProductId != null) {
            Map<String, String> map = new HashMap<String, String>();
            map.put("productId", mJcodeProductId);
            params = new RequestParams(map);
        }
        requestHttp(url, params);
    }

    private void setAddress(AddressBean addressBean) {
        if (addressBean != null) {
            mCheckoutAddress.setVisibility(View.VISIBLE);
            mCheckoutAddressEmpty.setVisibility(View.GONE);
            String addressUp =
                    addressBean.getProvinceName() + " " + addressBean.getCityName() + " " + addressBean.getCountyName();
            mAddressUp.setText(addressUp);
            String postCode = addressBean.getPostcode();
            String addressMiddle = addressBean.getAddress();
            if (addressBean.getPostcode() != null) {
                addressMiddle = addressMiddle + "(" + postCode + ")";
            }
            mAddressMiddle.setText(addressMiddle);
            String addressBottom = addressBean.getName() + " " + addressBean.getMobile();
            mAddressBottom.setText(addressBottom);
            mAddressId = addressBean.getId();
            if (mAddressId != null && !"".equals(mAddressId.trim())) {
                mParamMap.put(Constants.ShopCartOrder.ORDER_CONSIGNEEADDRESSID, mAddressId);
            } else {
                mParamMap.put(Constants.ShopCartOrder.ORDER_CONSIGNEE, addressBean.getName());
                String fullAddre = addressBean.getProvinceName() + addressBean.getCityName() + addressBean.getCountyName() +
                                   addressBean.getAddress();
                mParamMap.put(Constants.ShopCartOrder.ORDER_CONSIGNEEADDRESS, fullAddre);
                mParamMap.put(Constants.ShopCartOrder.ORDER_CONSIGNEEMOBILE, addressBean.getMobile());
                mParamMap.put(Constants.ShopCartOrder.ORDER_CONSIGNEEPHONE, addressBean.getPhone());
                mParamMap.put(Constants.ShopCartOrder.ORDER_CONSIGNEEPOSTCODE, addressBean.getPostcode());
                mParamMap.put(Constants.ShopCartOrder.ORDER_AREAID, addressBean.getCountyId());
            }
        } else {
            mCheckoutAddress.setVisibility(View.GONE);
            mCheckoutAddressEmpty.setVisibility(View.VISIBLE);
        }
    }

    private void addPaymentRadioBnts(RadioGroup radiogroup, final List<PaymentBean> payments) {
        final int size = payments.size();
        int checkedRadioButtonId = radiogroup.getCheckedRadioButtonId();
        if (checkedRadioButtonId < 0) {
            checkedRadioButtonId = 1;
        }
        RadioButton radiobutton = null;
        if (payments != null && payments.size() > 0) {
            for (int i = 0; i < size; i++) {
                PaymentBean paymentBean = payments.get(i);
                String tag = paymentBean.getId() + paymentBean.getContent();
                radiobutton = new RadioButton(getActivity());
                radiobutton.setText(paymentBean.getContent());
                radiobutton.setTag(tag);
                radiobutton.setId(i + 1);
                radiogroup.addView(radiobutton);
                if (size == 1) {
                    radiobutton.setBackgroundResource(R.drawable.radiobutton_single_bg);
                } else if (i == 0) {
                    radiobutton.setBackgroundResource(R.drawable.radiobutton_up_bg);
                } else if (i == i - 1) {
                    radiobutton.setBackgroundResource(R.drawable.radiobutton_bottom_bg);
                } else {
                    radiobutton.setBackgroundResource(R.drawable.radiobutton_middle_bg);
                }

                radiobutton.setButtonDrawable(new ColorDrawable(0));
                radiobutton.setLayoutParams(new android.widget.RadioGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                                                                       ViewGroup.LayoutParams.WRAP_CONTENT));

                radiogroup.setOnCheckedChangeListener(new android.widget.RadioGroup.OnCheckedChangeListener() {
                    public void onCheckedChanged(RadioGroup radiogroup, int j) {
                        // ToastUtil.show("select id=" + j + "obj info:" + payments.get(j - 1).getContent());
                        if (j > 0 && j <= size) {
                            mParamMap.put(Constants.ShopCartOrder.ORDER_PAYMENT, payments.get(j - 1).getId() + "");
                        }
                    }
                });
                radiogroup.check(i + 1);
            }
            radiogroup.check(checkedRadioButtonId);
        }
    }

    private void setPayment(List<PaymentBean> payments) {
        if (payments != null && payments.size() > 0) {
            mPaymentGroup.removeAllViews();
            addPaymentRadioBnts(mPaymentGroup, payments);
        }
    }

    private void addDeliveryRadioBtns(RadioGroup radiogroup, final List<DeliveryBean> deliverys) {
        final int size = deliverys.size();
        int checkedRadioButtonId = radiogroup.getCheckedRadioButtonId();
        if (checkedRadioButtonId < 0) {
            checkedRadioButtonId = 1;
        }
        RadioButton radiobutton = null;
        if (deliverys != null && deliverys.size() > 0) {
            for (int i = 0; i < size; i++) {
                DeliveryBean deliveryBean = deliverys.get(i);
                String tag = deliveryBean.getId() + deliveryBean.getContent();
                radiobutton = new RadioButton(getActivity());
                radiobutton.setText(deliveryBean.getContent());
                radiobutton.setTag(tag);
                radiobutton.setId(i + 1);
                radiogroup.addView(radiobutton);
                if (size == 1) {
                    radiobutton.setBackgroundResource(R.drawable.radiobutton_single_bg);
                } else if (i == 0) {
                    radiobutton.setBackgroundResource(R.drawable.radiobutton_up_bg);
                } else if (i == i - 1) {
                    radiobutton.setBackgroundResource(R.drawable.radiobutton_bottom_bg);
                } else {
                    radiobutton.setBackgroundResource(R.drawable.radiobutton_middle_bg);
                }

                radiobutton.setButtonDrawable(new ColorDrawable(0));
                radiobutton.setLayoutParams(new android.widget.RadioGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                                                                       ViewGroup.LayoutParams.WRAP_CONTENT));

                radiogroup.setOnCheckedChangeListener(new android.widget.RadioGroup.OnCheckedChangeListener() {
                    public void onCheckedChanged(RadioGroup radiogroup, int j) {
                        // ToastUtil.show("select id=" + j + "obj info:" + deliverys.get(j - 1).getContent());
                        if (j > 0 && j <= size) {
                            mParamMap.put(Constants.ShopCartOrder.ORDER_DELIVERYPERIOD, deliverys.get(j - 1).getId() + "");
                        }
                    }
                });
                radiogroup.check(i + 1);
            }
            radiogroup.check(checkedRadioButtonId);
        }
    }

    private void setDelivery(List<DeliveryBean> deliverys) {
        if (deliverys != null && deliverys.size() > 0) {
            mDelivertimeGroup.removeAllViews();
            addDeliveryRadioBtns(mDelivertimeGroup, deliverys);
        }
    }

    private void addInvoiceRadioBtns(RadioGroup radiogroup, final List<InvoiceBean> invoices) {
        final int size = invoices.size();
        int checkedRadioButtonId = radiogroup.getCheckedRadioButtonId();
        if (checkedRadioButtonId < 0) {
            checkedRadioButtonId = 1;
        }
        RadioButton radiobutton = null;
        if (invoices != null && invoices.size() > 0) {
            for (int i = 0; i < size; i++) {
                InvoiceBean invoiceBean = invoices.get(i);
                String tag = invoiceBean.getId() + invoiceBean.getContent();
                radiobutton = new RadioButton(getActivity());
                radiobutton.setText(invoiceBean.getContent());
                radiobutton.setTag(tag);
                radiobutton.setId(i + 1);
                radiogroup.addView(radiobutton);
                if (size == 1) {
                    radiobutton.setBackgroundResource(R.drawable.radiobutton_single_bg);
                } else if (i == 0) {
                    radiobutton.setBackgroundResource(R.drawable.radiobutton_up_bg);
                } else if (i == i - 1) {
                    radiobutton.setBackgroundResource(R.drawable.radiobutton_bottom_bg);
                } else {
                    radiobutton.setBackgroundResource(R.drawable.radiobutton_middle_bg);
                }

                radiobutton.setButtonDrawable(new ColorDrawable(0));
                radiobutton.setLayoutParams(new android.widget.RadioGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                                                                       ViewGroup.LayoutParams.WRAP_CONTENT));

                radiogroup.setOnCheckedChangeListener(new android.widget.RadioGroup.OnCheckedChangeListener() {
                    public void onCheckedChanged(RadioGroup radiogroup, int j) {
                        if (j > 0 && j <= size) {
                            if (j == size) {
                                mInvoiceTitleBgLL.setVisibility(View.VISIBLE);
                            } else {
                                mInvoiceTitleBgLL.setVisibility(View.GONE);
                            }
                            // ToastUtil.show("select id=" + j + "obj info:" + invoices.get(j - 1).getContent());
                            mParamMap.put(Constants.ShopCartOrder.ORDER_ISINVOICE, invoices.get(j - 1).getId() + "");
                        }
                    }
                });
                radiogroup.check(i + 1);
            }
            radiogroup.check(checkedRadioButtonId);
        }
    }

    private void setInvoice(List<InvoiceBean> invoices) {
        if (invoices != null && invoices.size() > 0) {
            mInvoiceGroup.removeAllViews();
            addInvoiceRadioBtns(mInvoiceGroup, invoices);
        }
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (mParamMap == null) {
            mParamMap = new HashMap<String, String>();
        }
        mJcodeProductId = getArguments().getString("jcode_product_id");
        mJcode = getArguments().getString("JCODE");
        mIsPrepaid = getArguments().getString("is_prepaid");
        mCustomerJsonHttpResponseHandler = new CustomerJsonHttpResponseHandler<ShoppingCartOrderBean>(getParent()) {
            @Override
            public void processCallSuccess(ShoppingCartOrderBean outBean, String msg) {
                if (outBean != null) {
                    mContainer.setVisibility(View.VISIBLE);
                    mWrongLL.setVisibility(View.GONE);
                    if (mParamMap == null) {
                        mParamMap = new HashMap<String, String>();
                    }
                    mShoppingCartOrderBean = outBean;
                    // 判断地址信息
                    AddressBean addressBean = outBean.getAddressVO();
                    setAddress(addressBean);
                    // 支付方式
                    List<PaymentBean> payments = outBean.getPaymentVO();
                    setPayment(payments);
                    // 配送方式
                    // 送货时间
                    List<DeliveryBean> deliverys = outBean.getDeliveryVO();
                    setDelivery(deliverys);
                    // 发票信息
                    List<InvoiceBean> invoices = outBean.getInvoiceVO();
                    setInvoice(invoices);
                } else {
                    mCheckoutAddress.setVisibility(View.GONE);
                    mCheckoutAddressEmpty.setVisibility(View.VISIBLE);
                    mContainer.setVisibility(View.GONE);
                    mWrongLL.setVisibility(View.VISIBLE);
                    mWrongTV.setText(msg.replaceAll("\\|", "\n"));
                }
            }

            @Override
            public void processCallFailure(ShoppingCartOrderBean outBean, String failureCode, String msg) {
                mContainer.setVisibility(View.GONE);
                mWrongLL.setVisibility(View.VISIBLE);
                mWrongTV.setText(msg.replaceAll("\\|", "\n"));
            }
        };
    }

    public View onCreateView(LayoutInflater layoutinflater, ViewGroup viewgroup, Bundle bundle) {
        super.onCreateView(layoutinflater, viewgroup, bundle);
        getParent().setTitle(R.string.user_checkout);
        mView = layoutinflater.inflate(R.layout.checkout_fragment, viewgroup, false);
        mCheckoutInfoContainer = (LinearLayout) mView.findViewById(R.id.ll_checkout_info_container);
        mCheckoutHintContainer = (LinearLayout) mView.findViewById(R.id.ll_checkout_hint_container);
        mCheckoutHint = (TextView) mView.findViewById(R.id.tv_checkout_hint);
        mAddressUp = (TextView) mView.findViewById(R.id.tv_checkout_address_up);
        mAddressMiddle = (TextView) mView.findViewById(R.id.tv_checkout_address_middle);
        mAddressBottom = (TextView) mView.findViewById(R.id.tv_checkout_address_bottom);
        // mLoadingView = (EmptyLoadingView)mView.findViewById(2131296273);
        mNext = (TextView) mView.findViewById(R.id.tv_next);
        mPaymentGroup = (RadioGroup) mView.findViewById(R.id.rg_checkout_form_radiogroup_payment);
        // mPaymentGroup.setOnCheckedChangeListener(this);
        mShipmentGroup = (RadioGroup) mView.findViewById(R.id.rg_checkout_form_radiogroup_shipment);
        // mShipmentGroup.setOnCheckedChangeListener(this);
        mDelivertimeGroup = (RadioGroup) mView.findViewById(R.id.rg_checkout_form_radiogroup_delivertime);
        // mDelivertimeGroup.setOnCheckedChangeListener(this);
        mInvoiceGroup = (RadioGroup) mView.findViewById(R.id.rg_checkout_form_radiogroup_invoice);
        // mInvoiceGroup.setOnCheckedChangeListener(this);
        mInvoiceTitleET = (EditText) mView.findViewById(R.id.et_checkout_form_invoice_title);
        mInvoiceTitleBgLL = (LinearLayout) mView.findViewById(R.id.ll_checkout_form_invoice_title_bg);

        mCheckoutAddressEmpty = (RelativeLayout) mView.findViewById(R.id.rl_checkout_address_empty);
        mCheckoutAddressEmpty.setVisibility(View.GONE);
        mCheckoutAddress = (RelativeLayout) mView.findViewById(R.id.rl_checkout_address);
        mCheckoutAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAddressActivity();
            }
        });
        mCheckoutAddressEmpty.setOnClickListener(new android.view.View.OnClickListener() {

            public void onClick(View view1) {
                openAddressActivity();
            }

        });
        mNext.setOnClickListener(new android.view.View.OnClickListener() {

            public void onClick(View view1) {
                onNext();
            }
        });
        // mView = mView;

		/*
		 * getActivity().getHomeButton().setOnClickListener(new android.mView.View.OnClickListener() {
		 * 
		 * public void onClick(View view1) { getActivity().onBackPressed(); }
		 * 
		 * });
		 */
        mContainer = (ScrollView) mView.findViewById(R.id.sv_checkout_container);
        mBuyAddr = (EditText) mView.findViewById(R.id.et_checkout_addr);
        mBuyConsignee = (EditText) mView.findViewById(R.id.et_checkout_addr_consignee);
        mBuyTel = (EditText) mView.findViewById(R.id.et_checkout_addr_tel);
        mWrongLL = (LinearLayout) mView.findViewById(R.id.ll_checkout_error_container);
        mWrongTV = (TextView) mView.findViewById(R.id.tv_error_msg);
        mContainer.setVisibility(View.GONE);
        mWrongLL.setVisibility(View.GONE);
        /*
		 * if (mIsShopping) { mView.findViewById(R.id.ll_checkout_form_delivertime).setVisibility(View.GONE);
		 * mView.findViewById(R.id.ll_checkout_addr_layout).setVisibility(View.VISIBLE); }
		 */
        return mView;
    }

    @Override
    public void onResume() {
        super.onResume();
        checkOrderHttpRequest();
        mView.invalidate();
    }

    @Override
    public void onStop() {
        super.onStop();
        mPaymentGroup.clearCheck();
        // mShipmentGroup.clearCheck();
        mDelivertimeGroup.clearCheck();
        mInvoiceGroup.clearCheck();
    }
}