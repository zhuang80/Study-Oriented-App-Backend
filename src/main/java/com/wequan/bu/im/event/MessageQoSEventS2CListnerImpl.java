package com.wequan.bu.im.event;

import java.util.ArrayList;

import com.wequan.bu.im.protocal.Protocal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author zhen
 */
public class MessageQoSEventS2CListnerImpl implements MessageQoSEventListenerS2C {
    private static Logger logger = LoggerFactory.getLogger(MessageQoSEventS2CListnerImpl.class);

    @Override
    public void messagesLost(ArrayList<Protocal> lostMessages) {
        logger.debug("【DEBUG_QoS_S2C事件】收到系统的未实时送达事件通知，当前共有"
                + lostMessages.size() + "个包QoS保证机制结束，判定为【无法实时送达】！");
    }

    @Override
    public void messagesBeReceived(String theFingerPrint) {
        if (theFingerPrint != null) {
            logger.debug("【DEBUG_QoS_S2C事件】收到对方已收到消息事件的通知，fp=" + theFingerPrint);
        }
    }
}
