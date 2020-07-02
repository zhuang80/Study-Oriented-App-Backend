package com.wequan.bu.service.impl;

import com.wequan.bu.repository.dao.OnlineEventMapper;
import com.wequan.bu.repository.model.OnlineEvent;
import com.wequan.bu.service.AbstractService;
import com.wequan.bu.service.OnlineEventService;
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
public class OnlineEventServiceImpl extends AbstractService<OnlineEvent> implements OnlineEventService {

    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private OnlineEventMapper onlineEventMapper;

    @PostConstruct
    public void postConstruct() {
        this.setMapper(onlineEventMapper);
    }

    @Override
    public List<OnlineEvent> getUserOnlineEvents(Integer userId, Integer pageNum, Integer pageSize, Integer... type) {
        Integer typeId = null;
        if (type != null && type.length > 0) {
            typeId = type[0];
        }
        RowBounds rowBounds = new RowBounds(pageNum, pageSize);
        return onlineEventMapper.selectByUserIdAndType(userId, typeId, rowBounds);
    }
}
