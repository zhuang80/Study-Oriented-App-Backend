package com.wequan.bu.util;

public enum TutorAvailability {
        AVAILABLE(1), CLOSE(0);
        private final int value;
        private TutorAvailability(int value){
            this.value = value;
        }
        private int getValue(){
            return value;
         }
}
