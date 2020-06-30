package com.wequan.bu.util;

public enum TransactionType {
    APPOINTMENT(0), PUBLIC_CLASS(1), STUDY_POINT(2);
    private final int value;
    private TransactionType(int value){
        this.value = value;
    }
    public int getValue(){
        return value;
    }
}
