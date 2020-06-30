package com.wequan.bu.util;

public enum TransactionStatus {
    CANCELED((short) -1), REQUIRES_PAYMENT_METHOD((short) 0), PROCESSING((short)1), SUCCEEDED((short)2),
        PROCESSING_REFUND((short) 4), REFUNDED((short) 5);
    private final short value;
    private TransactionStatus(short value){
        this.value = value;
    }
    public short getValue(){
        return value;
    }
}
