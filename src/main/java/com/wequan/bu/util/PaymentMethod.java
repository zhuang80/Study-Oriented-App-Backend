package com.wequan.bu.util;

public enum PaymentMethod {
    CARD((short) 1);
    private final short value;
    private PaymentMethod(short value){
        this.value = value;
    }
    public short getValue(){
        return value;
    }
}
