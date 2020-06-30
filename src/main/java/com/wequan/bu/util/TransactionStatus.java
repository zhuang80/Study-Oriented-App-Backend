package com.wequan.bu.util;

public enum TransactionStatus {
    REQUIRES_PAYMENT_METHOD((short) 0), PROCESSING((short)1), CANCELED((short)2), SUCCEEDED((short)3), REFUNDED((short)4);
    private final short value;
    private TransactionStatus(short value){
        this.value = value;
    }
    public short getValue(){
        return value;
    }
}
