package com.wequan.bu.util;

public enum AvailabilityStatus {
    UNAVAILABLE((short) -1), AVAILABLE((short) 1);
    private final short value;
    private AvailabilityStatus(short value){
        this.value = value;
    }
    public short getValue(){
        return value;
    }
}
