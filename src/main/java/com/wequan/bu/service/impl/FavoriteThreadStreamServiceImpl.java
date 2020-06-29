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

import java.util.List;

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
}
