package com.wequan.bu.repository.dao;

import com.wequan.bu.repository.model.TutorApplicationEducationBackground;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Zhaochao Huang
 */
@Mapper
public interface TutorApplicationEducationBackgroundMapper extends GeneralMapper<TutorApplicationEducationBackground>{
    public List<TutorApplicationEducationBackground> selectByIds(@Param("ids") String ids);
}
