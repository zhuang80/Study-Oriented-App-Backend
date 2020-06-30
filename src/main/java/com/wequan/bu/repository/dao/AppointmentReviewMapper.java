package com.wequan.bu.repository.dao;

import com.wequan.bu.repository.model.AppointmentReview;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

/**
 * @author ChrisChen
 */
@Mapper
public interface AppointmentReviewMapper extends GeneralMapper<AppointmentReview> {

    /**
     * 返回用户appointment review列表
     * @param userId 用户id
     * @param rowBounds 分页
     * @return appointment review列表
     */
    List<AppointmentReview> selectByUserId(@Param("userId") Integer userId, RowBounds rowBounds);
}