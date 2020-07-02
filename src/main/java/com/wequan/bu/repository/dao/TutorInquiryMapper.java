package com.wequan.bu.repository.dao;

import com.wequan.bu.controller.vo.TutorInquiryVo;
import com.wequan.bu.repository.model.Topic;
import com.wequan.bu.repository.model.TutorInquiry;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

/**
 * @author Zhaochao Huang
 */
@Mapper
public interface TutorInquiryMapper extends GeneralMapper<TutorInquiry> {
    List<TutorInquiry> selectBySubject(@Param("subject_id") Integer subjectId);

    void save(TutorInquiryVo tutorInquiry);

    List<TutorInquiry> selectPartPerSubject(@Param("limit") Integer limit);

    List<Topic> selectTopic();

    /**
     * 根据用户id获取tutor inquiry列表
     * @param userId 用户id
     * @param rowBounds 分页
     * @return tutor inquiry列表
     */
    List<TutorInquiry> selectByUserId(@Param("userId") Integer userId, RowBounds rowBounds);

    /**
     * 按条件获取Tutor inquiry列表
     * @param whereCondition where
     * @param orderCondition order by
     * @param rowBounds 分页
     * @return tutor inquiries列表
     */
    List<TutorInquiry> selectByConditions(@Param("where") String whereCondition, @Param("orderBy") String orderCondition, RowBounds rowBounds);
}
