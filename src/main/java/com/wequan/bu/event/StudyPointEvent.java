package com.wequan.bu.event;

import com.wequan.bu.controller.vo.StudyPointHistory;
import org.springframework.context.ApplicationEvent;

/**
 * @author ChrisChen
 */
public class StudyPointEvent extends ApplicationEvent {

    public StudyPointEvent(StudyPointHistory source) {
        super(source);
    }

    @Override
    public StudyPointHistory getSource() {
        return (StudyPointHistory)super.getSource();
    }
}
