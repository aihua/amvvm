package com.dodomath.app.tpl;

import android.content.Context;

import com.dodomath.app.utils.MyLogger;

import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.wechat.friends.Wechat;

public class ThirdPartyLoginHelper implements PlatformActionListener {

	public void loginWithWechat() {
		//微信登录
		//测试时，需要打包签名；sample测试时，用项目里面的demokey.keystore
		//打包签名apk,然后才能产生微信的登录
		Platform wechat = ShareSDK.getPlatform(Wechat.NAME);
		authorize(wechat);
	}

	private void authorize(Platform plat) {
		if (plat == null) {
			MyLogger.d("TPL", "不支持该功能");
			return;
		}

		plat.setPlatformActionListener(this);
		plat.SSOSetting(false);
		plat.showUser(null);
	}

	public void onComplete(Platform platform, int action, HashMap<String, Object> res) {
		if (action == Platform.ACTION_USER_INFOR) {
			MyLogger.d("TPL", "onComplete");
		}
	}
	
	public void onError(Platform platform, int action, Throwable t) {
		if (action == Platform.ACTION_USER_INFOR) {
			MyLogger.d("TPL", "onError");
		}
		t.printStackTrace();
	}
	
	public void onCancel(Platform platform, int action) {
		if (action == Platform.ACTION_USER_INFOR) {
			MyLogger.d("TPL", "onCancel");
		}
	}
	
	@SuppressWarnings("unchecked")
//	public boolean handleMessage(Message msg) {
//		switch(msg.what) {
//			case MSG_AUTH_CANCEL: {
//				//取消授权
//				Toast.makeText(activity, R.string.auth_cancel, Toast.LENGTH_SHORT).show();
//			} break;
//			case MSG_AUTH_ERROR: {
//				//授权失败
//				Toast.makeText(activity, R.string.auth_error, Toast.LENGTH_SHORT).show();
//			} break;
//			case MSG_AUTH_COMPLETE: {
//				//授权成功
//				Toast.makeText(activity, R.string.auth_complete, Toast.LENGTH_SHORT).show();
//				Object[] objs = (Object[]) msg.obj;
//				String platform = (String) objs[0];
//				HashMap<String, Object> res = (HashMap<String, Object>) objs[1];
//				if (signupListener != null && signupListener.onSignin(platform, res)) {
//					SignupPage signupPage = new SignupPage();
//					signupPage.setOnLoginListener(signupListener);
//					signupPage.setPlatform(platform);
//					signupPage.show(activity, null);
//				}
//			} break;
//			case MSG_SMSSDK_CALLBACK: {
//				if (msg.arg2 == SMSSDK.RESULT_ERROR) {
//					Toast.makeText(activity, "操作失败", Toast.LENGTH_SHORT).show();
//				} else {
//					switch (msg.arg1) {
//						case SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE: {
//							if(msgLoginDlg != null && msgLoginDlg.isShowing()){
//								msgLoginDlg.dismiss();
//							}
//							Toast.makeText(activity, "提交验证码成功", Toast.LENGTH_SHORT).show();
//							Message m = new Message();
//							m.what = MSG_AUTH_COMPLETE;
//							m.obj = new Object[] {"SMSSDK", (HashMap<String, Object>) msg.obj};
//							handler.sendMessage(m);
//						} break;
//						case SMSSDK.EVENT_GET_VERIFICATION_CODE:{
//							Toast.makeText(activity, "验证码已经发送", Toast.LENGTH_SHORT).show();
//						} break;
//					}
//				}
//			} break;
//		}
//		return false;
//	}
//

	public static void initSDK(Context context) {
		ShareSDK.initSDK(context);
	}
	
}
