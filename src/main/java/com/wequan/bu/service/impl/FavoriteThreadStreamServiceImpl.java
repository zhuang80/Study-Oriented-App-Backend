package com.wequan.bu.service.impl;

import com.wequan.bu.repository.dao.FavoriteThreadStreamMapper;
import com.wequan.bu.repository.model.FavoriteThreadStream;
import com.wequan.bu.repository.model.extend.FavoriteThreadStreamBriefInfo;
import com.wequan.bu.service.AbstractService;
import com.wequan.bu.service.FavoriteThreadStreamService;
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
public class FavoriteThreadStreamServiceImpl extends AbstractService<FavoriteThreadStream> implements FavoriteThreadStreamService {

    private static final Logger log = LoggerFactory.getLogger(FavoriteThreadStreamServiceImpl.class);

    @Autowired
    private FavoriteThreadStreamMapper favoriteThreadStreamMapper;

    @Override
    public List<FavoriteThreadStreamBriefInfo> findFavorites(Integer userId, Integer pageNum, Integer pageSize) {
        RowBounds rowBounds = new RowBounds(pageNum, pageSize);
        return favoriteThreadStreamMapper.selectByUserId(userId, rowBounds);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void postFavorite(Integer userId, Integer favoriteId, Integer action) {
        if (action == 1) {
            FavoriteThreadStream favoriteThreadStream = new FavoriteThreadStream();
            favoriteThreadStream.setUserId(userId);
            favoriteThreadStream.setThreadStreamId(favoriteId);
            favoriteThreadStream.setCreateTime(new Date());
            favoriteThreadStreamMapper.insertSelective(favoriteThreadStream);
        }
        if (action == -1) {
            Map<String, Integer> params = new HashMap<>(2);
            params.put("userId", userId);
            params.put("threadStreamId", favoriteId);
            favoriteThreadStreamMapper.deleteByPrimaryKey(params);
        }
    }

    @Override
    public boolean checkFavorite(Integer userId, Integer favoriteId) {
        return favoriteThreadStreamMapper.checkFavorite(userId, favoriteId);
    }
}
