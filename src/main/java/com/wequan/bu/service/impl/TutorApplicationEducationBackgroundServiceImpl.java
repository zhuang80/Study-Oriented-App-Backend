package com.wequan.bu.service.impl;

import com.wequan.bu.repository.dao.TutorApplicationEducationBackgroundMapper;
import com.wequan.bu.repository.model.TutorApplicationEducationBackground;
import com.wequan.bu.service.AbstractService;
import com.wequan.bu.service.TutorApplicationEducationBackgroundService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;

/**
 * @author Zhaochao Huang
 */
@Service
public class TutorApplicationEducationBackgroundServiceImpl extends AbstractService<TutorApplicationEducationBackground>
        implements TutorApplicationEducationBackgroundService {

    @Autowired
    private TutorApplicationEducationBackgroundMapper educationBackgroundMapper;

    @PostConstruct
    private void postConstruct(){
        this.setMapper(educationBackgroundMapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteByIds(String ids){
        if(ids != null && !ids.isEmpty()){
            educationBackgroundMapper.deleteByIds(ids);
        }
    }
}
