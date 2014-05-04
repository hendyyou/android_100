package cn.digione.yibaic.shop.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.LinearLayout;
import cn.digione.yibaic.shop.R;
import cn.digione.yibaic.shop.ShopApp;
import cn.digione.yibaic.shop.acitvity.BaseActivity;
import cn.digione.yibaic.shop.acitvity.ProductListActivity;
import cn.digione.yibaic.shop.bean.HomeBean;
import cn.digione.yibaic.shop.common.Constants;
import cn.digione.yibaic.shop.http.CustomerJsonHttpResponseHandler;
import cn.digione.yibaic.shop.http.HttpClient;
import cn.digione.yibaic.shop.util.ToastUtil;
import cn.digione.yibaic.shop.util.Utils;

public class HomeFragment extends BaseFragment {
    private static final int BOOK = 0;// 预约
    private LinearLayout mSubscribeLL;
    private WebView mWebView;
    private Button mNextBtn;
    private HomeBean homeBean;

    public static HomeFragment newInstance(String s) {
        HomeFragment newFragment = new HomeFragment();
        Bundle bundle = new Bundle();
        bundle.putString("extraParam", s);
        newFragment.setArguments(bundle);
        return newFragment;
    }

    public BaseActivity getParent() {
        return (BaseActivity) getActivity();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mCustomerJsonHttpResponseHandler = new CustomerJsonHttpResponseHandler<HomeBean>(getParent(), false) {
            @Override
            public void processCallSuccess(HomeBean outBean, String msg) {
                if (outBean != null) {
                    homeBean = outBean;
                    if (homeBean.getIndexUrl() != null) {
                        mWebView.loadUrl(homeBean.getIndexUrl());
                    }
                   /* if (BOOK == outBean.getSellType()) {// 如果是预约
                        mNextBtn.setText(getString(R.string.subscriber));
                    } else {
                        mNextBtn.setText(getString(R.string.buy));
                    }*/
                    mSubscribeLL.setVisibility(View.VISIBLE);
                    mNextBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (getParent().isLogin()) {
                                Intent intent = new Intent(getActivity(), ProductListActivity.class);
                                intent.putExtra("select_product_model", homeBean.getModel());
                                intent.putExtra("product_detail_title", homeBean.getModel());
                                intent.putExtra("select_product_class", homeBean.getProClass());
                                intent.setAction("index_select_product");
                                startActivity(intent);
                            } else {
                                getParent().gotoLogin();
                            }
                        }
                    });
                }
            }
        }

        ;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.product_details_fragment, container, false);
        mSubscribeLL = (LinearLayout) view.findViewById(R.id.ll_f_subscribe_container);
        mWebView = (WebView) view.findViewById(R.id.wv_product_detail_desc);
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        mWebView.setWebViewClient(new WebViewClient() {
            /*
			 * @Override public boolean shouldOverrideUrlLoading(WebView view, String url) { return
			 * super.shouldOverrideUrlLoading(view, url); }
			 */

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                // ToastUtil.show("onPageStarted");
                ShopApp.getInstance().showDialog(getActivity());
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                // ToastUtil.show("onPageFinished");
                ShopApp.getInstance().dismissDialog();
            }
        });

        if (Utils.Network.isHasNetwork(getActivity())) {
            String url = Constants.NetWorkRequest.USER_INDEX_URL_044;
            HttpClient client = HttpClient.getInstall(getParent());
            if (client == null) {
                client = HttpClient.getInstall(getActivity());
            }

            client.postAsync(url, null, mCustomerJsonHttpResponseHandler);
        } else {
            ToastUtil.show(getActivity(), getString(R.string.msg_network_fail), ToastUtil.LENGTH_SHORT);
        }

        mNextBtn = (Button) view.findViewById(R.id.btn_next);
        return view;
    }
}
