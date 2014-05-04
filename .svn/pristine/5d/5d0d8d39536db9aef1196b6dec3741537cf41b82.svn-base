package cn.digione.yibaic.shop.adapter;

import java.util.HashMap;
import java.util.List;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import cn.digione.yibaic.shop.R;
import cn.digione.yibaic.shop.acitvity.AddressManagementActivity;
import cn.digione.yibaic.shop.bean.AddressBean;
import cn.digione.yibaic.shop.bean.JsonNoneOutBean;
import cn.digione.yibaic.shop.common.Constants;
import cn.digione.yibaic.shop.http.CustomerJsonHttpResponseHandler;
import cn.digione.yibaic.shop.http.HttpClient;
import cn.digione.yibaic.shop.util.ToastUtil;
import cn.digione.yibaic.shop.util.Utils;

import com.loopj.android.http.RequestParams;

public class AddressListAdapter extends BaseAdapter {

	private List<AddressBean> addressList;
	private AddressManagementActivity mContext;
	private LayoutInflater mInflater;
	private boolean isSelect;
	private HttpClient client;
	private AlertDialog alertDialog;
	private CustomerJsonHttpResponseHandler<JsonNoneOutBean> deleteJsonHttpResponseHandler;
	private CustomerJsonHttpResponseHandler<JsonNoneOutBean> setDefaultJsonHttpResponseHandler;
	private static final int MAX_ADDRESS = 10; // 最大地址数量

	public AddressListAdapter(AddressManagementActivity context, List<AddressBean> list, boolean isSelect,
			CustomerJsonHttpResponseHandler<JsonNoneOutBean> deleteJsonHttpResponseHandler,
			CustomerJsonHttpResponseHandler<JsonNoneOutBean> setDefaultJsonHttpResponseHandler) {
		addressList = list;
		mContext = context;
		this.isSelect = isSelect; // 是否从购买处跳过来选地址
		mInflater = LayoutInflater.from(mContext);
		this.deleteJsonHttpResponseHandler = deleteJsonHttpResponseHandler;
		this.setDefaultJsonHttpResponseHandler = setDefaultJsonHttpResponseHandler;
	}

	@Override
	public int getCount() {
		int count = 0;
		if (addressList != null && addressList.size() > 0) {
			count = addressList.size();
		}
		return count;
	}

	public void deleteItem(int position) {
		if (addressList != null) {
			addressList.remove(position);
		}
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return addressList.get(position);
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
			convertView = mInflater.inflate(R.layout.address_list_item, null);
			viewHolder = new ViewHolder();
			viewHolder.areaView = (TextView) convertView.findViewById(R.id.area);
			viewHolder.addressView = (TextView) convertView.findViewById(R.id.address);
			viewHolder.consigneeView = (TextView) convertView.findViewById(R.id.consignee);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		final AddressBean addressbean = addressList.get(position);
		convertView.setBackgroundResource(R.drawable.service_item_text_bg);

		if (position != getCount() - 1) {
			viewHolder.areaView.setText(addressbean.getProvinceName() + " " + addressbean.getCityName() + " "
					+ addressbean.getCountyName());
			String telString = (addressbean.getMobile() == null || "".equals(addressbean.getMobile())) ? addressbean
					.getPhone() : addressbean.getMobile();

			viewHolder.consigneeView.setText(addressbean.getName() + " " + telString);
			viewHolder.areaView.setVisibility(View.VISIBLE);
			viewHolder.consigneeView.setVisibility(View.VISIBLE);
			if (isSelect && addressbean.getIsDefault() == 1) {
				convertView.setBackgroundResource(R.drawable.address_select_bg);
			}
		} else {
			viewHolder.areaView.setVisibility(View.GONE);
			viewHolder.consigneeView.setVisibility(View.GONE);
		}

		viewHolder.addressView.setText(addressbean.getAddress());

		setListener(convertView, position);

		return convertView;
	}

	private void setListener(View view, final int i) {
		if (isSelect && i != getCount() - 1) {// 购买流程调用。点击即选择地址，并设置为默认地址
			view.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View view) {
					// TODO Auto-generated method stub
					if (Utils.Network.isHasNetwork(mContext)) {

						String url = Constants.NetWorkRequest.CHANGE_DEFAULT_ADDRESS_URL_016;
						if (client == null) {
							client = HttpClient.getInstall(mContext);
						}
						HashMap<String, String> paramMap = new HashMap<String, String>();
						RequestParams params = new RequestParams(paramMap);
						params.put("consigneeAddressVO.id", addressList.get(i).getId());
						client.postAsync(url, params, setDefaultJsonHttpResponseHandler);
					} else {
						ToastUtil.show(mContext, mContext.getString(R.string.msg_network_fail), ToastUtil.LENGTH_SHORT);
					}
				}
			});

		} else {
			// 点击编辑某个地址
			view.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View view) {
					if (i == getCount() - 1) {// 最后一个元素是添加地址按钮
						if (getCount() <= MAX_ADDRESS) {//等号。因为最后一个元素是添加按钮。10条地址+1个按钮是11个元素
							mContext.showFragment("address_add_fragment", null, true);
						} else {
							ToastUtil.show(mContext.getString(R.string.max_address_tip));
						}
						return;
					}
					Bundle bundle = new Bundle();
					AddressBean adBean = addressList.get(i);
					bundle.putSerializable("modify_address", adBean);
					mContext.showFragment("address_add_fragment", bundle, true);
				}
			});
			// 长按删除某个地址
			view.setOnLongClickListener(new OnLongClickListener() {

				@Override
				public boolean onLongClick(View view) {
					if (i == getCount() - 1) {
						return false;
					}
					// TODO Auto-generated method stub
					AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
					builder.setTitle(mContext.getString(R.string.address_delete_title));
					builder.setMessage(mContext.getString(R.string.confirm_address_delete));
					final AddressBean adBean = addressList.get(i);
					builder.setPositiveButton(mContext.getString(R.string.edit_cart_item_button_delete),
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface mDialogInterface, int which) {
									if (Utils.Network.isHasNetwork(mContext)) {

										String url = Constants.NetWorkRequest.DELETE_ADDRESS_021;
										if (client == null) {
											client = HttpClient.getInstall(mContext);
										}
										HashMap<String, String> paramMap = new HashMap<String, String>();
										RequestParams params = new RequestParams(paramMap);
										params.put("consigneeAddressVO.id", adBean.getId());
										client.postAsync(url, params, deleteJsonHttpResponseHandler);
										deleteItem(i);
									} else {
										ToastUtil.show(mContext, mContext.getString(R.string.msg_network_fail),
												ToastUtil.LENGTH_SHORT);
									}
								}
							});
					builder.setNegativeButton(mContext.getString(R.string.no_string), new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface mDialogInterface, int which) {
							if (alertDialog != null) {
								alertDialog.dismiss();
							}
						}
					});
					alertDialog = builder.create();
					alertDialog.show();
					return false;

				}
			});

		}

	}

	private class ViewHolder {
		TextView areaView;
		TextView addressView;
		TextView consigneeView;
	}
}
