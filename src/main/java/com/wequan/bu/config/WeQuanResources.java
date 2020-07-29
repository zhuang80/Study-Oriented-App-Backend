package com.wequan.bu.config;

/**
 * @author ChrisChen
 */

public enum WeQuanResources {
    /**
     * 1 -> tutor; 2 -> course; 3 -> material; 4 -> thread;
     * 5 -> professor; 6 -> activity; 7 -> public class; 8 -> thread reply
     */
    TUTOR("tutor", 1),
    COURSE("course", 2),
    MATERIAL("material", 3),
    THREAD("thread", 4),
    PROFESSOR("professor", 5),
    ACTIVITY("activity", 6),
    PUBLIC_CLASS("public class", 7),
    THREAD_REPLY("thread reply", 8);

    String resourceName;
    int resourceId;

    WeQuanResources(String resourceName, int resourceId) {
        this.resourceName = resourceName;
        this.resourceId = resourceId;
    }

    public String getResourceName() {
        return resourceName;
    }

    public int getResourceId() {
        return resourceId;
    }

    public int getResourceType() {
        return resourceId;
    }
}
