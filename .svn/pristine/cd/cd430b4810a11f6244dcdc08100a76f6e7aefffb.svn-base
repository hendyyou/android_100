package cn.digione.yibaic.shop.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import cn.digione.yibaic.shop.R;
import cn.digione.yibaic.shop.acitvity.ShoppingActivity;

public class CouponFragment extends BaseFragment {
	// private static final String TAG = "ActivitiesFragment";
	// private String extraParam;// = "extraParam android";
	private String defaultHello = "default value";
	private Activity mContext;
	private EditText mInputCouponET;
	private Button mCouponBtn;
	private LinearLayout mInputCouponContainer;

	/*
	 * public static CouponFragment newInstance(String s) { CouponFragment newFragment = new CouponFragment(); Bundle bundle
	 * = new Bundle(); bundle.putString("extraParam", s); newFragment.setArguments(bundle); return newFragment;
	 * 
	 * }
	 */
	public ShoppingActivity getParent() {
		return (ShoppingActivity) getActivity();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mContext = getParent();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		View view = inflater.inflate(R.layout.coupon_fragment, container, false);
		mInputCouponContainer = (LinearLayout) view.findViewById(R.id.ll_input_coupon_title_container);
		mInputCouponContainer.setBackgroundResource(R.drawable.radiobutton_bottom_bg_p);
		mInputCouponET = (EditText) view.findViewById(R.id.et_coupon_input);
		mCouponBtn = (Button) view.findViewById(R.id.btn_coupon);
		mCouponBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

			}
		});
		return view;

	}
}
