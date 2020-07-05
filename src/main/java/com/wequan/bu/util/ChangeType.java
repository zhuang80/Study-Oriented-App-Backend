package com.wequan.bu.util;

/**
 * @author Zhaochao Huang
 */
public enum ChangeType {
    BEFORE_PAYMENT((short) 1), BEFORE_APPOINTMENT((short) 2), AFTER_APPOINTMENT((short) 3);
    private final short value;
    private ChangeType(short value){
        this.value = value;
    }
    public short getValue(){
        return value;
    }
}
