package com.wequan.bu.service.impl;

import com.github.pagehelper.PageHelper;
import com.wequan.bu.controller.vo.TutorInquiryVo;
import com.wequan.bu.repository.dao.TutorInquiryMapper;
import com.wequan.bu.repository.model.TutorInquiry;
import com.wequan.bu.service.AbstractService;
import com.wequan.bu.service.TutorInquiryService;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;

/**
 * @author Zhaochao Huang
 */
@Service
public class TutorInquiryServiceImpl extends AbstractService<TutorInquiry> implements TutorInquiryService {

    private static final Logger log = LoggerFactory.getLogger(TutorInquiryServiceImpl.class);

    @Autowired
    private TutorInquiryMapper tutorInquiryMapper;

    @PostConstruct
    public void postConstruct(){
        this.setMapper(tutorInquiryMapper);
    }

    @Override
    public List<TutorInquiry> findBySubject(Integer subjectId, Integer pageNum, Integer pageSize) {
        //the limit indicates how many tutor inquiries displayed under each subject
        Integer limit = 4;
        if(pageNum == null || pageNum <= 0 ) {
            pageNum = 1;
        }
        if(pageSize == null || pageSize <= 0){
            pageSize = 10;
        }
        PageHelper.startPage(pageNum, pageSize);
        List<TutorInquiry> tutorInquiryList;
        if(subjectId == null){
            tutorInquiryList = tutorInquiryMapper.selectPartPerSubject(limit);
        }else {
            tutorInquiryList = tutorInquiryMapper.selectBySubject(subjectId);
        }

        return tutorInquiryList;
    }

    @Override
    public void save(TutorInquiryVo tutorInquiry) {
        tutorInquiryMapper.save(tutorInquiry);
    }

    @Override
    public List<TutorInquiry> getUserTutorInquiries(Integer userId, Integer pageNum, Integer pageSize) {
        RowBounds rowBounds = new RowBounds(pageNum, pageSize);
        return tutorInquiryMapper.selectByUserId(userId, rowBounds);
    }

    @Override
    public List<TutorInquiry> search(String whereCondition, String orderCondition, Map<String, Integer> pageCondition) {
        List<TutorInquiry> tutorInquiries = null;
        tutorInquiries = tutorInquiryMapper.selectByConditions(whereCondition, orderCondition,
                new RowBounds(pageCondition.get("pageNo"), pageCondition.get("pageSize")));
        return tutorInquiries;
    }
}
