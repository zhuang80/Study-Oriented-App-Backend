package com.wequan.bu.util;

public enum TagCategory {
    ONLINE_EVENT(1), DISCUSSION_GROUP(2), THREAD(3), PROFESSOR(4), TUTOR_REVIEW(5);
    private final int value;
    private TagCategory(int value){
        this.value = value;
    }
    public int getValue(){
        return value;
    }

}
