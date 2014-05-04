package cn.digione.yibaic.shop.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;
import cn.digione.yibaic.shop.R;
import cn.digione.yibaic.shop.acitvity.ProductListActivity;
import cn.digione.yibaic.shop.adapter.ProductListAdapter;
import cn.digione.yibaic.shop.bean.JsonPagerEntityBean;
import cn.digione.yibaic.shop.bean.ProductBean;
import cn.digione.yibaic.shop.common.Constants;
import cn.digione.yibaic.shop.http.CustomerJsonHttpResponseHandler;
import cn.digione.yibaic.shop.http.HttpClient;
import cn.digione.yibaic.shop.util.Log;

import com.loopj.android.http.RequestParams;

public class ProductListFragment extends BaseFragment<JsonPagerEntityBean<ProductBean>> implements
		AbsListView.OnScrollListener {
	private ProductListAdapter mProductListAdapter = null;
	private List<ProductBean> mDatas = null;
	private ListView mProductListLV = null;
	private Bundle mBundle = null;
	private int mCategoryId = -1;
	private String mCategoryName = null;
	private String proModel = null;

	public void requestHttp() {
		HttpClient client = HttpClient.getInstall(getParent());
		String url = Constants.NetWorkRequest.PRODUCT_LIST_URL_002;
		Map<String, String> map = new HashMap<String, String>();
		if (mCategoryId != -1) {
			map.put("searchProductVO.proClass", mCategoryId + "");
		}
		map.put("searchProductVO.pageSize", 999 + "");
		map.put("searchProductVO.currentPage", 1 + "");

		RequestParams params = new RequestParams(map);
		client.postAsync(url, params, mCustomerJsonHttpResponseHandler);
	}

	public ProductListActivity getParent() {
		return (ProductListActivity) getActivity();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getParent().setHomeButtonEnable(true);
		getParent().setShoppingBarEnable(true);
		mBundle = getArguments();
		if (mBundle != null) {
			mCategoryId = mBundle.getInt("category_id");
			mCategoryName = mBundle.getString("category_name");
			proModel = mBundle.getString("pro_model");
		} else {
			getParent().setTitle(getString(R.string.app_name));
		}

		mCustomerJsonHttpResponseHandler = new CustomerJsonHttpResponseHandler<JsonPagerEntityBean<ProductBean>>(
				getParent(), true) {
			@Override
			public void processCallSuccess(final JsonPagerEntityBean<ProductBean> outBean, final String msg) {
				if (outBean != null && outBean.getResultList().size() > 0) {
					if (mDatas == null) {
						mDatas = new ArrayList<ProductBean>();
					}
					Boolean isEmpty = mDatas.isEmpty();
					if (mDatas != null && !isEmpty && mCurrentPage == 1) {
						mDatas.clear();
					}
					mPageCount = outBean.getPageCount();
					mPageSize = outBean.getPageSize();
					// mCurrentPage = outBean.getCurrentPage();

					mDatas.addAll(outBean.getResultList());

					mProductListAdapter = null;
					mProductListAdapter = new ProductListAdapter(getParent(), mDatas, getArguments());
					mProductListLV.setAdapter(mProductListAdapter);
					mProductListLV.setSelection(mSelectposition);
					// mProductListLV.setOnScrollListener(ProductListFragment.this);
				}
			}
		};
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		if (proModel != null) {
			getParent().setTitle(proModel);
		} else {
			getParent().setTitle(mCategoryName);
		}
		requestHttp();
		View view = inflater.inflate(R.layout.product_list_fragment, container, false);
		mProductListLV = (ListView) view.findViewById(R.id.lv_product_list);
		mProductListLV.setOnScrollListener(ProductListFragment.this);
		return view;
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		// 当不滚动时
		if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
			// 判断是否滚动到底部
			if (view.getLastVisiblePosition() == view.getCount() - 1) {
				if (mCurrentPage < mPageCount && mPageCount != 1 && mDatas != null && mDatas.size() > 0) {
					mSelectposition = mDatas.size() - 1;
					// int pageNumber = mCurrentPage++;
					++mCurrentPage;
					Log.i("mSelectposition=" + mSelectposition + ",CurrentPage=" + mCurrentPage);
					// requestHttp(pageNumber, mPageSize);
					requestHttp();
				}
			}
		}
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

	}
}
