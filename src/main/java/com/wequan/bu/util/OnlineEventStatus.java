package com.wequan.bu.util;

/**
 * @author Zhaochao Huang
 */
public enum OnlineEventStatus {
    CANCELED((short) -1), SCHEDULED((short) 0), ONGOING((short) 1), DONE((short) 2);
    private final short value;
    private OnlineEventStatus(short value){
        this.value = value;
    }
    public short getValue(){
        return value;
    }
}
