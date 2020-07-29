package com.wequan.bu.repository.dao;

import com.wequan.bu.repository.model.LikeRecord;
import com.wequan.bu.repository.model.extend.LikeRecordBriefInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

/**
 * @author ChrisChen
 */
@Mapper
public interface LikeRecordMapper extends GeneralMapper<LikeRecord> {

    /**
     * 返回用户被点赞列表
     * @param userId 用户id
     * @param rowBounds 分页
     * @return LikeRecord列表
     */
    List<LikeRecordBriefInfo> selectByResourceBelongId(@Param("belongId") Integer userId, RowBounds rowBounds);

}
