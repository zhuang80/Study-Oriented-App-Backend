package com.wequan.bu.service.impl;

import com.wequan.bu.config.WeQuanResources;
import com.wequan.bu.controller.vo.ThreadVo;
import com.wequan.bu.repository.dao.LikeRecordMapper;
import com.wequan.bu.repository.dao.ReportRecordMapper;
import com.wequan.bu.repository.dao.ThreadMapper;
import com.wequan.bu.repository.model.LikeRecord;
import com.wequan.bu.repository.model.ReportRecord;
import com.wequan.bu.repository.model.Thread;
import com.wequan.bu.repository.model.extend.ThreadStats;
import com.wequan.bu.service.AbstractService;
import com.wequan.bu.service.ThreadService;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author ChrisChen
 */
@Service
public class ThreadServiceImpl extends AbstractService<Thread> implements ThreadService {

    private static final Logger log = LoggerFactory.getLogger(ThreadServiceImpl.class);

    @Autowired
    private ThreadMapper threadMapper;
    @Autowired
    private ReportRecordMapper reportRecordMapper;
    @Autowired
    private LikeRecordMapper likeRecordMapper;

    @PostConstruct
    public void postConstruct(){
        this.setMapper(threadMapper);
    }

    @Override
    public Thread findByPrimaryKey(Integer id) {
        return threadMapper.selectByPrimaryKey(id);
    }

    @Override
    public int addThread(ThreadVo record) {
        Thread thread = new Thread();
        BeanUtils.copyProperties(record, thread);
        thread.setStatus(0);
        thread.setDislikes(0);
        thread.setLikes(0);
        thread.setCreateTime(new Date());
        threadMapper.insertSelective(thread);
        return thread.getId();
    }

    @Override
    public List<Thread> findBySchoolAndTag(Integer schoolId, Integer tagId, Integer pageNum, Integer pageSize){
        RowBounds rowBounds = new RowBounds(pageNum,pageSize);
        return threadMapper.selectBySchoolIdAndTag(schoolId, tagId, rowBounds);
    }

    @Override
    public List<Thread> findByOtherSchoolId(Integer schoolId, Integer pageNum, Integer pageSize) {
        RowBounds rowBounds = new RowBounds(pageNum, pageSize);
        return threadMapper.selectBySchoolId(schoolId, rowBounds);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void likeThread(Integer threadId, Integer userId) {
        Integer threadCreateBy = threadMapper.selectCreateByById(threadId);
        LikeRecord likeRecord = LikeRecord.builder().userId(userId).resourceType((short)WeQuanResources.THREAD.getResourceType())
                .resourceId(threadId).resourceBelongUserId(threadCreateBy).createTime(new Date()).build();
        likeRecordMapper.insertSelective(likeRecord);
        threadMapper.likeThread(threadId, userId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void dislikeThread(Integer threadId, Integer userId) {
        threadMapper.dislikeThread(threadId, userId);
    }

    @Override
    public String findUserSelectedSubjects(Integer userId) {
        return threadMapper.selectSubjectIdsUserSelected(userId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addUserSelectedSubjects(Integer userId, String subjectIds){
        threadMapper.insertUserSelectedSubjects(userId, subjectIds);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteUserSelectedSubjects(Integer userId){
        threadMapper.deleteUserSelectedSubjects(userId);
    }

    @Override
    public List<Thread> getUserFollowingThreads(Integer userId, Integer pageNum, Integer pageSize){
        RowBounds rowBounds = new RowBounds(pageNum, pageSize);
        return threadMapper.selectByUserFollowing(userId, rowBounds);
    }

    @Override
    public List<Thread> findBySchoolIdOrderByView(Integer schoolId, Integer pageNum, Integer pageSize) {
        RowBounds rowBounds = new RowBounds(pageNum,pageSize);
        return threadMapper.selectBySchoolIdOrderedByView(schoolId, rowBounds);
    }

    @Override
    public void reportThread(Integer threadId, Integer userId, String reason) {
        ReportRecord reportRecord = ReportRecord.builder().userId(userId).resourceType((short) 1)
                .resourceId(threadId).reason(reason).reportTime(new Date()).build();
        reportRecordMapper.insertSelective(reportRecord);
    }

    @Override
    public void reportThreadReply(Integer threadId, Integer replyId, Integer userId, String reason) {
        ReportRecord reportRecord = ReportRecord.builder().userId(userId).resourceType((short) 2)
                .resourceId(replyId).reason(reason).reportTime(new Date()).build();
        reportRecordMapper.insertSelective(reportRecord);
    }

    @Override
    public List<Thread> getUserInterestedStudyHelpThreads(Integer userId, String subjectIds, Integer pageNum, Integer pageSize) {
        RowBounds rowBounds = new RowBounds(pageNum, pageSize);
        String[] subjectIdArr = StringUtils.remove(subjectIds, " ").split(",");
        List<Integer> sIds = Stream.of(subjectIdArr).map(Integer::parseInt).collect(Collectors.toList());
        return threadMapper.selectUserInterestedStudyHelpThreads(userId, sIds, rowBounds);
    }

    @Override
    public List<ThreadStats> getUserThreads(Integer userId, Integer pageNum, Integer pageSize) {
        RowBounds rowBounds = new RowBounds(pageNum, pageSize);
        return threadMapper.selectByUserId(userId, rowBounds);
    }

    @Override
    public void addThreadSubjects(int threadId, List<Integer> subjectIds) {
        threadMapper.insertSubjectIds(threadId, subjectIds, new Date());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addViewRecord(int userId, int threadId, Date viewTime) {
        threadMapper.insertViewHistory(userId, threadId, viewTime);
    }

    @Override
    public List<Thread> getLabelThreads(Integer label, Integer pageNum, Integer pageSize) {
        RowBounds rowBounds = new RowBounds(pageNum, pageSize);
        return threadMapper.selectLabelThreads(label, rowBounds);
    }

    @Override
    public List<Thread> search(String whereCondition, String orderCondition, Map<String, Integer> pageCondition) {
        List<Thread> threads = null;
        threads = threadMapper.selectByConditions(whereCondition, orderCondition,
                new RowBounds(pageCondition.get("pageNo"), pageCondition.get("pageSize")));
        return threads;
    }

}
