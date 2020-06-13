package com.wequan.bu.event.listener;

import com.wequan.bu.event.StudyPointEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

/**
 * @author ChrisChen
 */
@Component
public class WeQuanEventListeners {

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void handleStudyPointEvent(StudyPointEvent studyPointEvent) {
        System.out.println("=========" + studyPointEvent);
    }

}
