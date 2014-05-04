package cn.digione.yibaic.shop.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import cn.digione.yibaic.shop.R;
import cn.digione.yibaic.shop.bean.OrderProductBean;
import cn.digione.yibaic.shop.http.DisplayImageOptionsForCustom;
import cn.digione.yibaic.shop.util.Utils;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

public class OrderProductListAdapter extends BaseAdapter {

	private ArrayList<OrderProductBean> orderProductList;
	private Context mContext;
	private LayoutInflater mInflater;
	private ImageLoader mImageLoader;
	private DisplayImageOptions options;

	public OrderProductListAdapter(Context context, ArrayList<OrderProductBean> list) {
		orderProductList = list;
		mContext = context;
		mInflater = LayoutInflater.from(mContext);
		mImageLoader = ImageLoader.getInstance();
		options = DisplayImageOptionsForCustom.getDisplayImageOptions();
	}

	@Override
	public int getCount() {
		int count = 0;
		if (orderProductList != null && orderProductList.size() > 0) {
			count = orderProductList.size();
		}
		return count;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return orderProductList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder viewHolder = null;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.order_detail_product_item, null);
			viewHolder = new ViewHolder();
			viewHolder.productImageView = (ImageView) convertView.findViewById(R.id.product_image);
			viewHolder.titleView = (TextView) convertView.findViewById(R.id.product_name);
			viewHolder.priceView = (TextView) convertView.findViewById(R.id.product_price);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		final OrderProductBean obean = orderProductList.get(position);

		viewHolder.titleView.setText(obean.getProductName()
				+ (obean.getProModel() == null ? "" : (" " + obean.getProModel()))
				+ (obean.getProStandardName() == null ? "" : (" " + obean.getProStandardName()))
				+ (obean.getProRom() == null ? "" : (" " + obean.getProRom()))
				+ (obean.getColorName() == null ? "" : (" " + obean.getColorName())));
		String sumstr = mContext.getString(R.string.cart_item_center);
		viewHolder.priceView.setText(String.format(sumstr, Utils.Money.MoneyToYuan(obean.getCurrentPrice()),
				obean.getQuantity(), Utils.Money.MoneyToYuan(obean.getCurrentPrice() * obean.getQuantity())));

		mImageLoader.displayImage(obean.getProPic(), viewHolder.productImageView, options);
		return convertView;
	}

	private class ViewHolder {
		ImageView productImageView;
		TextView titleView;
		TextView priceView;
	}
}
