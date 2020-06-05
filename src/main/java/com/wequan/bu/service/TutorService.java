package com.wequan.bu.service;

import com.wequan.bu.repository.model.Tutor;

import java.util.List;
import java.util.Map;

/**
 * @author ChrisChen
 */
public interface TutorService extends Service<Tutor> {

    /**
     * 按搜索条件搜索tutor
     * @param whereCondition where
     * @param groupCondition group by
     * @param orderCondition order by
     * @param pageCondition page
     * @return Tutor列表
     */
    List<Tutor> search(String whereCondition, String groupCondition, String orderCondition, Map<String, Integer> pageCondition);
}
