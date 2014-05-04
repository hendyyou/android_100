package cn.digione.yibaic.shop.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import cn.digione.yibaic.shop.R;

/**
 * 自定义等待框
 * 
 * @author zhangqr
 * 
 */
public class CustomProgressDialog extends Dialog {
	private Context context = null;
	private static CustomProgressDialog customProgressDialog = null;

	public CustomProgressDialog(Context context) {
		super(context);
		this.context = context;
	}

	public CustomProgressDialog(Context context, int theme) {
		super(context, theme);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.customprogressdialog);
	}

	public void onWindowFocusChanged(boolean hasFocus) {

		ImageView view = (ImageView) findViewById(R.id.loadingImageView);
		Animation rotateAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.progress_bar_anim);
        if (null != rotateAnimation) {
            LinearInterpolator lir = new LinearInterpolator();
            rotateAnimation.setInterpolator(lir);
            view.startAnimation(rotateAnimation);
        }

	}

	/**
	 * 
	 * [Summary] setMessage 提示内容
	 * 
	 * @param strMessage
	 * @return
	 * 
	 * 
	 *         public CustomProgressDialog setMessage(String strMessage){ TextView tvMsg =
	 *         (TextView)customProgressDialog.findViewById(R.id.id_tv_loadingmsg);
	 * 
	 *         if (tvMsg != null){ tvMsg.setText(strMessage); }
	 * 
	 *         return customProgressDialog; }
	 */
}
