package com.wequan.bu.repository.dao;

import com.wequan.bu.repository.model.Topic;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Zhaochao Huang
 */
@Mapper
public interface TopicMapper extends GeneralMapper<Topic> {
    @Override
    List<Topic> selectByIds(@Param("ids") String ids);
}
