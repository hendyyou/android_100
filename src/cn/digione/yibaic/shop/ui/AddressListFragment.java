package cn.digione.yibaic.shop.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import cn.digione.yibaic.shop.R;
import cn.digione.yibaic.shop.ShopApp;
import cn.digione.yibaic.shop.acitvity.AddressManagementActivity;
import cn.digione.yibaic.shop.adapter.AddressListAdapter;
import cn.digione.yibaic.shop.bean.AddressBean;
import cn.digione.yibaic.shop.bean.JsonNoneOutBean;
import cn.digione.yibaic.shop.common.Constants;
import cn.digione.yibaic.shop.http.CustomerJsonHttpResponseHandler;
import cn.digione.yibaic.shop.http.HttpClient;
import cn.digione.yibaic.shop.util.ToastUtil;
import cn.digione.yibaic.shop.util.Utils;

import com.loopj.android.http.RequestParams;

/**
 * 地址列表界面
 * 
 * @author Administrator
 */
public class AddressListFragment extends BaseFragment {

	private static final String TAG = "AddressListFragment";
	private final String MAX_PAGE_SIZE = "999"; // 表示不分页
	private AddressListAdapter mAdapter;
	private ListView mListView;
	private HttpClient client;
	private CustomerJsonHttpResponseHandler<List<AddressBean>> addressHttpResponseHandler;
	private CustomerJsonHttpResponseHandler<JsonNoneOutBean> deleteJsonHttpResponseHandler;
	private CustomerJsonHttpResponseHandler<JsonNoneOutBean> setDefaultJsonHttpResponseHandler;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Intent intent = getActivity().getIntent();
		final boolean isSelect = intent == null ? false : intent.getBooleanExtra("isSelect", false);
		addressHttpResponseHandler = new CustomerJsonHttpResponseHandler<List<AddressBean>>(getActivity()) {

			@Override
			public void processCallSuccess(List<AddressBean> outBean, String msg) {
				// TODO Auto-generated method stub
				if(outBean==null){
					outBean=new ArrayList<AddressBean>();
				} 			
				if (outBean != null) {
					AddressBean tailBean = new AddressBean();
					tailBean.setAddress(getString(R.string.checkout_button_add_address));
					outBean.add(tailBean);
					mAdapter = new AddressListAdapter((AddressManagementActivity) getActivity(), outBean, isSelect,
							deleteJsonHttpResponseHandler, setDefaultJsonHttpResponseHandler);
					mListView.setAdapter(mAdapter);
				}
			}
		};
		deleteJsonHttpResponseHandler = new CustomerJsonHttpResponseHandler<JsonNoneOutBean>(getActivity()) {

			@Override
			public void processCallSuccess(JsonNoneOutBean outBean, String msg) {
				// TODO Auto-generated method stub
				mAdapter.notifyDataSetChanged();
				ShopApp.setListViewHeightBasedOnChildren(mListView);
			}
		};
		setDefaultJsonHttpResponseHandler = new CustomerJsonHttpResponseHandler<JsonNoneOutBean>(getActivity()) {

			@Override
			public void processCallSuccess(JsonNoneOutBean outBean, String msg) {
				/*
				 * Intent intentParam = getActivity().getIntent(); final String jcode = intentParam == null ? null :
				 * intentParam.getStringExtra("JCODE"); Intent intent = new Intent(getParent(), ShoppingActivity.class);
				 * intent.putExtra("select_address_back_checkout", "checkout"); if (jcode != null && jcode.trim().length() >
				 * 0) { intent.putExtras(intentParam.getExtras()); } startActivity(intent);
				 */
				getParent().finish();
			}
		};
	}

	private AddressManagementActivity getParent() {
		return (AddressManagementActivity) getActivity();
	}

	public View onCreateView(LayoutInflater layoutinflater, ViewGroup viewgroup, Bundle bundle) {
		super.onCreateView(layoutinflater, viewgroup, bundle);
		View view;
		view = layoutinflater.inflate(R.layout.address_list_fragment, viewgroup, false);

		mListView = (ListView) view.findViewById(R.id.address_listview);

		if (Utils.Network.isHasNetwork(getActivity())) {
			String url = Constants.NetWorkRequest.QUERY_ADDRESS_015;
			if (client == null) {
				client = HttpClient.getInstall(getActivity());
			}
			HashMap<String, String> paramMap = new HashMap<String, String>();
			RequestParams params = new RequestParams(paramMap);
			params.put("consigneeAddressVO.currentPage", "1");
			params.put("consigneeAddressVO.pageSize", MAX_PAGE_SIZE); // 表示不分页
			client.postAsync(url, params, addressHttpResponseHandler);
		} else {
			ToastUtil.show(getActivity(), getString(R.string.msg_network_fail), ToastUtil.LENGTH_SHORT);
		}

		return view;

	}
}