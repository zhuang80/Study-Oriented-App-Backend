package com.wequan.bu.repository.dao;

import com.wequan.bu.controller.vo.OnlineEvent;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Zhaochao Huang
 */
@Mapper
public interface OnlineEvenMapper extends GeneralMapper<OnlineEvent> {
    List<OnlineEvent> selectByUserId(@Param("user_id") Integer userId);
}
