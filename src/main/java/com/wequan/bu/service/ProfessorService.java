package com.wequan.bu.service;

import com.wequan.bu.repository.model.Professor;

import java.util.List;

/**
 * @author Zhaochao Huang
 */
public interface ProfessorService extends Service<Professor>{

    /**
     * @param limit the number of reviews showed for each course
     * @return a list of Professor whose courseRates field is present
     */
    List<Professor> findAllWithRateByName(Integer limit, String name);
}
