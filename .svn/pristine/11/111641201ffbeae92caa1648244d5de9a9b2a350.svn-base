package cn.digione.yibaic.shop.acitvity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import cn.digione.yibaic.shop.R;
import cn.digione.yibaic.shop.common.Constants;
import cn.digione.yibaic.shop.ui.*;

/**
 * 订单列表
 * 
 * @author zhangqr
 */

public class OrderListActivity extends BaseActivity

{

	public OrderListActivity() {
	}

	private void handleIntent() {
		Intent intent = getIntent();
		mAction = intent.getAction();
		mType = intent.getIntExtra("order_list_type", 0);
	}

	protected Fragment newFragmentByTag(String tag) {

		if ("tag_order_list".equals(tag)) {
			currentFragment = new OrderListFragment();
			((OrderListFragment) currentFragment).setAction(mAction);
			((OrderListFragment) currentFragment).setType(mType);

		} else if ("order_detail_fragment".equals(tag)) {
			currentFragment = new OrderDetailFragment();
		} else if ("appointment_fragment".equals(tag)) {
			currentFragment = new BookFragment();
		} else if ("coupon_list_fragment".equals(tag)) {
			currentFragment = new CouponQueryListFragment();
        } else if ("order_pay_fragment".equals(tag)) {
            currentFragment = new OrderPayFragment();
		}

		return currentFragment;
	}

	protected void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		setCustomContentView(R.layout.order_list_activity);
		setHomeButtonEnable(true);
		setShoppingBarEnable(true);
		handleIntent();

		if (TextUtils.equals(mAction, "service_list")) {
			showFragment("tag_order_list", null, false);
			if (mType == Constants.AccountFragment.ORDER_LIST) {
				setTitle(R.string.account_my_order_list);
			}

		} else if (TextUtils.equals(mAction, "order_detail")) {
			String orderIdString = getIntent().getStringExtra("order_id");
			if (orderIdString != null) {
				Bundle bd = new Bundle();
				bd.putString("orderId", orderIdString);
				showFragment("order_detail_fragment", bd, false);
			}
		} else if (TextUtils.equals(mAction, "book_list")) {
			showFragment("appointment_fragment", null, false);
			if (mType == Constants.AccountFragment.BOOK_LIST) {
				setTitle(R.string.book_query);
			}

		} else if (TextUtils.equals(mAction, "coupon_list")) {
			showFragment("coupon_list_fragment", null, false);
			setTitle(getString(R.string.my_coupons));
		}
	}

	public static final String TAG_ORDER_DELIVERVIEW = "tag_order_deliverview";
	public static final String TAG_ORDER_EXPRESS = "tag_order_express";
	public static final String TAG_ORDER_LIST = "tag_order_list";
	public static final String TAG_ORDER_VIEW = "tag_order_view";
	private String mAction;

	private Fragment currentFragment;
	// private OrderViewFragment mOrderViewFragment;

	private int mType;
}