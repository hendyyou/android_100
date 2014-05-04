package cn.digione.yibaic.shop.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import cn.digione.yibaic.shop.R;
import cn.digione.yibaic.shop.acitvity.ShoppingActivity;
import cn.digione.yibaic.shop.bean.JsonNoneOutBean;
import cn.digione.yibaic.shop.bean.ShoppingCartItemBean;
import cn.digione.yibaic.shop.common.Constants;
import cn.digione.yibaic.shop.http.CustomerJsonHttpResponseHandler;
import cn.digione.yibaic.shop.http.DisplayImageOptionsForCustom;
import cn.digione.yibaic.shop.http.HttpClient;
import cn.digione.yibaic.shop.util.Utils;
import com.loopj.android.http.RequestParams;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA. User: youzh Date: 14-3-11 Time: 上午10:33
 */
public class EditCartItemFragment extends BaseFragment {
    private static OnEidtCartItemCountllListener mEidtCartItemCount;
    private TextView cartItemTitleTV;
    private TextView cartItemPriceTV;
    private EditText numberEditText;
    private Button delCartItemBtn;
    private Button confirmCountBtn;
    private ImageView productPic;
    private ShoppingCartItemBean mShoppingCartItemBean;
    private CustomerJsonHttpResponseHandler<JsonNoneOutBean> mModifyShopCartItem;
    private int mCartItemPosition = -1;
    private ImageLoadingListener mImageLoadingListener;
    private AlertDialog alertDialog;

    public static EditCartItemFragment newInstance(String s) {
        EditCartItemFragment newFragment = new EditCartItemFragment();
        Bundle bundle = new Bundle();
        newFragment.setArguments(bundle);
        return newFragment;
    }

    public static void setEidtCartItemCount(OnEidtCartItemCountllListener eidtCartItemCount) {
        mEidtCartItemCount = eidtCartItemCount;
    }

    public ShoppingActivity getParent() {
        return (ShoppingActivity) getActivity();
    }

    public void requestHttp(String url, RequestParams params, CustomerJsonHttpResponseHandler httpResponseHandler) {
        HttpClient client = HttpClient.getInstall(getParent());
        client.postAsync(url, params, httpResponseHandler);
    }

    private void deleteCartItemRequestHttp(Integer id) {
        String url = Constants.NetWorkRequest.SHOPPING_DELETE_CART_URL_032;
        url = String.format(url, id);
        /*
         * Map<String, String> map = new HashMap<String, String>(); RequestParams params = new RequestParams(map);
		 * map.put("searchProductVO.proClass", mCategoryId + ""); map.put("searchProductVO.proModel", mProModel);
		 * map.put("searchProductVO.proRom", mProRom); map.put("searchProductVO.pageSize", mPageSize + "");
		 * map.put("searchProductVO.currentPage", mCurrentPage + "");
		 */
        requestHttp(url, null, mCustomerJsonHttpResponseHandler);
    }

    private void editCartItemRequestHttp(Integer id, Integer num) {
        String url = Constants.NetWorkRequest.SHOPPING_MODIFY_CART_URL_033;
        url = String.format(url, id);
        Map<String, String> map = new HashMap<String, String>();
        map.put("num", String.valueOf(num));
        RequestParams params = new RequestParams(map);
        requestHttp(url, params, mModifyShopCartItem);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCustomerJsonHttpResponseHandler = new CustomerJsonHttpResponseHandler<JsonNoneOutBean>(getParent(), false) {
            @Override
            public void processCallSuccess(JsonNoneOutBean outBean, String msg) {
                mEidtCartItemCount.changeCount(0, mCartItemPosition);
                getParent().onBackPressed();
                // getParent().showFragment("shopping_fragment", null, false);
            }
        };

        mModifyShopCartItem = new CustomerJsonHttpResponseHandler<JsonNoneOutBean>(getParent(), false) {
            @Override
            public void processCallSuccess(JsonNoneOutBean outBean, String msg) {

                getParent().onBackPressed();

            }
        };
        this.mImageLoadingListener = DisplayImageOptionsForCustom.getImageLoadingListener();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        getParent().setTitle(R.string.edit_cart_update);
        View view = inflater.inflate(R.layout.edit_cartitem_fragment, container, false);
        numberEditText = (EditText) view.findViewById(R.id.edit_cart_number_tv);
        confirmCountBtn = (Button) view.findViewById(R.id.btn_confirm_count);
        cartItemTitleTV = (TextView) view.findViewById(R.id.tv_edit_cart_product_title);
        cartItemPriceTV = (TextView) view.findViewById(R.id.tv_edit_cart_price);
        delCartItemBtn = (Button) view.findViewById(R.id.btn_delete_cart_item);
        productPic = (ImageView) view.findViewById(R.id.iv_edit_cart_item_pic);

        mShoppingCartItemBean = (ShoppingCartItemBean) getArguments().getSerializable("cart_item_object");
        mCartItemPosition = getArguments().getInt("cart_item_position");

        Integer num = mShoppingCartItemBean.getNum();
        Integer resNum = mShoppingCartItemBean.getProRes();
        if (num > 0) {
            numberEditText.setText(String.valueOf(num));
        }
        // int inSpindex = Arrays.in

        String picURL2 = mShoppingCartItemBean.getProPic2();
        if (picURL2 == null || "".equals(picURL2)) {
            picURL2 = mShoppingCartItemBean.getProPic();
            String[] splitStr = picURL2.split("_");
            String[] suffixArr = splitStr[splitStr.length - 1].split("\\.");
            picURL2 = splitStr[0] + "_2." + suffixArr[suffixArr.length - 1];
        }

        if (!TextUtils.isEmpty(picURL2)) {
            ImageLoader imageLoader = ImageLoader.getInstance();
            productPic.setImageDrawable(null);
            imageLoader.displayImage(picURL2, productPic, mImageLoadingListener);
        } else {
            productPic.setImageResource(R.drawable.no_picture);
        }

        String proName = mShoppingCartItemBean.getProName() == null ? "" : mShoppingCartItemBean.getProName();
        String proColor = mShoppingCartItemBean.getProColor() == null ? "" : mShoppingCartItemBean.getProColor();
        String proRom = mShoppingCartItemBean.getProRom() == null ? "" : mShoppingCartItemBean.getProRom();
        String proStandardName =
                mShoppingCartItemBean.getProStandardName() == null ? "" : mShoppingCartItemBean.getProStandardName();
        String title = proName + " " + proColor+ " " + proRom + " " + proStandardName ;

        cartItemTitleTV.setText(title);
        String price = Utils.Money.MoneyToYuan(mShoppingCartItemBean.getProPrice() * num);
        cartItemPriceTV.setText(getString(R.string.order_list_rmb) + price);

        delCartItemBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mShoppingCartItemBean != null) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getParent());
                    builder.setTitle(getString(R.string.delete_cart_title));
                    builder.setMessage(getString(R.string.delete_cart_message));
                    builder.setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface mDialogInterface, int which) {
                            deleteCartItemRequestHttp(mShoppingCartItemBean.getId());
                        }
                    });
                    builder.setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface mDialogInterface, int which) {
                            if (alertDialog != null) {
                                alertDialog.dismiss();
                            }
                        }
                    });
                    alertDialog = builder.create();
                    alertDialog.show();
                }
            }
        });

        confirmCountBtn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                String num = numberEditText.getText().toString();
                if (num == null || num.length() <= 0 || num.equals("0")) {
                    numberEditText.setError(Utils.getErrorSpanString(R.string.order_update_must_fill, getActivity()));
                    return;
                }
                if (mShoppingCartItemBean != null && numberEditText.getText() != null) {
                    editCartItemRequestHttp(mShoppingCartItemBean.getId(),
                                            Integer.parseInt(numberEditText.getText().toString()));
                }
            }
        });

        return view;
    }

    // 数据专递接口
    public interface OnEidtCartItemCountllListener {
        public void changeCount(int count, int position);
    }
}
