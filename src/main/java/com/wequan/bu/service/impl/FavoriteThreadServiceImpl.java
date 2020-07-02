package com.wequan.bu.service.impl;

import com.wequan.bu.repository.dao.FavoriteThreadMapper;
import com.wequan.bu.repository.model.FavoriteThread;
import com.wequan.bu.repository.model.extend.FavoriteThreadBriefInfo;
import com.wequan.bu.service.AbstractService;
import com.wequan.bu.service.FavoriteThreadService;
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
public class FavoriteThreadServiceImpl extends AbstractService<FavoriteThread> implements FavoriteThreadService {

    private static final Logger log = LoggerFactory.getLogger(FavoriteThreadServiceImpl.class);

    @Autowired
    private FavoriteThreadMapper favoriteThreadMapper;

    @Override
    public List<FavoriteThreadBriefInfo> findFavorites(Integer userId, Integer pageNum, Integer pageSize) {
        RowBounds rowBounds = new RowBounds(pageNum, pageSize);
        return favoriteThreadMapper.selectByUserId(userId, rowBounds);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void postFavorite(Integer userId, Integer favoriteId, Integer action) {
        if (action == 1) {
            FavoriteThread favoriteThread = new FavoriteThread();
            favoriteThread.setUserId(userId);
            favoriteThread.setThreadId(favoriteId);
            favoriteThread.setCreateTime(new Date());
            favoriteThreadMapper.insertSelective(favoriteThread);
        }
        if (action == -1) {
            Map<String, Integer> params = new HashMap<>(2);
            params.put("userId", userId);
            params.put("threadId", favoriteId);
            favoriteThreadMapper.deleteByPrimaryKey(params);
        }
    }
}
