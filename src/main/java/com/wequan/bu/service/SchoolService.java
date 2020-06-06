package com.wequan.bu.service;

import com.wequan.bu.repository.model.School;

import java.util.List;

/**
 * @author Zhaochao Huang
 */
public interface SchoolService extends Service<School>{
    /**
     * get a specified page of schools
     * @param pageNum the number of page
     * @param pageSize the size of each page
     * @return
     */
    public List<School> findAll(Integer pageNum, Integer pageSize);
}
