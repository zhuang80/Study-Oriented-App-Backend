package com.wequan.bu.controller.vo;

import com.wequan.bu.service.*;

/**
 * @author ChrisChen
 */
public enum FavoriteCategory {

    /**
     *
     */
    TUTOR(1, FavoriteTutorService.class),
    THREAD(4, FavoriteThreadService .class),
    THREAD_REPLY(8, FavoriteThreadStreamService.class);

    int category;
    Class<? extends Service> favoriteService;

    FavoriteCategory(int category, Class<? extends Service> favoriteService) {
        this.category = category;
        this.favoriteService = favoriteService;
    }

    public static Class<? extends Service> getFavoriteService(int category) {
        Class<? extends Service> favoriteService = null;
        for (FavoriteCategory e : values()) {
            if (e.category == category) {
                favoriteService = e.favoriteService;
                break;
            }
        }
        return favoriteService;
    }

}
