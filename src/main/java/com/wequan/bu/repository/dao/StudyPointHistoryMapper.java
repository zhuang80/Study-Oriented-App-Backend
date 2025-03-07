package com.wequan.bu.repository.dao;

import com.wequan.bu.repository.model.StudyPointHistory;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

/**
 * @author ChrisChen
 */
@Mapper
public interface StudyPointHistoryMapper extends GeneralMapper<StudyPointHistory> {

    /**
     * 获取用户学习积分变动历史
     * @param userId 用户id
     * @param rowBounds 分页
     * @return 学习积分变动历史
     */
    List<StudyPointHistory> selectByUserId(Integer userId, RowBounds rowBounds);

    /**
     * 插入积分变动记录
     * @param studyPointHistory 积分变动记录
     */
    void insert(StudyPointHistory studyPointHistory);
}
