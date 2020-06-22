package com.wequan.bu.im;

import com.wequan.bu.im.core.AutoReLoginDaemon;
import com.wequan.bu.im.core.KeepAliveDaemon;
import com.wequan.bu.im.core.LocalUDPDataReciever;
import com.wequan.bu.im.core.LocalUDPSocketProvider;
import com.wequan.bu.im.core.QoS4ReciveDaemon;
import com.wequan.bu.im.core.QoS4SendDaemon;
import com.wequan.bu.im.event.ChatBaseEvent;
import com.wequan.bu.im.event.ChatTransDataEvent;
import com.wequan.bu.im.event.MessageQoSEvent;

/**
 * @author zhen
 */
public class ClientCore {
    private final static String TAG = ClientCore.class.getSimpleName();

    public static boolean DEBUG = true;
    public static boolean autoReLogin = true;

    private static ClientCore instance = null;

    private boolean _init = false;
    private boolean connectedToServer = true;
    private boolean loginHasInit = false;
    private String currentLoginUserId = null;
    private String currentLoginToken = null;
    private String currentLoginExtra = null;

    private ChatBaseEvent chatBaseEvent = null;
    private ChatTransDataEvent chatTransDataEvent = null;
    private MessageQoSEvent messageQoSEvent = null;

    public static ClientCore getInstance() {
        if (instance == null)
            instance = new ClientCore();
        return instance;
    }

    private ClientCore() {
    }

    public void init() {
        if (!_init) {
            _init = true;
        }
    }

    public void release() {
        LocalUDPSocketProvider.getInstance().closeLocalUDPSocket();
        AutoReLoginDaemon.getInstance().stop();
        QoS4SendDaemon.getInstance().stop();
        KeepAliveDaemon.getInstance().stop();
        LocalUDPDataReciever.getInstance().stop();
        QoS4ReciveDaemon.getInstance().stop();

        QoS4SendDaemon.getInstance().clear();
        QoS4ReciveDaemon.getInstance().clear();

        _init = false;

        this.setLoginHasInit(false);
        this.setConnectedToServer(false);
    }

    public String getCurrentLoginUserId() {
        return currentLoginUserId;
    }

    public ClientCore setCurrentLoginUserId(String currentLoginUserId) {
        this.currentLoginUserId = currentLoginUserId;
        return this;
    }

    public String getCurrentLoginToken() {
        return currentLoginToken;
    }

    public void setCurrentLoginToken(String currentLoginToken) {
        this.currentLoginToken = currentLoginToken;
    }

    public String getCurrentLoginExtra() {
        return currentLoginExtra;
    }

    public ClientCore setCurrentLoginExtra(String currentLoginExtra) {
        this.currentLoginExtra = currentLoginExtra;
        return this;
    }

    public boolean isLoginHasInit() {
        return loginHasInit;
    }

    public ClientCore setLoginHasInit(boolean loginHasInit) {
        this.loginHasInit = loginHasInit;
        return this;
    }

    public boolean isConnectedToServer() {
        return connectedToServer;
    }

    public void setConnectedToServer(boolean connectedToServer) {
        this.connectedToServer = connectedToServer;
    }

    public boolean isInitialed() {
        return this._init;
    }

    public void setChatBaseEvent(ChatBaseEvent chatBaseEvent) {
        this.chatBaseEvent = chatBaseEvent;
    }

    public ChatBaseEvent getChatBaseEvent() {
        return chatBaseEvent;
    }

    public void setChatTransDataEvent(ChatTransDataEvent chatTransDataEvent) {
        this.chatTransDataEvent = chatTransDataEvent;
    }

    public ChatTransDataEvent getChatTransDataEvent() {
        return chatTransDataEvent;
    }

    public void setMessageQoSEvent(MessageQoSEvent messageQoSEvent) {
        this.messageQoSEvent = messageQoSEvent;
    }

    public MessageQoSEvent getMessageQoSEvent() {
        return messageQoSEvent;
    }
}
