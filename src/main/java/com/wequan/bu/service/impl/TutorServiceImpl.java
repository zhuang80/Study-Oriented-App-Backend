package com.wequan.bu.service.impl;

import com.wequan.bu.repository.dao.TutorMapper;
import com.wequan.bu.repository.model.Tutor;
import com.wequan.bu.service.AbstractService;
import com.wequan.bu.service.TutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * @author ChrisChen
 */
@Service
public class TutorServiceImpl extends AbstractService<Tutor> implements TutorService {

    @Autowired
    private TutorMapper tutorMapper;

    @PostConstruct
    public void postConstruct() {
        this.setMapper(tutorMapper);
    }

    @Override
    public List<Tutor> findTutors(Integer subjectId) {
        return tutorMapper.selectTutors(subjectId);
    }

}
