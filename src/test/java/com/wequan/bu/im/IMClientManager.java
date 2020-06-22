package com.wequan.bu.im;

import com.wequan.bu.im.conf.ConfigEntity;
import com.wequan.bu.im.event.ChatBaseEventImpl;
import com.wequan.bu.im.event.ChatTransDataEventImpl;
import com.wequan.bu.im.event.MessageQoSEventImpl;

/**
 * @author zhen
 */
public class IMClientManager {
    private static String TAG = IMClientManager.class.getSimpleName();

    private static IMClientManager instance = null;

    private boolean init = false;

    // 
    private ChatBaseEventImpl baseEventListener = null;
    //
    private ChatTransDataEventImpl transDataListener = null;
    //
    private MessageQoSEventImpl messageQoSListener = null;

    public static IMClientManager getInstance() {
        if (instance == null)
            instance = new IMClientManager();
        return instance;
    }

    private IMClientManager() {
        initIMSDK();
    }

    public void initIMSDK() {
        if (!init) {

            // 设置服务器ip和服务器端口
            ConfigEntity.serverIP = "127.0.0.1";
            ConfigEntity.serverUDPPort = 7901;

//			ConfigEntity.setSenseMode(SenseMode.MODE_10S);

            // 开启/关闭DEBUG信息输出
//	    	ClientCoreSDK.DEBUG = false;

            // 设置事件回调
            baseEventListener = new ChatBaseEventImpl();
            transDataListener = new ChatTransDataEventImpl();
            messageQoSListener = new MessageQoSEventImpl();
            ClientCore.getInstance().setChatBaseEvent(baseEventListener);
            ClientCore.getInstance().setChatTransDataEvent(transDataListener);
            ClientCore.getInstance().setMessageQoSEvent(messageQoSListener);

            init = true;
        }
    }

    public void release() {
        ClientCore.getInstance().release();
        resetInitFlag();
    }

    public void resetInitFlag() {
        init = false;
    }

    public ChatTransDataEventImpl getTransDataListener() {
        return transDataListener;
    }

    public ChatBaseEventImpl getBaseEventListener() {
        return baseEventListener;
    }

    public MessageQoSEventImpl getMessageQoSListener() {
        return messageQoSListener;
    }
}
