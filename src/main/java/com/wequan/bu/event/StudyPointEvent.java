package com.wequan.bu.event;

import org.springframework.context.ApplicationEvent;

/**
 * @author ChrisChen
 */
public class StudyPointEvent extends ApplicationEvent {

    /**
     * Create a new {@code ApplicationEvent}.
     *
     * @param source the object on which the event initially occurred or with
     *               which the event is associated (never {@code null})
     */
    public StudyPointEvent(Object source) {
        super(source);
    }
}
