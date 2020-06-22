package com.wequan.bu.im.event;

import java.util.ArrayList;

import com.wequan.bu.im.protocal.Protocal;

/**
 * @author zhen
 */
public interface MessageQoSEvent {
    void messagesLost(ArrayList<Protocal> lostMessages);

    void messagesBeReceived(String theFingerPrint);
}
