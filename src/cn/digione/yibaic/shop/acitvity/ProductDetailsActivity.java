package cn.digione.yibaic.shop.acitvity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import cn.digione.yibaic.shop.R;
import cn.digione.yibaic.shop.ui.ProductDetailsFragment;

/**
 * Created with IntelliJ IDEA.
 * User: youzh
 * Date: 14-3-10
 * Time: 上午10:12
 */
public class ProductDetailsActivity extends BaseActivity {
    private FragmentManager mFragmentManager = null;

    private ProductDetailsFragment mProductDetailsFragment;


    private void popupFragment() {
        mFragmentManager.popBackStack();
    }


    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setCustomContentView(R.layout.product_details_activity);
        Intent intent = getIntent();
        String title = "";
        if (intent != null) {
            title = intent.getStringExtra("bar_title");
            if (TextUtils.isEmpty(title)) {
                title = getString(R.string.app_name);
            }
        }

        setTitle(title);
        mFragmentManager = getSupportFragmentManager();
        mProductDetailsFragment = new ProductDetailsFragment();
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.ll_prduct_detail_fragment_container, mProductDetailsFragment);
        fragmentTransaction.commit();
        setShoppingBarEnable(false);
    }
}
