package com.wequan.bu;

import com.wequan.bu.controller.vo.StudyPointHistory;
import com.wequan.bu.event.StudyPointEvent;
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
		StudyPointHistory pointHistory = new StudyPointHistory();
		pointHistory.setChangeAmount(-100);
		StudyPointEvent studyPointEvent = new StudyPointEvent(pointHistory);
		applicationContext.publishEvent(studyPointEvent);
	}

}