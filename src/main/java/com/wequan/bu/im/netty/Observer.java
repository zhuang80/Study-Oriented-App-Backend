package com.wequan.bu.im.netty;

/**
 * @author zhen
 */
public interface Observer {
    void update(boolean sucess, Object extraObj);
}
