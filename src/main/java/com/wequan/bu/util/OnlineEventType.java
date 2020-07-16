package com.wequan.bu.util;

public enum OnlineEventType {
    PUBLIC_CLASS((short) 0), ACTIVITY((short) 1);
    private final short value;
    private OnlineEventType(short value){
        this.value = value;
    }
    public short getValue(){
        return value;
    }
}
