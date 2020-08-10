package com.wequan.bu.service.impl;

import com.wequan.bu.repository.dao.FavoriteDiscussionGroupMapper;
import com.wequan.bu.repository.model.FavoriteDiscussionGroup;
import com.wequan.bu.service.AbstractService;
import com.wequan.bu.service.FavoriteDiscussionGroupService;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ChrisChen
 */
@Service
public class FavoriteDiscussionGroupServiceImpl extends AbstractService<FavoriteDiscussionGroup> implements FavoriteDiscussionGroupService {

    private static final Logger log = LoggerFactory.getLogger(FavoriteDiscussionGroupServiceImpl.class);

    @Autowired
    private FavoriteDiscussionGroupMapper favoriteDiscussionGroupMapper;

    @Override
    public List<? extends FavoriteDiscussionGroup> findFavorites(Integer userId, Integer pageNum, Integer pageSize) {
        RowBounds rowBounds = new RowBounds(pageNum, pageSize);
        return favoriteDiscussionGroupMapper.selectByUserId(userId, rowBounds);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void postFavorite(Integer userId, Integer favoriteId, Integer action) {
        if (action == 1) {
            FavoriteDiscussionGroup favoriteDiscussionGroup = new FavoriteDiscussionGroup();
            favoriteDiscussionGroup.setUserId(userId);
            favoriteDiscussionGroup.setDiscussionGroupId(favoriteId);
            favoriteDiscussionGroup.setCreateTime(new Date());
            favoriteDiscussionGroupMapper.insertSelective(favoriteDiscussionGroup);
        }
        if (action == -1) {
            Map<String, Integer> params = new HashMap<>(2);
            params.put("userId", userId);
            params.put("discussionGroupId", favoriteId);
            favoriteDiscussionGroupMapper.deleteByPrimaryKey(params);
        }
    }

    @Override
    public boolean checkFavorite(Integer userId, Integer favoriteId) {
        return favoriteDiscussionGroupMapper.checkFavorite(userId, favoriteId);
    }
}
