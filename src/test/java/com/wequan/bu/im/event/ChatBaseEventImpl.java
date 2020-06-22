package com.wequan.bu.im.event;

//import com.wequan.bu.im.demo.MainGUI;

import com.wequan.bu.im.utils.Log;

import java.util.Observer;

/**
 * @author zhen
 */
public class ChatBaseEventImpl implements ChatBaseEvent {
    private final static String TAG = ChatBaseEventImpl.class.getSimpleName();

//	private MainGUI mainGUI = null;

    // 本Observer目前仅用于登陆时（因为登陆与收到服务端的登陆验证结果
    // 是异步的，所以有此观察者来完成收到验证后的处理）
    private Observer loginOkForLaunchObserver = null;

    @Override
    public void onLoginMessage(int dwErrorCode) {
        if (dwErrorCode == 0) {
            Log.p(TAG, "【DEBUG_UI】IM服务器登录/连接成功！");
//
//			// TODO 以下代码仅用于DEMO哦
//			if(this.mainGUI != null)
//			{
//				this.mainGUI.refreshMyid();
//				this.mainGUI.showIMInfo_green("IM服务器登录/连接成功,dwErrorCode="+dwErrorCode);
//			}
        } else {
            Log.e(TAG, "【DEBUG_UI】IM服务器登录/连接失败，错误代码：" + dwErrorCode);
//
//			// TODO 以下代码仅用于DEMO哦
//			if(this.mainGUI != null)
//			{
//				this.mainGUI.refreshMyid();
//				this.mainGUI.showIMInfo_red("IM服务器登录/连接失败,code="+dwErrorCode);
//			}
        }
//
//		// 此观察者只有开启程序首次使用登陆界面时有用
//		if(loginOkForLaunchObserver != null)
//		{
//			loginOkForLaunchObserver.update(null, dwErrorCode);
//			loginOkForLaunchObserver = null;
//		}
    }

    @Override
    public void onLinkCloseMessage(int dwErrorCode) {
        Log.e(TAG, "【DEBUG_UI】与IM服务器的网络连接出错关闭了，error：" + dwErrorCode);
//
//		// TODO 以下代码仅用于DEMO哦
//		if(this.mainGUI != null)
//		{
//			this.mainGUI.refreshMyid();
//			this.mainGUI.showIMInfo_red("与IM服务器的连接已断开, 自动登陆/重连将启动! ("+dwErrorCode+")");
//		}
    }

    public void setLoginOkForLaunchObserver(Observer loginOkForLaunchObserver) {
        this.loginOkForLaunchObserver = loginOkForLaunchObserver;
    }

//	public ChatBaseEventImpl setMainGUI(MainGUI mainGUI)
//	{
//		this.mainGUI = mainGUI;
//		return this;
//	}

}
