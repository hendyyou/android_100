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
import cn.digione.yibaic.shop.bean.OrderBean;
import cn.digione.yibaic.shop.common.Constants;
import cn.digione.yibaic.shop.http.DisplayImageOptionsForCustom;
import cn.digione.yibaic.shop.util.Utils;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

public class OrderListAdapter extends BaseAdapter {

	private ArrayList<OrderBean> orderList;
	private Context mContext;
	private LayoutInflater mInflater;
	private ImageLoader mImageLoader;
	private DisplayImageOptions options;

	public OrderListAdapter(Context context, ArrayList<OrderBean> list) {
		orderList = list;
		mContext = context;
		mInflater = LayoutInflater.from(mContext);
		mImageLoader = ImageLoader.getInstance();
		options = DisplayImageOptionsForCustom.getDisplayImageOptions();
	}

	protected void bindBackground(View view, int i) {
		if (getCount() == 1) {
			view.setBackgroundResource(R.drawable.list_item_single_bg);
			return;
		}
		/*
		 * if (i == 0) { view.setBackgroundResource(R.drawable.list_item_top_bg); return; }
		 */
		if (i == -1 + getCount()) {
			view.setBackgroundResource(R.drawable.list_item_bottom_bg);
			return;
		} else {
			view.setBackgroundResource(R.drawable.list_item_middle_bg);
			return;
		}
	}

	@Override
	public int getCount() {
		int count = 0;
		if (orderList != null && orderList.size() > 0) {
			count = orderList.size();
		}
		return count;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return orderList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder viewHolder = null;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.order_list_item, null);
			viewHolder = new ViewHolder();
			viewHolder.orderpicImageView = (ImageView) convertView.findViewById(R.id.order_item_photo);
			viewHolder.orderTimeView = (TextView) convertView.findViewById(R.id.order_item_time);
			viewHolder.priceView = (TextView) convertView.findViewById(R.id.order_item_price);
			viewHolder.statusView = (TextView) convertView.findViewById(R.id.order_status);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		final OrderBean obean = orderList.get(position);

		viewHolder.priceView.setText(mContext.getString(R.string.order_list_rmb)+Utils.Money.MoneyToYuan(obean.getOrderTotalAmount()));

		mImageLoader.displayImage(obean.getOrderPic(), viewHolder.orderpicImageView, options);

		viewHolder.orderTimeView.setText(obean.getOrderTime());

		viewHolder.statusView.setText(obean.getOrderStatusName());

	//	bindBackground(convertView, position);
		return convertView;
	}

	private class ViewHolder {
		ImageView orderpicImageView;
		TextView priceView;
		TextView orderTimeView;
		TextView statusView;
	}
}
