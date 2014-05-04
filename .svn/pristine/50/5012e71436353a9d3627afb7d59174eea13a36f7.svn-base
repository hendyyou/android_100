package cn.digione.yibaic.shop.adapter;

import java.util.List;

import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import cn.digione.yibaic.shop.R;
import cn.digione.yibaic.shop.acitvity.AdvertiseActivity;
import cn.digione.yibaic.shop.acitvity.BaseActivity;
import cn.digione.yibaic.shop.bean.ActivitiesBean;
import cn.digione.yibaic.shop.http.DisplayImageOptionsForCustom;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;

/**
 * Created with IntelliJ IDEA. User: youzh Date: 14-3-6 Time: 下午2:39
 */
public class ActivitiesListAdapter extends BaseAdapter {
	private BaseActivity mContext;
	private LayoutInflater mLayoutInflater;
	private List<ActivitiesBean> datas;
	private ImageLoadingListener mImageLoadingListener;

	public ActivitiesListAdapter(BaseActivity mContext, List<ActivitiesBean> datas) {
		this.datas = datas;
		this.mContext = mContext;
		this.mLayoutInflater = LayoutInflater.from(mContext);
		this.mImageLoadingListener = DisplayImageOptionsForCustom.getImageLoadingListener();
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
			viewHolder = new ViewHolder();
			view = mLayoutInflater.inflate(R.layout.activities_list_item, null);
			viewHolder.activitiesLL = (LinearLayout) view.findViewById(R.id.ll_activities_container);
			viewHolder.picUrlIV = (ImageView) view.findViewById(R.id.iv_activities_pic);
			view.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) view.getTag();
		}

		final ActivitiesBean actbean = datas.get(position);

		viewHolder.activitiesLL.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(mContext, AdvertiseActivity.class);
				intent.putExtra("activitiesBean", actbean);
				mContext.startActivity(intent);
			}
		});

		String picURL = actbean.getPicUrl();
		if (!TextUtils.isEmpty(picURL)) {
			ImageLoader imageLoader = ImageLoader.getInstance();
			viewHolder.picUrlIV.setImageDrawable(null);
			imageLoader.displayImage(picURL, viewHolder.picUrlIV, mImageLoadingListener);
		} else {
			viewHolder.picUrlIV.setImageResource(R.drawable.no_picture);
		}
		return view;
	}

	protected void bindBackground(View view, int i) {
		if (getCount() == 1) {
			view.setBackgroundResource(R.drawable.list_item_single_bg);
		}
		if (i == 0) {
			view.setBackgroundResource(R.drawable.list_item_top_bg);
		}
		if (i == -1 + getCount()) {
			view.setBackgroundResource(R.drawable.list_item_bottom_bg);

		} else {
			view.setBackgroundResource(R.drawable.list_item_middle_bg);
		}
	}

	private class ViewHolder {
		LinearLayout activitiesLL;
		ImageView picUrlIV;
	}
}
