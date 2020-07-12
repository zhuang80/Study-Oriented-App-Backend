package com.wequan.bu.controller.vo;

/**
 * @author ChrisChen
 */
public enum StorageKeyCategory {
    /**
     * Path on AWS S3
     */
    PROFILE(1),
    TUTOR_APPLICATION(2),
    THREAD(3),
    THREAD_STREAM(4),
    DISCUSSION_GROUP(5),
    COURSE_MATERIAL(6),
    PUBLIC_CLASS(7),
    ACTIVITY(8);

    int keyCategory;

    StorageKeyCategory(int keyCategory) {
        this.keyCategory = keyCategory;
    }

    public int getKeyCategory() {
        return keyCategory;
    }

    public static String getStorageKey(int keyCategory) {
        String storageKey = null;
        for (StorageKeyCategory storageKeyCategory : values()) {
            if (storageKeyCategory.keyCategory == keyCategory) {
                storageKey = storageKeyCategory.name().toLowerCase();
                break;
            }
        }
        return storageKey;
    }

}
