package cn.digione.yibaic.shop.acitvity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import cn.digione.yibaic.shop.R;
import cn.digione.yibaic.shop.ui.ActivitiesWebFragment;

/**
 * 广告、活动
 * 
 * @author zhangqr
 * 
 */

public class AdvertiseActivity extends BaseActivity {

	protected Fragment newFragmentByTag(String s) {

		if ("activities_web".equals(s)) {

			currentFragment = new ActivitiesWebFragment();

		}

		return currentFragment;
	}

	protected void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		setCustomContentView(R.layout.order_list_activity);
		setShoppingBarEnable(true);
		setHomeButtonEnable(true);
		showFragment("activities_web", bundle, false);
	}

	private Fragment currentFragment;

}