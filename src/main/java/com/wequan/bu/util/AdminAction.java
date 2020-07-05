package com.wequan.bu.util;

public enum AdminAction {
    REJECT((short) -1 ), PEDNING((short) 0), APPROVE((short) 1);
    private final short value;
    private AdminAction(short value){
        this.value = value;
    }
    public short getValue(){
        return value;
    }
}
