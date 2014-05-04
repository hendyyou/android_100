package cn.digione.yibaic.shop.acitvity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import cn.digione.yibaic.shop.R;
import cn.digione.yibaic.shop.ui.FMaBuyFragment;

/**
 * Created with IntelliJ IDEA.
 * User: youzh
 * Date: 14-3-11
 * Time: 上午10:24
 */
public class FMaBuyActivity extends BaseActivity {
    private FragmentManager mFragmentManager;
    private FMaBuyFragment mShoppingFragment;

    protected Fragment newFragmentByTag(String s) {
        Fragment fragment = null;
        if ("fmabuy_fragment".equals(s)) {
            fragment = new FMaBuyFragment();
        }
        return fragment;
    }

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setCustomContentView(R.layout.fmabuy_activity);
        setTitle(R.string.app_name);
        setShoppingBarEnable(true);
        setHomeButtonEnable(true);
        showFragment("fmabuy_fragment", getIntent().getExtras(), false);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        FragmentManager fManager = getSupportFragmentManager();
        int backStackCount = fManager.getBackStackEntryCount();
        for (int i = backStackCount - 1; i >= 0; i--) {
            fManager.popBackStack();
        }
    }
}
