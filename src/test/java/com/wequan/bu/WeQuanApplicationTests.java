package com.wequan.bu;

import com.wequan.bu.event.StudyPointEvent;
import com.wequan.bu.repository.model.StudyPointHistory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

class WeQuanApplicationTests extends BaseTester {

	@Autowired
	private ApplicationContext applicationContext;

	@Test
	void contextLoads() {
	}

	@Test
	void eventTest() {
		StudyPointHistory pointHistory = StudyPointHistory.builder().changeAmount((short)-100).build();
		StudyPointEvent studyPointEvent = new StudyPointEvent(pointHistory);
		applicationContext.publishEvent(studyPointEvent);
	}

}