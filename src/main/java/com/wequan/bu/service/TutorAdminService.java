package com.wequan.bu.service;

import com.wequan.bu.controller.vo.TutorApplicationVo;
import com.wequan.bu.repository.model.TutorApplication;

import java.io.IOException;

/**
 * @author Zhaochao Huang
 */
public interface TutorAdminService extends Service<TutorApplication> {

    public void apply(TutorApplicationVo tutorApplicationVo) throws IOException;


}
