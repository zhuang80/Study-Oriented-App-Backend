package com.wequan.bu.util;

public enum AppointmentStatus {
    CANCELED((short) -1), DEFAULT((short) 0 ), COMPLETED((short) 1), PROCESSING_REFUND((short) 2), REFUNDED((short) 3), PAID((short) 4);
    private final short value;
    private AppointmentStatus(short value){
        this.value = value;
    }
    public short getValue(){
        return value;
    }
}
