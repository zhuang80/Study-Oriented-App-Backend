package com.wequan.bu.service.impl;

import com.wequan.bu.repository.dao.DiscussionGroupMapper;
import com.wequan.bu.repository.model.DiscussionGroup;
import com.wequan.bu.repository.model.DiscussionGroupMember;
import com.wequan.bu.service.AbstractService;
import com.wequan.bu.service.DiscussionGroupService;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author ChrisChen
 */
@Service
public class DiscussionGroupServiceImpl extends AbstractService<DiscussionGroup> implements DiscussionGroupService {

    private static final Logger log = LoggerFactory.getLogger(DiscussionGroupServiceImpl.class);

    @Autowired
    private DiscussionGroupMapper discussionGroupMapper;

    @PostConstruct
    public void postConstruct(){
        this.setMapper(discussionGroupMapper);
    }

    @Override
    public List<DiscussionGroup> getUserDiscussionGroups(Integer userId, Integer pageNum, Integer pageSize) {
        RowBounds rowBounds = new RowBounds(pageNum, pageSize);
        return discussionGroupMapper.selectByUserId(userId, rowBounds);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void doUserAction(Integer userId, Integer dgId, String action) {
        DiscussionGroupMember discussionGroupMember = new DiscussionGroupMember();
        discussionGroupMember.setDiscussionGroupId(dgId);
        discussionGroupMember.setMemberId(userId);
        discussionGroupMember.setAction(action);
        discussionGroupMember.setActionTime(new Date());
        discussionGroupMapper.insertOrUpdateActionByUserId(discussionGroupMember);
    }

    @Override
    public List<DiscussionGroup> search(String whereCondition, String orderCondition, Map<String, Integer> pageCondition) {
        List<DiscussionGroup> discussionGroups = null;
        discussionGroups = discussionGroupMapper.selectByConditions(whereCondition, orderCondition,
                new RowBounds(pageCondition.get("pageNo"), pageCondition.get("pageSize")));
        return discussionGroups;
    }
}
