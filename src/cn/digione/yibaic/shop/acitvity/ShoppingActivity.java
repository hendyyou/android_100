package cn.digione.yibaic.shop.acitvity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import cn.digione.yibaic.shop.R;
import cn.digione.yibaic.shop.bean.ShoppingCartItemBean;
import cn.digione.yibaic.shop.ui.*;

/**
 * Created with IntelliJ IDEA.
 * User: youzh
 * Date: 14-3-11
 * Time: 上午10:24
 */
public class ShoppingActivity extends BaseActivity {
    private FragmentManager mFragmentManager;
    private ShoppingFragment mShoppingFragment;
    private ShoppingCartItemBean mShoppingCartItemBean;

    @Override
    protected Fragment newFragmentByTag(String s) {
        Fragment fragment = null;
        if ("checkout_fragment".equals(s)) {
            fragment = new CheckoutFragment();
        }

        if ("checkout_fragment_update_address".equals(s)) {
            fragment = new CheckoutFragment();
        }

        if ("edit_cartitem_fragment".equals(s)) {
            fragment = new EditCartItemFragment();
            //((EditCartItemFragment) fragment).setOnCheckStatusListener(this);
        }
        if ("order_submit_fragment".equals(s)) {
            fragment = new OrderSubmitFragment();
        }
        if ("shopping_fragment".equals(s)) {
            fragment = new ShoppingFragment();
            //((ShoppingFragment) fragment).setOnCheckStatusListener(this);
        }
        if ("coupon_fragment".equals(s)) {
            fragment = new CouponFragment();
            //((CouponFragment) couponfragment).setCouponValidatedListener(this);
        }
        if ("shopping_fragment".equals(s)) {
            fragment = new ShoppingFragment();
            //((ShoppingProductFragment) shoppingproductfragment).setOnCheckStatusListener(this);
        }
        if ("home_fragment".equals(s)) {
            fragment = HomeFragment.newInstance("home_fragment");
        }

       /* if ("incast_products_fragment".equals(s)) {
            return new IncastProductsFragment();
        }*/
        return fragment;
    }

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setCustomContentView(R.layout.shopping_activity);
        setTitle(R.string.product_shopping_cart);
        setShoppingBarEnable(false);
        setHomeButtonEnable(true);
        Intent intent = getIntent();
        String jcode = intent.getStringExtra("JCODE");
        String isPrepaid = intent.getStringExtra("is_prepaid");
        String proId = intent.getStringExtra("jcode_product_id");
      /*  String fromAddress = intent.getStringExtra("select_address_back_checkout");
        if (fromAddress != null && fromAddress.equals("checkout")) {
            showFragment("checkout_fragment_update_address", getIntent().getExtras(), false);
        } else*/
        if (jcode != null && jcode.trim().length() > 0 && proId != null) {
            showFragment("checkout_fragment", getIntent().getExtras(), false);
        } else if ("is_prepaid".equals(isPrepaid) && proId != null) {
            showFragment("checkout_fragment", getIntent().getExtras(), false);
        } else {
            showFragment("shopping_fragment", getIntent().getExtras(), false);
        }
    }

   /* @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        String fromAddress = intent.getStringExtra("select_address_back_checkout");
        if (fromAddress != null && fromAddress.equals("checkout")) {
            showFragment("checkout_fragment_update_address", intent.getExtras(), false);
        }
    }*/
}
