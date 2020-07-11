package com.wequan.bu.repository.dao;

import com.wequan.bu.repository.model.TutorStripe;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author Zhaochao Huang
 */
@Mapper
public interface TutorStripeMapper extends GeneralMapper<TutorStripe>{
    public TutorStripe selectByTutorId(@Param("tutor_id") Integer tutorId);

    public int deleteByStripeAccount(@Param("stripe_account") String stripeAccount);
}
