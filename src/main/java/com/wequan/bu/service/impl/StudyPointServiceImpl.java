package com.wequan.bu.service.impl;

import com.wequan.bu.controller.vo.Transaction;
import com.wequan.bu.repository.dao.StudyPointHistoryMapper;
import com.wequan.bu.repository.dao.UserMapper;
import com.wequan.bu.repository.model.StudyPointHistory;
import com.wequan.bu.repository.model.User;
import com.wequan.bu.service.AbstractService;
import com.wequan.bu.service.StudyPointService;
import com.wequan.bu.service.TransactionService;
import com.wequan.bu.util.PaymentMethod;
import com.wequan.bu.util.TransactionType;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * @author ChrisChen
 */
@Service
public class StudyPointServiceImpl extends AbstractService<StudyPointHistory> implements StudyPointService {

    private static final Logger log = LoggerFactory.getLogger(StudyPointServiceImpl.class);

    @Autowired
    private StudyPointHistoryMapper studyPointHistoryMapper;
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private TransactionService transactionService;

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
    @Transactional(rollbackFor = Exception.class)
    public void addStudyPoint(StudyPointHistory studyPoint) {
        Integer userId = studyPoint.getUserId();
        Short changeAmount = studyPoint.getChangeAmount();
        userMapper.updateStudyPointByUserId(userId, changeAmount);
        studyPointHistoryMapper.insert(studyPoint);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addStudyPointHistoryAndUpdateUserProfile(Transaction transaction) {
        User user = userMapper.selectByPrimaryKey(transaction.getPayFrom());
        Short studyPoints = user.getStudyPoints();
        Short increment =(short) (long) transaction.getPayAmount();

        userMapper.updateStudyPointByUserId(user.getId(), increment);

        StudyPointHistory studyPointHistory = StudyPointHistory.builder().build();
        studyPointHistory.setUserId(transaction.getPayFrom());
        studyPointHistory.setActionLog("Top Up");
        studyPointHistory.setChangeAmount(increment);
        studyPointHistory.setActionTime(new Date());
        studyPointHistory.setRemainingAmount((short) (studyPoints + increment));
        studyPointHistory.setTransactionId(transaction.getId());

        studyPointHistoryMapper.insertSelective(studyPointHistory);
    }

    @Override
    public void save(StudyPointHistory studyPointHistory) {
        studyPointHistoryMapper.insert(studyPointHistory);
    }
}
