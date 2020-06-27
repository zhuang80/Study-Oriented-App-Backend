package com.wequan.bu.service;

import com.wequan.bu.repository.model.TutorStripe;

public interface StripeService extends Service<TutorStripe> {
    public void storeConnectedId(String code, Integer tutorId);
}
