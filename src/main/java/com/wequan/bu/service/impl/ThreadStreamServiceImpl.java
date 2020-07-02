package com.wequan.bu.service.impl;

import com.wequan.bu.repository.dao.ThreadStreamMapper;
import com.wequan.bu.repository.model.ThreadStream;
import com.wequan.bu.service.AbstractService;
import com.wequan.bu.service.ThreadStreamService;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * @author ChrisChen
 */
@Service
public class ThreadStreamServiceImpl extends AbstractService<ThreadStream> implements ThreadStreamService {

    private static final Logger log = LoggerFactory.getLogger(ThreadStreamServiceImpl.class);

    @Autowired
    private ThreadStreamMapper threadStreamMapper;

    @PostConstruct
    public void postConstruct(){
        this.setMapper(threadStreamMapper);
    }

    @Override
    public List<ThreadStream> getUserThreadReplies(Integer userId, Integer pageNum, Integer pageSize) {
        RowBounds rowBounds = new RowBounds(pageNum, pageSize);
        return threadStreamMapper.selectByUserId(userId, rowBounds);
    }
}
