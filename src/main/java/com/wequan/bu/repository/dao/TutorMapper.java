package com.wequan.bu.repository.dao;

import com.wequan.bu.repository.model.Tutor;
import com.wequan.bu.repository.model.extend.TutorRateInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

/**
 * @author Zhen Lin
 */
@Mapper
public interface TutorMapper extends GeneralMapper<Tutor> {

    /**
     * 按条件获取Tutor列表
     * @param whereCondition where
     * @param orderCondition order by
     * @param rowBounds 分页对象
     * @return Tutor列表
     */
    List<TutorRateInfo> selectByConditions(@Param("where") String whereCondition, @Param("orderBy") String orderCondition, RowBounds rowBounds);

    List<Tutor> selectTutors(@Param("subject_id") Integer subjectId);

    List<TutorRateInfo> selectTopTutors(@Param("subject_id") Integer subjectId);

}
