package com.wequan.bu.im.event;

/**
 * @author zhen
 */
public interface ChatBaseEvent {
    public void onLoginMessage(int dwErrorCode);

    public void onLinkCloseMessage(int dwErrorCode);
}
