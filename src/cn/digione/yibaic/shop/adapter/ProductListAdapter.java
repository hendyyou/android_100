package cn.digione.yibaic.shop.adapter;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import cn.digione.yibaic.shop.R;
import cn.digione.yibaic.shop.acitvity.ProductListActivity;
import cn.digione.yibaic.shop.bean.ProductBean;
import cn.digione.yibaic.shop.http.DisplayImageOptionsForCustom;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;

import java.util.List;

/**
 * Created with IntelliJ IDEA. User: youzh Date: 14-3-6 Time: 下午2:39
 */
public class ProductListAdapter extends BaseAdapter {
	private ProductListActivity mContext;
	private LayoutInflater mLayoutInflater;
	private List<ProductBean> datas;
	private ImageLoadingListener mImageLoadingListener;
	private Bundle mBundle;

	public ProductListAdapter(ProductListActivity context, List<ProductBean> datas, Bundle bundle) {
		this.datas = datas;
		this.mContext = context;
		this.mLayoutInflater = LayoutInflater.from(mContext);
		this.mImageLoadingListener = DisplayImageOptionsForCustom.getImageLoadingListener();
		this.mBundle = bundle;
	}

	@Override
	public int getCount() {
		int count = 0;
		if (datas != null && datas.size() > 0) {
			count = datas.size();
		}
		return count;
	}

	@Override
	public Object getItem(int position) {
		Object object = null;
		if (datas != null && datas.size() > 0) {
			object = datas.get(position);
		}
		return object;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View view, ViewGroup parent) {
		ViewHolder viewHolder = null;
        if (view == null) {
            view = mLayoutInflater.inflate(R.layout.product_list_item, null);
			viewHolder = new ViewHolder();
			viewHolder.picUrlIV = (ImageView) view.findViewById(R.id.iv_product_image);
			viewHolder.nameTV = (TextView) view.findViewById(R.id.tv_product_name);
			viewHolder.productContainerRL = (RelativeLayout) view.findViewById(R.id.rl_product_container);
			view.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) view.getTag();
		}

		final ProductBean productBean = datas.get(position);

		String picURL = productBean.getPic();
		if (!TextUtils.isEmpty(picURL)) {
			ImageLoader imageLoader = ImageLoader.getInstance();
			viewHolder.picUrlIV.setImageDrawable(null);
			imageLoader.displayImage(picURL, viewHolder.picUrlIV, mImageLoadingListener);
		} else {
			viewHolder.picUrlIV.setImageResource(R.drawable.no_picture);
		}
		final String proModel = productBean.getProModel() == null ? "" : productBean.getProModel();
		viewHolder.nameTV.setText(proModel);

		viewHolder.productContainerRL.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Bundle bundle = new Bundle();
				bundle.putInt("select_product_id", productBean.getId());
				bundle.putString("select_product_model", proModel);
				mContext.showFragment("product_details_fragment", bundle, true);
			}
		});
		return view;
	}

	protected void bindBackground(View view, int i) {
		if (getCount() == 1) {
			view.setBackgroundResource(R.drawable.list_item_single_bg);
			return;
		}
		if (i == 0) {
			view.setBackgroundResource(R.drawable.list_item_top_bg);
			return;
		}
		if (i == -1 + getCount()) {
			view.setBackgroundResource(R.drawable.list_item_bottom_bg);
			return;
		} else {
			view.setBackgroundResource(R.drawable.list_item_middle_bg);
			return;
		}
	}

	private class ViewHolder {
		ImageView picUrlIV;
		TextView nameTV;
		RelativeLayout productContainerRL;
	}
}
