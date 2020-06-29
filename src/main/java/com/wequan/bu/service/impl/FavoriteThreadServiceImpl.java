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

import java.util.List;

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
}
