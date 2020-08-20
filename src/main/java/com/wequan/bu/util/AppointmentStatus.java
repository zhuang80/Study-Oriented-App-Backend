package com.wequan.bu.util;

public enum AppointmentStatus {
    /**
     * the status of appointment
     */
    DEFAULT((short) 0), PAID((short) 1), IN_PROGRESS((short) 2), COMPLETED((short) 3), REVIEWED((short) 4),
    CANCELLED((short) -1), PROCESSING_REFUND((short) -2), REFUNDED((short) -3);
    private final short value;
    private AppointmentStatus(short value){
        this.value = value;
    }
    public short getValue(){
        return value;
    }
}
