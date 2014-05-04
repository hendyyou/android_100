package cn.digione.yibaic.shop.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import cn.digione.yibaic.shop.R;
import cn.digione.yibaic.shop.acitvity.MainActivity;
import cn.digione.yibaic.shop.adapter.CategoryListAdapter;
import cn.digione.yibaic.shop.bean.CategoryGoodsBean;
import cn.digione.yibaic.shop.common.Constants;
import cn.digione.yibaic.shop.http.CustomerJsonHttpResponseHandler;
import cn.digione.yibaic.shop.http.HttpClient;

import java.util.ArrayList;
import java.util.List;

public class CategoryFragment extends BaseFragment<ArrayList<CategoryGoodsBean>> {
	private CategoryListAdapter mCategoryListAdapter;
	private List<CategoryGoodsBean> mDatas = null;
	private ListView mCategoryLV;
	private HttpClient mClient;

	public static CategoryFragment newInstance(String s) {
		CategoryFragment newFragment = new CategoryFragment();
		Bundle bundle = new Bundle();
		newFragment.setArguments(bundle);
		return newFragment;
	}

	public MainActivity getParent() {
		return (MainActivity) getActivity();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mCustomerJsonHttpResponseHandler = new CustomerJsonHttpResponseHandler<ArrayList<CategoryGoodsBean>>(getParent()) {
			@Override
			public void processCallSuccess(ArrayList<CategoryGoodsBean> outBean, String msg) {

				if (outBean != null && outBean.size() > 0) {
					mDatas = outBean;
					mCategoryListAdapter = null;
					mCategoryListAdapter = new CategoryListAdapter(getParent(), mDatas);
					mCategoryLV.setAdapter(mCategoryListAdapter);
				}
			}
		};
		mClient = HttpClient.getInstall(getParent());
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		mClient.postAsync(Constants.NetWorkRequest.CATEGORY_URL_TO_DETAIL_002, null, mCustomerJsonHttpResponseHandler);
		View view = inflater.inflate(R.layout.category_fragment, null, false);
		mCategoryLV = (ListView) view.findViewById(R.id.lv_goods_category);
		return view;
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		if (mDatas != null) {
			mDatas.clear();
			mDatas = null;
		}
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		mCategoryListAdapter = null;
	}

}
