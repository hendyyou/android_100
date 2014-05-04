package cn.digione.yibaic.shop.acitvity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import cn.digione.yibaic.shop.R;
import cn.digione.yibaic.shop.bean.ShoppingCartItemBean;
import cn.digione.yibaic.shop.ui.EditCartItemFragment;

/**
 * Created with IntelliJ IDEA.
 * User: youzh
 * Date: 14-3-11
 * Time: 上午10:24
 */
public class EditCartItemActivity extends BaseActivity {
    private FragmentManager mFragmentManager;
    private EditCartItemFragment mEditCartItemFragment;
    private ShoppingCartItemBean mShoppingCartItemBean;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setCustomContentView(R.layout.edit_cart_item_activity);
        Intent intent = getIntent();
        mFragmentManager = getSupportFragmentManager();
        mEditCartItemFragment = EditCartItemFragment.newInstance("shopping_cart");
        mEditCartItemFragment.setArguments(intent.getExtras());
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragment_edit_cart_item_container, mEditCartItemFragment);
        //fragmentTransaction.addToBackStack()
        fragmentTransaction.commit();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
    }
}
