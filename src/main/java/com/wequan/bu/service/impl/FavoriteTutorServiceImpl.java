package com.wequan.bu.service.impl;

import com.wequan.bu.repository.dao.FavoriteTutorMapper;
import com.wequan.bu.repository.model.FavoriteTutor;
import com.wequan.bu.repository.model.extend.FavoriteTutorBriefInfo;
import com.wequan.bu.service.AbstractService;
import com.wequan.bu.service.FavoriteTutorService;
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
public class FavoriteTutorServiceImpl extends AbstractService<FavoriteTutor> implements FavoriteTutorService {

    private static final Logger log = LoggerFactory.getLogger(FavoriteTutorServiceImpl.class);

    @Autowired
    private FavoriteTutorMapper favoriteTutorMapper;

    @Override
    public List<FavoriteTutorBriefInfo> findFavorites(Integer userId, Integer pageNum, Integer pageSize) {
        RowBounds rowBounds = new RowBounds(pageNum, pageSize);
        return favoriteTutorMapper.selectByUserId(userId, rowBounds);
    }
}
