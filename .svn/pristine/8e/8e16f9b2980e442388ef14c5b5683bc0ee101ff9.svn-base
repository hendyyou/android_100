package cn.digione.yibaic.shop.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import cn.digione.yibaic.shop.R;
import cn.digione.yibaic.shop.bean.AreaBean;

public class AreaListAdapter extends BaseAdapter {

	private List<AreaBean> areas;
	private Context mContext;
	private LayoutInflater mInflater;

	public AreaListAdapter(Context context, List<AreaBean> areas) {
		this.areas = areas;
		mContext = context;
		mInflater = LayoutInflater.from(mContext);
	}

	@Override
	public int getCount() {
		int count = 0;
		if (areas != null && areas.size() > 0) {
			count = areas.size();
		}
		return count;
	}

	@Override
	public AreaBean getItem(int position) {
		// TODO Auto-generated method stub
		return areas.get(position);
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
			convertView = mInflater.inflate(R.layout.base_spinner_item, null);
			viewHolder = new ViewHolder();

			viewHolder.textView = (TextView) convertView.findViewById(R.id.spinner_text_item);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		viewHolder.textView.setText(areas.get(position).getText());

		return convertView;
	}

	private class ViewHolder {
		TextView textView;
	}
}
