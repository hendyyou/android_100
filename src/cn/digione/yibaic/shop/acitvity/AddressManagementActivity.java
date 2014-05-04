package cn.digione.yibaic.shop.acitvity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import cn.digione.yibaic.shop.R;
import cn.digione.yibaic.shop.ui.AddressAddFragment;
import cn.digione.yibaic.shop.ui.AddressListFragment;

/**
 * 地址管理
 * 
 * @author zhangqr
 * 
 */

public class AddressManagementActivity extends BaseActivity

{

	public AddressManagementActivity() {
	}

	protected Fragment newFragmentByTag(String s) {

		if ("address_list".equals(s)) {

			currentFragment = new AddressListFragment();

		} else if ("address_add_fragment".equals(s)) {
			currentFragment = new AddressAddFragment();
		}

		return currentFragment;
	}

	protected void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		setCustomContentView(R.layout.order_list_activity);
		setHomeButtonEnable(true);
		setShoppingBarEnable(true);
		showFragment("address_list", bundle, false);
		setTitle(R.string.address_management);
	}

	private Fragment currentFragment;

}