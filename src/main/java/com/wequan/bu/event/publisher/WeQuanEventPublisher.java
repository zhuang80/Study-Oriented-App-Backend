package com.wequan.bu.event.publisher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class WeQuanEventPublisher {

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;


}
