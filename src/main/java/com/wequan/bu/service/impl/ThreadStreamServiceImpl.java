package com.wequan.bu.service.impl;

import com.wequan.bu.config.WeQuanResources;
import com.wequan.bu.repository.dao.LikeRecordMapper;
import com.wequan.bu.repository.dao.ThreadStreamMapper;
import com.wequan.bu.repository.model.LikeRecord;
import com.wequan.bu.repository.model.ThreadStream;
import com.wequan.bu.service.AbstractService;
import com.wequan.bu.service.ThreadStreamService;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.List;

/**
 * @author ChrisChen
 */
@Service
public class ThreadStreamServiceImpl extends AbstractService<ThreadStream> implements ThreadStreamService {

    private static final Logger log = LoggerFactory.getLogger(ThreadStreamServiceImpl.class);

    @Autowired
    private ThreadStreamMapper threadStreamMapper;
    @Autowired
    private LikeRecordMapper likeRecordMapper;

    @PostConstruct
    public void postConstruct(){
        this.setMapper(threadStreamMapper);
    }

    @Override
    public List<ThreadStream> getUserReplies(Integer userId, Integer pageNum, Integer pageSize) {
        RowBounds rowBounds = new RowBounds(pageNum, pageSize);
        return threadStreamMapper.selectByUserId(userId, rowBounds);
    }

    @Override
    public List<ThreadStream> getDirectThreadReplies(Integer threadId, Integer pageNum, Integer pageSize) {
        RowBounds rowBounds = new RowBounds(pageNum, pageSize);
        return threadStreamMapper.selectDirectRepliesById(threadId, rowBounds);
    }

    @Override
    public List<ThreadStream> getIndirectThreadReplies(Integer threadId, Integer pageNum, Integer pageSize) {
        RowBounds rowBounds = new RowBounds(pageNum, pageSize);
        return threadStreamMapper.selectIndirectRepliesById(threadId, rowBounds);
    }

    @Override
    public List<ThreadStream> getThreadReplyIndirectReplies(Integer threadId, Integer directReplyId, Integer pageNum, Integer pageSize) {
        RowBounds rowBounds = new RowBounds(pageNum, pageSize);
        return threadStreamMapper.selectIndirectRepliesForDirectReply(threadId, directReplyId, rowBounds);
    }

    @Override
    public int addThreadReply(ThreadStream threadStream) {
        threadStream.setLikes(0);
        threadStream.setDislikes(0);
        threadStream.setCreateTime(new Date());
        threadStream.setUpdateTime(null);
        threadStreamMapper.insertSelective(threadStream);
        return threadStream.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void likeThreadReply(Integer threadId, Integer replyId, Integer userId) {
        Integer replyCreateBy = threadStreamMapper.selectCreateByById(replyId);
        LikeRecord likeRecord = LikeRecord.builder().userId(userId).resourceType((short) WeQuanResources.THREAD_REPLY.getResourceType())
                .resourceId(replyId).resourceBelongUserId(replyCreateBy).createTime(new Date()).build();
        likeRecordMapper.insertSelective(likeRecord);
        threadStreamMapper.likeThreadReply(threadId, replyId, userId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void dislikeThreadReply(Integer threadId, Integer replyId, Integer userId) {
        threadStreamMapper.dislikeThreadReply(threadId, replyId, userId);
    }

    @Override
    public List<ThreadStream> getLabelThreadReplies(Integer label, Integer pageNum, Integer pageSize) {
        RowBounds rowBounds = new RowBounds(pageNum, pageSize);
        return threadStreamMapper.selectLabelThreadReply(label, rowBounds);
    }

    @Override
    public List<ThreadStream> getUserThreadReplies(Integer userId, Integer pageNum, Integer pageSize) {
        RowBounds rowBounds = new RowBounds(pageNum, pageSize);
        return threadStreamMapper.selectThreadReplyByUserId(userId, rowBounds);
    }
}
