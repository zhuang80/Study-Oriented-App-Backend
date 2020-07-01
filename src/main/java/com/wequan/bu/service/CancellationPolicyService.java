package com.wequan.bu.service;

import com.wequan.bu.repository.model.CancellationPolicy;

/**
 * @auhtor Zhaochao Huang
 */
public interface CancellationPolicyService extends Service<CancellationPolicy> {

    public CancellationPolicy findByTutorId(Integer tutorId);
}
