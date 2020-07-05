package com.wequan.bu.util;

public enum TutorApplicationStatus {
    DELETED((short) -1), PENDING((short) 0), APPROVE((short) 1), REJECT((short) 2), REQUIRE_AMEND((short) 2);
    private final short value;
    private TutorApplicationStatus(short value){
        this.value = value;
    }
    public short getValue(){
        return value;
    }
}
