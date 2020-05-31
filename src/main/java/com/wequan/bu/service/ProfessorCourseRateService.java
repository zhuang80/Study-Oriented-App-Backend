package com.wequan.bu.service;

import com.wequan.bu.repository.model.ProfessorCourseRate;

import java.util.List;

/**
 * @author Zhaochao Huang
 */
public interface ProfessorCourseRateService extends Service<ProfessorCourseRate> {

    /**
     *get a list of certain professor reviews for a certain course
     * @param p_id the professor id
     * @param c_id the course id
     * @return a list of reviews
     */
    public List<ProfessorCourseRate> findAllByProfessorIdAndCourseId(Integer p_id, Integer c_id);
}
