package cn.digione.yibaic.shop.ui;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import cn.digione.yibaic.shop.R;
import cn.digione.yibaic.shop.adapter.AreaListAdapter;
import cn.digione.yibaic.shop.bean.AddressBean;
import cn.digione.yibaic.shop.bean.AreaBean;
import cn.digione.yibaic.shop.bean.JsonNoneOutBean;
import cn.digione.yibaic.shop.bean.RequestErrorBean;
import cn.digione.yibaic.shop.common.Constants;
import cn.digione.yibaic.shop.http.CustomerJsonHttpResponseHandler;
import cn.digione.yibaic.shop.http.HttpClient;
import cn.digione.yibaic.shop.util.ToastUtil;
import cn.digione.yibaic.shop.util.Utils;
import com.loopj.android.http.RequestParams;

import java.util.HashMap;
import java.util.List;

public class AddressAddFragment extends BaseFragment {

	private EditText nameEditText; // 收货人姓名
	private Spinner provinceSpinner; // 省份列表
	private Spinner citySpinner;// 城市列表
	private Spinner districtSpinner; // 区列表
	private EditText addressDetailEditText; // 详细地址
	private EditText zipCodeEditText; // 邮政编码
	private EditText phoneEditText; // 手机号码
	private EditText addressFixedPhoneAreaEditText; // 固话区号
	private EditText addressFixedPhoneEditText; // 固话号码
	private EditText addressFixedPhoneSubEditText; // 固话分机
	private Button submitButton; // 提交按钮
	private AddressBean modifyAddressBean; // 地址bean
	private AreaListAdapter provinceAdapter; // 省份列表adapter
	private AreaListAdapter cityAdapter; // 城市列表adapter
	private AreaListAdapter districtAdapter; // 区列表adapter
	private CustomerJsonHttpResponseHandler<JsonNoneOutBean> mAddressrJsonHttpResponseHandler;
	private CustomerJsonHttpResponseHandler<List<AreaBean>> provinceHttpResponseHandler;
	private HttpClient client;

	@Override
	public View onCreateView(LayoutInflater layoutinflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = layoutinflater.inflate(R.layout.address_add_fragment, container, false);
		initView(view);
		return view;
	}

	/**
	 * 初始化组件
	 * 
	 * @param view
	 */
	private void initView(View view) {
		nameEditText = (EditText) view.findViewById(R.id.consignee_name);
		provinceSpinner = (Spinner) view.findViewById(R.id.address_province);
		citySpinner = (Spinner) view.findViewById(R.id.address_city);
		citySpinner.setEnabled(false);
		districtSpinner = (Spinner) view.findViewById(R.id.address_district);
		districtSpinner.setEnabled(false);
		addressDetailEditText = (EditText) view.findViewById(R.id.address_detail);
		zipCodeEditText = (EditText) view.findViewById(R.id.address_zipcode);
		phoneEditText = (EditText) view.findViewById(R.id.address_tel);
		// 固话区号
		addressFixedPhoneAreaEditText = (EditText) view.findViewById(R.id.address_fixed_phone_area);
		// 固话号码
		addressFixedPhoneEditText = (EditText) view.findViewById(R.id.address_fixed_phone);
		// 固话分机
		addressFixedPhoneSubEditText = (EditText) view.findViewById(R.id.address_fixed_phone_sub);
		submitButton = (Button) view.findViewById(R.id.address_submitbtn);
		// 提交按钮
		submitButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (isEmpty(nameEditText) || isEmpty(addressDetailEditText) || isEmpty(phoneEditText) || isEmpty(zipCodeEditText)) {
					return;
				}
				if (provinceSpinner.getSelectedItemPosition() == 0) {
					ToastUtil.show(getString(R.string.please_address_select_province));
					return;
				} else if (citySpinner.getSelectedItemPosition() == 0) {
					ToastUtil.show(getString(R.string.please_address_select_city));
					return;
				} else if (districtSpinner.getSelectedItemPosition() == 0) {
					ToastUtil.show(getString(R.string.please_address_select_district));
					return;
				}

				String name = nameEditText.getText().toString();
				String addressDetail = addressDetailEditText.getText().toString();
				String mobilePhone = phoneEditText.getText().toString();

				String zipcode = zipCodeEditText.getEditableText().toString();
				String fix_phone_areacode = addressFixedPhoneAreaEditText.getText().toString();
				String fix_phone = addressFixedPhoneEditText.getText().toString();
				String fix_phone_sub = addressFixedPhoneSubEditText.getText().toString();

				if (Utils.Network.isHasNetwork(getActivity())) {
					String url = Constants.NetWorkRequest.COMMIT_ADDRESS_020;
					if (client == null) {
						client = HttpClient.getInstall(getActivity());
					}
					HashMap<String, String> paramMap = new HashMap<String, String>();
					RequestParams params = new RequestParams(paramMap);
					if (modifyAddressBean != null) {// 地址更新，则需要提供ID
						params.put("consigneeAddressVO.id", modifyAddressBean.getId());
						params.put("consigneeAddressVO.isDefault", modifyAddressBean.getIsDefault());
					} else {// 如果是创建地址，则直接设置为默认地址
						params.put("consigneeAddressVO.isDefault", "1");
					}
					if (fix_phone_areacode != null &&!"".equals(fix_phone_areacode) && fix_phone != null && !"".equals(fix_phone)) {
						if (fix_phone_sub != null && !"".equals(fix_phone_sub)) {// 固话，有分机
							params.put("consigneeAddressVO.phone", fix_phone_areacode + "-" + fix_phone + "-"
									+ fix_phone_sub);
						} else {// 固话，没有分机
							params.put("consigneeAddressVO.phone", fix_phone_areacode + "-" + fix_phone);
						}
					}else{
						params.put("consigneeAddressVO.phone", "");
					}
					params.put("consigneeAddressVO.name", name);
					params.put("consigneeAddressVO.mobile", mobilePhone);
					params.put("consigneeAddressVO.postcode", zipcode);
					params.put("consigneeAddressVO.areaId", ((AreaBean) districtSpinner.getSelectedItem()).getId());
					params.put("consigneeAddressVO.address", addressDetail);
                    params.put("ConsigneeAddressVO.userId",
                               Utils.Preference.getStringPref(getActivity(), Constants.DataFile.DefaultKey.USER_ID, ""));
                    client.postAsync(url, params, mAddressrJsonHttpResponseHandler);
				} else {
					ToastUtil.show(getActivity(), getString(R.string.msg_network_fail), ToastUtil.LENGTH_SHORT);
				}

			}
		});

		if (Utils.Network.isHasNetwork(getActivity())) {
			String url = Constants.NetWorkRequest.PROVINCE_URL_018;
			if (client == null) {
				client = HttpClient.getInstall(getActivity());
			}
			HashMap<String, String> paramMap = new HashMap<String, String>();
			RequestParams params = new RequestParams(paramMap);
			params.put("id", "-1"); // -1表示查询全国所有省份
			client.postAsync(url, params, provinceHttpResponseHandler);
		} else {
			ToastUtil.show(getActivity(), getString(R.string.msg_network_fail), ToastUtil.LENGTH_SHORT);
		}

		if (getArguments() != null && getArguments().getSerializable("modify_address") != null) {
			modifyAddressBean = (AddressBean) getArguments().getSerializable("modify_address");
			setAddressToView(modifyAddressBean);
		}

	}

	private boolean isEmpty(EditText editText) {
		// TODO Auto-generated method stub
		if (editText == null) {
			return true;
		} else if (editText.getText() == null || editText.getText().length() <= 0) {
			editText.setError(Utils.getErrorSpanString(R.string.must_fill, getActivity()));
			return true;
		}
		return false;
	}

	private void setAddressToView(AddressBean addressBean) {
		if (addressBean == null) {
			return;
		}
		nameEditText.setText(addressBean.getName());
		addressDetailEditText.setText(addressBean.getAddress());
		zipCodeEditText.setText(addressBean.getPostcode());
		phoneEditText.setText(addressBean.getMobile());
		if (addressBean.getPhone() != null && addressBean.getPhone().contains("-")) {
			String[] fixPhoneArray = addressBean.getPhone().split("-");
			if (fixPhoneArray.length > 1) {
				addressFixedPhoneAreaEditText.setText(fixPhoneArray[0]);
				addressFixedPhoneEditText.setText(fixPhoneArray[1]);
				if (fixPhoneArray.length > 2) {
					addressFixedPhoneSubEditText.setText(fixPhoneArray[2]);
				}
			}
		}

	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		provinceHttpResponseHandler = new CustomerJsonHttpResponseHandler<List<AreaBean>>(getActivity()) {

			@Override
			public void processCallSuccess(final List<AreaBean> outBean, String msg) {

				// 省份
				if ("1".equals(outBean.get(0).getAreaType())) {
					AreaBean headBean = new AreaBean();
					headBean.setText(getString(R.string.address_select_province));
					outBean.add(0, headBean);
					provinceAdapter = new AreaListAdapter(getContext(), outBean);
					loadSpinner(outBean, provinceSpinner, provinceAdapter, citySpinner);
					int count = provinceAdapter.getCount();
					for (int i = 1; i < count; i++) {
						if (modifyAddressBean != null && modifyAddressBean.getProvinceName() != null
								&& modifyAddressBean.getProvinceName().equals(provinceAdapter.getItem(i).getText())) {
							provinceSpinner.setSelection(i);
						}
					}
				} else if ("2".equals(outBean.get(0).getAreaType())) {// 城市
					AreaBean headBean = new AreaBean();
					headBean.setText(getString(R.string.address_select_city));
					outBean.add(0, headBean);
					cityAdapter = new AreaListAdapter(getContext(), outBean);
					loadSpinner(outBean, citySpinner, cityAdapter, districtSpinner);
					int count = cityAdapter.getCount();
					for (int i = 1; i < count; i++) {
						if (modifyAddressBean != null && modifyAddressBean.getCityName() != null
								&& modifyAddressBean.getCityName().equals(cityAdapter.getItem(i).getText())) {
							citySpinner.setSelection(i);
						}
					}
				} else if ("3".equals(outBean.get(0).getAreaType())) {// 县区
					AreaBean headBean = new AreaBean();
					headBean.setText(getString(R.string.address_select_district));
					outBean.add(0, headBean);
					districtAdapter = new AreaListAdapter(getContext(), outBean);
					loadSpinner(outBean, districtSpinner, districtAdapter, null);
					int count = districtAdapter.getCount();
					for (int i = 1; i < count; i++) {
						if (modifyAddressBean != null && modifyAddressBean.getCountyName() != null
								&& modifyAddressBean.getCountyName().equals(districtAdapter.getItem(i).getText())) {
							districtSpinner.setSelection(i);
						}
					}
				}

			}

			private void loadSpinner(final List<AreaBean> outBean, Spinner currentSpinner, AreaListAdapter currentAdapter,
					final Spinner subSpiner) {
				// TODO Auto-generated method stub

				currentSpinner.setAdapter(currentAdapter);

				if (subSpiner == null) {
					return;
				}
				currentSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
						// TODO Auto-generated method stub

						if (position == 0) {
							subSpiner.setSelection(0);
							subSpiner.setEnabled(false);
							if (subSpiner != districtSpinner) {
								districtSpinner.setSelection(0);
								districtSpinner.setEnabled(false);
							}
							return;
						}
						// 请求下一级
						subSpiner.setEnabled(true);
						if (Utils.Network.isHasNetwork(getActivity())
								&& "true".equals(outBean.get(position).getHasChildren())) {
							String url = Constants.NetWorkRequest.PROVINCE_URL_018;
							if (client == null) {
								client = HttpClient.getInstall(getActivity());
							}
							HashMap<String, String> paramMap = new HashMap<String, String>();
							RequestParams params = new RequestParams(paramMap);
							params.put("id", outBean.get(position).getId()); // -1表示查询全国所有省份
							client.postAsync(url, params, provinceHttpResponseHandler);
						} else {
							ToastUtil.show(getActivity(), getString(R.string.msg_network_fail), ToastUtil.LENGTH_SHORT);
						}
					}

					@Override
					public void onNothingSelected(AdapterView<?> parent) {
						// TODO Auto-generated method stub

					}
				});

			}

			@Override
			public void processCallFailure(List<AreaBean> outBean, String failureCode, String msg) {

				ToastUtil.show(getActivity(), msg, ToastUtil.LENGTH_SHORT);

			}

			@Override
			public void processAfterHttpSuccess(List<AreaBean> outBean, Integer msgCode, String failureCode, String msg) {
				super.processAfterHttpSuccess(outBean, msgCode, failureCode, msg);
			}

			@Override
			public void processHttpFailure(Throwable e, RequestErrorBean outBean) {
				super.processHttpFailure(e, outBean);
			}
		};
		mAddressrJsonHttpResponseHandler = new CustomerJsonHttpResponseHandler<JsonNoneOutBean>(getActivity()) {

			@Override
			public void processCallSuccess(JsonNoneOutBean outBean, String msg) {
				// TODO Auto-generated method stub
				FragmentManager fManager = getActivity().getSupportFragmentManager();
				FragmentTransaction ft = fManager.beginTransaction();
				fManager.popBackStack();
				ft.commit();
			}
		};
	}

}
