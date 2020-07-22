package com.wequan.bu.event;

import org.springframework.context.ApplicationEvent;

/**
 * @author ChrisChen
 */
public class ViewEvent extends ApplicationEvent {

    private ViewType viewType;
    private int userId;
    private int targetId;

    private ViewEvent(Object source, ViewType viewType, int userId, int targetId) {
        super(source);
        this.viewType = viewType;
        this.userId = userId;
        this.targetId = targetId;
    }

    public static ViewEvent createOn(ViewType viewType, int userId, int targetId) {
        Object source = new Object();
        return new ViewEvent(source, viewType, userId, targetId);
    }

    public ViewType getViewType() {
        return viewType;
    }

    public int getUserId() {
        return userId;
    }

    public int getTargetId() {
        return targetId;
    }

    public enum ViewType {
        /**
         * View Type
         */
        TUTOR(1),
        COURSE(2),
        MATERIAL(3),
        THREAD(4),
        PROFESSOR(5),
        ACTIVITY(6),
        PUBLIC_CLASS(7),
        THREAD_REPLY(8);

        private int type;

        ViewType(int type) {
            this.type = type;
        }

        public int getType() {
            return type;
        }
    }
}
