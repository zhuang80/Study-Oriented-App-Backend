package com.wequan.bu.service.impl;

import com.wequan.bu.repository.dao.CancellationPolicyMapper;
import com.wequan.bu.repository.model.CancellationPolicy;
import com.wequan.bu.service.AbstractService;
import com.wequan.bu.service.CancellationPolicyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * @author Zhaochao Huang
 */
@Service
public class CancellationPolicyServiceImpl extends AbstractService<CancellationPolicy> implements CancellationPolicyService {
    @Autowired
    private CancellationPolicyMapper cancellationPolicyMapper;

    @PostConstruct
    public void postConstruct(){
        this.setMapper(cancellationPolicyMapper);
    }

    @Override
    public CancellationPolicy findByTutorId(Integer tutorId){
        return cancellationPolicyMapper.selectByTutorId(tutorId);
    }

}
