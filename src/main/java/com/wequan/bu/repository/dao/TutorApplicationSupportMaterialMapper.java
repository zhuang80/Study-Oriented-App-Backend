package com.wequan.bu.repository.dao;

import com.wequan.bu.repository.model.TutorApplicationSupportMaterial;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Zhaochao Huang
 */
@Mapper
public interface TutorApplicationSupportMaterialMapper extends GeneralMapper<TutorApplicationSupportMaterial> {
    public List<TutorApplicationSupportMaterial> selectByIds(@Param("ids") String ids);
}
