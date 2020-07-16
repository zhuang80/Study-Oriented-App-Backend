package com.wequan.bu.repository.dao;

import com.wequan.bu.repository.model.OnlineEvent;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

/**
 * @author Zhaochao Huang
 */
@Mapper
public interface OnlineEventMapper extends GeneralMapper<OnlineEvent> {

    List<OnlineEvent> selectByUserId(@Param("user_id") Integer userId);

    /**
     * 根据用户id和type获取online event列表
     * @param userId 用户id
     * @param typeId online event类型
     * @param rowBounds 分页
     * @return online event列表
     */
    List<OnlineEvent> selectByUserIdAndType(@Param("userId") Integer userId, @Param("type") Integer typeId, RowBounds rowBounds);

    List<OnlineEvent> selectAllByType(@Param("type") Short type);

    List<OnlineEvent> selectAllBySchoolId(@Param("school_id") Integer schoolId);
}
