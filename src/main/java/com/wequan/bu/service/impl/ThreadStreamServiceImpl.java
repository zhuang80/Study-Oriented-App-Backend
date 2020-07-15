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
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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
        List<ThreadStream> threadStreams = threadStreamMapper.selectByUserId(userId, rowBounds);
        return threadStreams.stream().sorted(Comparator.comparing(ThreadStream::getCreateTime).reversed()).collect(Collectors.toList());
    }

    @Override
    public List<ThreadStream> getDirectThreadReplies(Integer threadId, Integer pageNum, Integer pageSize) {
        RowBounds rowBounds = new RowBounds(pageNum, pageSize);
        List<ThreadStream> threadStreams = threadStreamMapper.selectDirectRepliesById(threadId, rowBounds);
        return threadStreams.stream().sorted(Comparator.comparing(ThreadStream::getCreateTime).reversed()).collect(Collectors.toList());
    }

    @Override
    public List<ThreadStream> getIndirectThreadReplies(Integer threadId, Integer pageNum, Integer pageSize) {
        RowBounds rowBounds = new RowBounds(pageNum, pageSize);
        List<ThreadStream> threadStreams = threadStreamMapper.selectIndirectRepliesById(threadId, rowBounds);
        return threadStreams.stream().sorted(Comparator.comparing(ThreadStream::getCreateTime).reversed()).collect(Collectors.toList());
    }

    @Override
    public List<ThreadStream> getThreadReplyIndirectReplies(Integer threadId, Integer directReplyId, Integer pageNum, Integer pageSize) {
        RowBounds rowBounds = new RowBounds(pageNum, pageSize);
        List<ThreadStream> threadStreams = threadStreamMapper.selectIndirectRepliesForDirectReply(threadId, directReplyId, rowBounds);
        return threadStreams.stream().sorted(Comparator.comparing(ThreadStream::getCreateTime).reversed()).collect(Collectors.toList());
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
        threadStreamMapper.likeThreadReply(threadId, replyId, userId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void dislikeThreadReply(Integer threadId, Integer replyId, Integer userId) {
        threadStreamMapper.dislikeThreadReply(threadId, replyId, userId);
    }
}
