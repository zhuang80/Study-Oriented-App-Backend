package com.wequan.bu.repository.dao;

import com.wequan.bu.repository.model.ThreadStream;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

/**
 * @author ChrisChen
 */
@Mapper
public interface ThreadStreamMapper extends GeneralMapper<ThreadStream> {

    /**
     * 获取用户回复帖子列表
     * @param userId 用户id
     * @param rowBounds 分页
     * @return 用户回复帖子列表
     */
    List<ThreadStream> selectByUserId(@Param("userId") Integer userId, RowBounds rowBounds);
}
