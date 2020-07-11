package com.wequan.bu.service.impl;

import com.wequan.bu.config.WeQuanConstants;
import com.wequan.bu.repository.dao.SchoolMapper;
import com.wequan.bu.repository.dao.SubjectsMapper;
import com.wequan.bu.repository.dao.TagMapper;
import com.wequan.bu.repository.dao.TopicMapper;
import com.wequan.bu.repository.model.School;
import com.wequan.bu.repository.model.Subject;
import com.wequan.bu.repository.model.Tag;
import com.wequan.bu.repository.model.Topic;
import com.wequan.bu.service.CommonDataService;
import com.wequan.bu.util.redis.RedisUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author ChrisChen
 */
@Service
public class CommonDataServiceImpl implements CommonDataService {

    private static final Logger log = LoggerFactory.getLogger(CommonDataServiceImpl.class);
    private static final long CACHE_EXPIRE_TIME = 10 * 60;

    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private SchoolMapper schoolMapper;
    @Autowired
    private TagMapper tagMapper;
    @Autowired
    private SubjectsMapper subjectsMapper;
    @Autowired
    private TopicMapper topicMapper;

    private Lock lock = new ReentrantLock();

    @Override
    public List<School> getSchoolData() {
        List schools = redisUtil.lGet(WeQuanConstants.SCHOOL_CACHE_KEY, 0, -1);
        if (Objects.isNull(schools) || schools.size() == 0) {
            lock.lock();
            try {
                schools = redisUtil.lGet(WeQuanConstants.SCHOOL_CACHE_KEY, 0, -1);
                if (Objects.isNull(schools) || schools.size() == 0) {
                    //query in database
                    schools = schoolMapper.selectAll();
                    redisUtil.lSet(WeQuanConstants.SCHOOL_CACHE_KEY, schools, CACHE_EXPIRE_TIME);
                }
            } catch (Exception e) {
                log.error("getSchoolData()", e);
            } finally {
                lock.unlock();
            }
        }
        return schools;
    }

    @Override
    public List<Tag> getTagData() {
        List tags = redisUtil.lGet(WeQuanConstants.TAG_CACHE_KEY, 0, -1);
        if (Objects.isNull(tags) || tags.size() == 0) {
            lock.lock();
            try {
                tags = redisUtil.lGet(WeQuanConstants.TAG_CACHE_KEY, 0, -1);
                if (Objects.isNull(tags) || tags.size() == 0) {
                    //query in database
                    tags = tagMapper.selectAll();
                    redisUtil.lSet(WeQuanConstants.TAG_CACHE_KEY, tags, CACHE_EXPIRE_TIME);
                }
            } catch (Exception e) {
                log.error("getTagData()", e);
            } finally {
                lock.unlock();
            }
        }
        return tags;
    }

    @Override
    public List<Subject> getSubjectData() {
        List subjects = redisUtil.lGet(WeQuanConstants.SUBJECT_CACHE_KEY, 0, -1);
        if (Objects.isNull(subjects) || subjects.size() == 0) {
            lock.lock();
            try {
                subjects = redisUtil.lGet(WeQuanConstants.SUBJECT_CACHE_KEY, 0, -1);
                if (Objects.isNull(subjects) || subjects.size() == 0) {
                    //query in database
                    subjects = subjectsMapper.selectAll();
                    redisUtil.lSet(WeQuanConstants.SUBJECT_CACHE_KEY, subjects, CACHE_EXPIRE_TIME);
                }
            } catch (Exception e) {
                log.error("getSubjectData()", e);
            } finally {
                lock.unlock();
            }
        }
        return subjects;
    }

    @Override
    public List<Topic> getTopicData() {
        List topics = redisUtil.lGet(WeQuanConstants.TOPIC_CACHE_KEY, 0, -1);
        if (Objects.isNull(topics) || topics.size() == 0) {
            lock.lock();
            try {
                topics = redisUtil.lGet(WeQuanConstants.TOPIC_CACHE_KEY, 0, -1);
                if (Objects.isNull(topics) || topics.size() == 0) {
                    //query in database
                    topics = topicMapper.selectAll();
                    redisUtil.lSet(WeQuanConstants.SUBJECT_CACHE_KEY, topics, CACHE_EXPIRE_TIME);
                }
            } catch (Exception e) {
                log.error("getTopicData()", e);
            } finally {
                lock.unlock();
            }
        }
        return topics;
    }
}
