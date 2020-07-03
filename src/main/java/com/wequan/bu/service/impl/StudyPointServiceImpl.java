package com.wequan.bu.service.impl;

import com.wequan.bu.repository.dao.StudyPointHistoryMapper;
import com.wequan.bu.repository.model.StudyPointHistory;
import com.wequan.bu.service.AbstractService;
import com.wequan.bu.service.StudyPointService;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * @author ChrisChen
 */
@Service
public class StudyPointServiceImpl extends AbstractService<StudyPointHistory> implements StudyPointService {

    private static final Logger log = LoggerFactory.getLogger(StudyPointServiceImpl.class);

    @Autowired
    private StudyPointHistoryMapper studyPointHistoryMapper;

    @PostConstruct
    public void postConstruct() {
        this.setMapper(studyPointHistoryMapper);
    }

    @Override
    public List<StudyPointHistory> getUserStudyPointTransactions(Integer userId, Integer pageNum, Integer pageSize) {
        RowBounds rowBounds = new RowBounds(pageNum, pageSize);
        return studyPointHistoryMapper.selectByUserId(userId);
    }

    @Override
    public void save(StudyPointHistory studyPointHistory) {
        studyPointHistoryMapper.insert(studyPointHistory);
    }
}
