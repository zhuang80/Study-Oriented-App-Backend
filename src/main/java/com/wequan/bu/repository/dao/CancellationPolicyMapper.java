package com.wequan.bu.repository.dao;

import com.wequan.bu.repository.model.CancellationPolicy;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author Zhaochao Huang
 */
@Mapper
public interface CancellationPolicyMapper extends GeneralMapper<CancellationPolicy> {

    public CancellationPolicy selectByTutorId(@Param("tutor_id") Integer tutorId);
}
