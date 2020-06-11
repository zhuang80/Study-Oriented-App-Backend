package com.wequan.bu.repository.dao;

import com.wequan.bu.repository.model.Material;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author Zhaochao Huang
 */
@Mapper
public interface MaterialMapper extends GeneralMapper<Material>{

    /**
     * select all materials by course id and professor id
     * @param c_id course id
     * @param p_id professor id
     * @return a list of materials uploaded for a certain course taught by a certain professor
     */
    public List<Material> selectByCourseIdAndProfessorId(Integer c_id, Integer p_id);
}
