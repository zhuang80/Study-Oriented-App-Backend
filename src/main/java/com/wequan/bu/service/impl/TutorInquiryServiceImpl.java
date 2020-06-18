package com.wequan.bu.service.impl;

import com.wequan.bu.controller.vo.TutorInquiryVo;
import com.wequan.bu.repository.dao.TutorInquiryMapper;
import com.wequan.bu.repository.model.TutorInquiry;
import com.wequan.bu.service.AbstractService;
import com.wequan.bu.service.TutorInquiryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * @author Zhaochao Huang
 */
@Service
public class TutorInquiryServiceImpl extends AbstractService<TutorInquiry> implements TutorInquiryService {
    @Autowired
    private TutorInquiryMapper tutorInquiryMapper;

    @PostConstruct
    public void postConstruct(){
        this.setMapper(tutorInquiryMapper);
    }


    @Override
    public List<TutorInquiry> findBySubject(Integer subjectId) {
        return tutorInquiryMapper.selectBySubject(subjectId);
    }

    @Override
    public void save(TutorInquiryVo tutorInquiry) {
        tutorInquiryMapper.save(tutorInquiry);
    }
}
