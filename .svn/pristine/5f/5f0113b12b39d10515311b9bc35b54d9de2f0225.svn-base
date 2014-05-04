package cn.digione.yibaic.shop.ui;

import java.util.List;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import cn.digione.yibaic.shop.R;
import cn.digione.yibaic.shop.adapter.CouponListAdapter;
import cn.digione.yibaic.shop.bean.CouponBean;
import cn.digione.yibaic.shop.common.Constants;
import cn.digione.yibaic.shop.http.CustomerJsonHttpResponseHandler;
import cn.digione.yibaic.shop.http.HttpClient;
import cn.digione.yibaic.shop.util.ToastUtil;
import cn.digione.yibaic.shop.util.Utils;

/**
 * 订单列表界面
 * 
 * @author Administrator
 * 
 */
public class CouponQueryListFragment extends BaseFragment {

	private static final String TAG = "CouponListFragment";
	private String mAction;
	private CouponListAdapter mAdapter;
	private ListView mListView;
	private ImageView noCouponView;
	private int mType;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		mCustomerJsonHttpResponseHandler = new CustomerJsonHttpResponseHandler<List<CouponBean>>(getActivity()) {

			@Override
			public void processCallSuccess(List<CouponBean> outBean, String msg) {
				// TODO Auto-generated method stub
				if (outBean != null && outBean.size() > 0) {
					noCouponView.setVisibility(View.GONE);
					mAdapter = null;
					mAdapter = new CouponListAdapter(getActivity(), outBean);
					mListView.setAdapter(mAdapter);
				} else {
					noCouponView.setVisibility(View.VISIBLE);
				}
			}
		};

	}

	private void requestHttp() {
		if (Utils.Network.isHasNetwork(getActivity())) {
			HttpClient client = HttpClient.getInstall(getActivity());
			String url = Constants.NetWorkRequest.SEARCH_COUPON_LIST_URL_039;
			client.postAsync(url, null, mCustomerJsonHttpResponseHandler);
		} else {
			ToastUtil.show(getActivity(), getString(R.string.msg_network_fail), ToastUtil.LENGTH_SHORT);
		}
	}

	public View onCreateView(LayoutInflater layoutinflater, ViewGroup viewgroup, Bundle bundle) {
		super.onCreateView(layoutinflater, viewgroup, bundle);
		View view;
		view = layoutinflater.inflate(R.layout.coupon_list_fragment, viewgroup, false);
		mListView = (ListView) view.findViewById(R.id.coupon_listview);
		noCouponView = (ImageView) view.findViewById(R.id.no_coupon_view);
		requestHttp();

		return view;

	}

	public void setAction(String s) {
		mAction = s;
	}

	public void setType(int s) {
		mType = s;
	}

}