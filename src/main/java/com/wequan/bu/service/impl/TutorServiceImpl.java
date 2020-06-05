package com.wequan.bu.service.impl;

import com.wequan.bu.repository.dao.TutorMapper;
import com.wequan.bu.repository.model.Tutor;
import com.wequan.bu.service.AbstractService;
import com.wequan.bu.service.TutorService;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;

/**
 * @author ChrisChen
 */
@Service
public class TutorServiceImpl extends AbstractService<Tutor> implements TutorService {

    private static final Logger logger = LoggerFactory.getLogger(TutorServiceImpl.class);

    @Autowired
    private TutorMapper tutorMapper;

    @PostConstruct
    public void postConstruct() {
        this.setMapper(tutorMapper);
    }

    @Override
    public List<Tutor> search(String whereCondition, String groupCondition, String orderCondition, Map<String, Integer> pageCondition) {
        List<Tutor> tutors = null;
        if (StringUtils.isBlank(groupCondition)) {
            tutors = tutorMapper.selectByConditions(whereCondition, orderCondition,
                    new RowBounds(pageCondition.get("pageNo"), pageCondition.get("pageSize")));
        } else {
            String[] columns = groupCondition.split(",");
            // to do
        }
        return tutors;
    }
}
