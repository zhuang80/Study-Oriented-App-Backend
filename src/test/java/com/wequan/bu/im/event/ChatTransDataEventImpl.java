package com.wequan.bu.im.event;

//import com.wequan.bu.im.demo.MainGUI;

import com.wequan.bu.im.utils.Log;

/**
 * @author zhen
 */
public class ChatTransDataEventImpl implements ChatTransDataEvent {
    private final static String TAG = ChatTransDataEventImpl.class.getSimpleName();

//	private MainGUI mainGUI = null;

    @Override
    public void onTransBuffer(String fingerPrintOfProtocal, String userid, String dataContent, int typeu) {
        Log.d(TAG, "【DEBUG_UI】[typeu=" + typeu + "]收到来自用户" + userid + "的消息:" + dataContent);
//
//		if(mainGUI != null)
//		{
////			this.mainGUI.showToast(dwUserid+"说："+dataContent);
//			this.mainGUI.showIMInfo_black(userid+"说："+dataContent);
//		}
    }

//	public ChatTransDataEventImpl setMainGUI(MainGUI mainGUI)
//	{
////		this.mainGUI = mainGUI;
//		return this;
//	}

    @Override
    public void onErrorResponse(int errorCode, String errorMsg) {
        Log.d(TAG, "【DEBUG_UI】收到服务端错误消息，errorCode=" + errorCode + ", errorMsg=" + errorMsg);

//		if(errorCode ==  ErrorCode.ForS.RESPONSE_FOR_UNLOGIN)
//			this.mainGUI.showIMInfo_brightred("服务端会话已失效，自动登陆/重连将启动! ("+errorCode+")");
//		else
//			this.mainGUI.showIMInfo_red("Server反馈错误码："+errorCode+",errorMsg="+errorMsg);
    }
}
