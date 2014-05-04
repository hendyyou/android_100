package cn.digione.yibaic.shop.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import cn.digione.yibaic.shop.R;
import cn.digione.yibaic.shop.acitvity.FMaBuyActivity;
import cn.digione.yibaic.shop.acitvity.OrderListActivity;
import cn.digione.yibaic.shop.acitvity.ProductListActivity;
import cn.digione.yibaic.shop.acitvity.ShoppingActivity;
import cn.digione.yibaic.shop.adapter.ProductColorAdapter;
import cn.digione.yibaic.shop.bean.AmSucceedBean;
import cn.digione.yibaic.shop.bean.JsonPagerEntityBean;
import cn.digione.yibaic.shop.bean.ProductColorBean;
import cn.digione.yibaic.shop.common.Constants;
import cn.digione.yibaic.shop.http.CustomerJsonHttpResponseHandler;
import cn.digione.yibaic.shop.http.HttpClient;
import cn.digione.yibaic.shop.util.ToastUtil;
import com.loopj.android.http.RequestParams;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductColorFragment extends BaseFragment<JsonPagerEntityBean<ProductColorBean>>
        implements ProductColorAdapter.ProductOperatorListener {
    private static final String NOT_PREPAY = "1"; // 不支持预支付
    private ProductColorAdapter mColorSelectAdapter;
    private List<ProductColorBean> mDatas = null;
    private ListView mProductListLV;
    private String mProModel;
    private int mProClass;
    private String mJcode;
    private AlertDialog alertDialog;
    private CustomerJsonHttpResponseHandler<ProductColorBean> mAddShopCartHttpResponseHandler;
    private CustomerJsonHttpResponseHandler<AmSucceedBean> mAppointMentHttpResponseHandler;

    public ProductListActivity getParent() {
        return (ProductListActivity) getActivity();
    }

    public void requestHttp(String url, Map<String, String> map, CustomerJsonHttpResponseHandler httpResponseHandler) {
        HttpClient client = HttpClient.getInstall(getParent());
        RequestParams params = new RequestParams(map);
        client.postAsync(url, params, httpResponseHandler);
    }

    private void productColorSelectRequestHttp() {
        String url = Constants.NetWorkRequest.PRODUCT_COLOR_LIST_URL_003;
        Map<String, String> map = new HashMap<String, String>();
        map.put("searchProductVO.proModel", mProModel);
        if (mProClass != 0) {
            map.put("searchProductVO.proClass", mProClass + "");
        }
        requestHttp(url, map, mCustomerJsonHttpResponseHandler);
    }

    private void addShopCartRequestHttp(String productId, String num) {
        String url = Constants.NetWorkRequest.SHOPPING_ADD_CART_URL_031;
        Map<String, String> map = new HashMap<String, String>();
        map.put("productId", productId);
        map.put("num", num);
        requestHttp(url, map, mAddShopCartHttpResponseHandler);
    }

    private void submitAppointment(String productId) {
        String url = Constants.NetWorkRequest.CREATE_USER_APPOINTMENT_URL_041;
        Map<String, String> map = new HashMap<String, String>();
        map.put("searchAppointmentVO.proID", productId);
        // map.put("searchAppointmentVO.appointmentID", appointmentId);
        requestHttp(url, map, mAppointMentHttpResponseHandler);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            mProModel = args.getString("select_product_model");
            mJcode = args.getString("JCODE");
            mProClass = args.getInt("select_product_class");

            getParent().setTitle(mProModel);
        }
        mCustomerJsonHttpResponseHandler =
                new CustomerJsonHttpResponseHandler<JsonPagerEntityBean<ProductColorBean>>(getParent(), false) {
                    @Override
                    public void processCallSuccess(final JsonPagerEntityBean<ProductColorBean> outBean, final String msg) {
                        if (outBean != null && outBean.getResultList().size() > 0) {
                            mDatas = outBean.getResultList();
                            mProductListLV.setSelection(mSelectposition);
                            mColorSelectAdapter = null;
                            mColorSelectAdapter = new ProductColorAdapter(getParent(), mDatas, mJcode);
                            mColorSelectAdapter.setOnProductOperatorListener(ProductColorFragment.this);
                            mProductListLV.setAdapter(mColorSelectAdapter);
                            // mProductListLV.setOnScrollListener(ProductColorFragment.this);
                            // ShopApp.setListViewHeightBasedOnChildren(mProductListLV);
                        }
                    }
                };
        mAddShopCartHttpResponseHandler = new CustomerJsonHttpResponseHandler<ProductColorBean>(getParent(), false) {
            @Override
            public void processCallSuccess(ProductColorBean outBean, String msg) {
                if (outBean != null) {
                    getParent().updateShoppingCountRequestHttp();
                    if (outBean.getProClass() != 1) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(getParent());
                        builder.setTitle(getString(R.string.plus_shopping_cart_success));
                        builder.setMessage(getString(R.string.plus_shopping_cart_hint));
                        builder.setPositiveButton(getString(R.string.go_shopping_cart),
                                                  new DialogInterface.OnClickListener() {

                                                      @Override
                                                      public void onClick(DialogInterface mDialogInterface, int which) {
                                                          Intent intent = new Intent(getActivity(), ShoppingActivity.class);
                                                          startActivity(intent);
                                                      }
                                                  }
                        );
                        builder.setNegativeButton(getString(R.string.go_buy), new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface mDialogInterface, int which) {
                                if (alertDialog != null) {
                                    alertDialog.dismiss();
                                }
                            }
                        });
                        alertDialog = builder.create();
                        alertDialog.show();
                    } else {
                        Intent intent = new Intent(getActivity(), ShoppingActivity.class);
                        startActivity(intent);
                    }
                }
            }
        };

        mAppointMentHttpResponseHandler = new CustomerJsonHttpResponseHandler<AmSucceedBean>(getParent(), false) {
            @Override
            public void processCallSuccess(final AmSucceedBean outBean, String msg) {
                if (NOT_PREPAY.equals(outBean.getIsPrepaid())) {
                    //不支持预付的，就去我的预约界面。
                    Intent intent = new Intent(getActivity(), OrderListActivity.class);
                    intent.setAction("book_list");
                    startActivity(intent);
                    getParent().finish();
                    return;
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(getParent());
                builder.setTitle(getString(R.string.appointment_success));
                builder.setMessage(getString(R.string.appointment_hint));
                builder.setPositiveButton(getString(R.string.pay_now), new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface mDialogInterface, int which) {
                        // 去确认订单
                        Intent intent = new Intent(getParent(), ShoppingActivity.class);
                        intent.putExtra("jcode_product_id", outBean.getProId());
                        intent.putExtra("is_prepaid", "is_prepaid");
                        intent.setAction("index_select_product");
                        startActivity(intent);
                        getParent().finish();
                    }
                });
                builder.setNegativeButton(getString(R.string.go_no_appointment), new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface mDialogInterface, int which) {
                        if (alertDialog != null) {
                            alertDialog.dismiss();
                            Intent intent = new Intent(getActivity(), OrderListActivity.class);
                            intent.setAction("book_list");
                            startActivity(intent);
                            getParent().finish();
                        }
                    }
                });
                alertDialog = builder.create();
                alertDialog.show();

            }
        };

    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        productColorSelectRequestHttp();
        View view = inflater.inflate(R.layout.product_color_list_fragment, container, false);
        mProductListLV = (ListView) view.findViewById(R.id.lv_product_list);
        /*
         * mProductListLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
		 * 
		 * @Override public void onItemClick(AdapterView<?> parent, View view, int position,
		 * long id) { mSelectedItemPosition
		 * = position; } });
		 * 
		 * mProductListLV.setOnScrollListener(this);
		 * 
		 * submitBtn = (Button) view.findViewById(R.id.btn_submit); submitBtn.setOnClickListener(new View.OnClickListener ()
		 * {
		 * 
		 * @Override public void onClick(View v) { if (mSelectedItemPosition >= 0) { // 提交到服务器的数据库中，然后 // 可购买
		 * ProductColorBean colorBean = mDatas.get(mSelectedItemPosition); if (mJcode != null && mJcode != "" &&
		 * !mJcode.equals("")) { // 可购买 if ("1".equals(colorBean.getSellType())) { ToastUtil.show("product id =" +
		 * colorBean.getId()); Intent intent = new Intent(getActivity(), ShoppingActivity.class); intent.putExtra("JCODE",
		 * mJcode); startActivity(intent); } } else { // 可购买 if ("1".equals(colorBean.getSellType())) {
		 * 
		 * // ToastUtil.show(mSelectedItemPosition + ":" + colorBean.getProName()); // 可预约 } else if
		 * ("0".equals(colorBean.getSellType())) {
		 * 
		 * } } } else { ToastUtil.show(R.string.please_choose_product); } } });
		 */
        return view;
    }

    @Override
    public void gerBuy(int position, int productId) {
        // ToastUtil.show("product id =" + productId);
        addShopCartRequestHttp(String.valueOf(productId), "1");
    }

    @Override
    public void appointment(int productId) {
        submitAppointment(String.valueOf(productId));
    }

    @Override
    public void fMaBuy(int position, int productId) {
        ProductColorBean productColorBean = mDatas.get(position);
        if (productColorBean != null) {
            Intent intent = new Intent(getActivity(), FMaBuyActivity.class);
            intent.putExtra("jcode_product_id", productColorBean.getId() + "");
            startActivity(intent);
        } else {
            ToastUtil.show(R.string.after_visit);
        }
    }

    public void pay(String proId) {
        Intent intent = new Intent(getActivity(), ShoppingActivity.class);
        intent.putExtra("jcode_product_id", proId);
        intent.putExtra("is_prepaid", "is_prepaid");
        intent.setAction("index_select_product");
        startActivity(intent);
    }
}
