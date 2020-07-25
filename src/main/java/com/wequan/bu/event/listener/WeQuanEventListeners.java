package com.wequan.bu.event.listener;

import com.wequan.bu.event.StudyPointEvent;
import com.wequan.bu.event.ViewEvent;
import com.wequan.bu.repository.model.StudyPointHistory;
import com.wequan.bu.service.StudyPointService;
import com.wequan.bu.service.ThreadService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author ChrisChen
 */
@Component
public class WeQuanEventListeners {

    private static final Logger log = LoggerFactory.getLogger(WeQuanEventListeners.class);

    @Autowired
    private ThreadService threadService;
    @Autowired
    private StudyPointService studyPointService;

    @Async
    @EventListener
    public void handleStudyPointEvent(StudyPointEvent studyPointEvent) {
        StudyPointHistory studyPoint = studyPointEvent.getSource();
        studyPointService.addStudyPoint(studyPoint);
    }

    @Async
    @EventListener
    public void handleViewEvent(ViewEvent viewEvent) {
        ViewEvent.ViewType viewType = viewEvent.getViewType();
        int userId = viewEvent.getUserId();
        int targetId = viewEvent.getTargetId();
        Date viewTime = new Date(viewEvent.getTimestamp());
        switch (viewType) {
            case TUTOR:
                // add view record for tutor
                break;
            case THREAD:
                threadService.addViewRecord(userId, targetId, viewTime);
                break;
            case THREAD_REPLY:
                // add view record for thread reply
                break;
            default:
                break;
        }
    }

}
