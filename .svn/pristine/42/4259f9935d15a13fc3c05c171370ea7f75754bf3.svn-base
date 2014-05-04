package cn.digione.yibaic.shop.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import cn.digione.yibaic.shop.R;
import cn.digione.yibaic.shop.common.Constants;

/**
 * Created with IntelliJ IDEA.
 * User: Simpson
 * Date: 2014/4/11
 * Time: 13:33
 */
public class PhoneOperateResultFragment extends StepsFragment {

    protected void onButtonFinishClicked() {
        getActivity().finish();
    }

    public View onCreateView(LayoutInflater layoutinflater, ViewGroup viewgroup, Bundle bundle) {
        super.onCreateView(layoutinflater, viewgroup, bundle);
        View view = layoutinflater.inflate(R.layout.phone_operate_result_fragment, viewgroup, false);
        if (null == view) {
            return null;
        }
        super.isUpdateShopCartData = false;
        Bundle argBundle = getArguments();
        getActivity().setTitle(argBundle.getInt(Constants.ArgName.UI.PhoneOperateResult.TITLE_NAME));
        TextView mStatusTextView = (TextView) view.findViewById(R.id.tv_operate_result);
        mStatusTextView.setText(argBundle.getInt(Constants.ArgName.UI.PhoneOperateResult.STATUS_TEXT_ID));
        return view;
    }

    public void onResume() {
        super.onResume();
        displaySoftInputIfNeed(getView(), false);
    }
}
