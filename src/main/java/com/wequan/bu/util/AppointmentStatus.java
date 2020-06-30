package com.wequan.bu.util;

public enum AppointmentStatus {
    CANCELED((short) -1), DEFAULT((short) 0 ), COMPLETED((short) 1);
    private final short value;
    private AppointmentStatus(short value){
        this.value = value;
    }
    public short getValue(){
        return value;
    }
}
