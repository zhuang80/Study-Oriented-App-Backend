package com.wequan.bu.service;

import com.wequan.bu.controller.vo.Transaction;
import com.wequan.bu.repository.model.StudyPointHistory;

import java.util.List;

/**
 * @author ChrisChen
 */
public interface StudyPointService extends Service<StudyPointHistory> {

    /**
     * 获取用户学习积分变动历史
     * @param userId 用户id
     * @param pageNum pageNum
     * @param pageSize pageSize
     * @return 学习积分变动历史
     */
    List<StudyPointHistory> getUserStudyPointTransactions(Integer userId, Integer pageNum, Integer pageSize);

    /**
     * 添加学习积分
     * @param studyPoint 学习积分
     */
    void addStudyPoint(StudyPointHistory studyPoint);

    void addStudyPointHistoryAndUpdateUserProfile(Transaction transaction);
}
