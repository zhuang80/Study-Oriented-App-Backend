package com.wequan.bu.service.impl;

import com.wequan.bu.repository.dao.ThreadMapper;
import com.wequan.bu.repository.model.Thread;
import com.wequan.bu.repository.model.ThreadStream;
import com.wequan.bu.repository.model.ThreadUserSelectedSubjects;
import com.wequan.bu.service.AbstractService;
import com.wequan.bu.service.ThreadService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.List;

public class ThreadSeriviceImpl extends AbstractService<Thread> implements ThreadService {
    @Autowired
    private ThreadMapper threadMapper;

    @PostConstruct
    public void postConstruct(){
        this.setMapper(threadMapper);
    }
    @Override
    public Thread findByPrimaryKey(Integer id){
        if(id!=null){
            return threadMapper.selectByPrimaryKey(id);
        }
        return null;
    }
    @Override
    public int deleteByPrimaryKey(Integer id){
        if(id!=null){
            return threadMapper.deleteByPrimaryKey(id);
        }
        return -1;
    }
    @Override
    public int insert(Thread record){
        if(record!=null){
            return threadMapper.insert(record);
        }
        return -1;
    }

    @Override
    public Integer numberOfLikesOfThread(Thread thread){
        if(thread!=null){

        }
        return null;
    }

    /**
     * 6/20
     * @param threadStream
     * @return
     */
    @Override
    public int insertReply(ThreadStream threadStream){
        if(threadStream!=null){
            return threadMapper.insertReply(threadStream);
        }
        return -1;
    }
    @Override
    public int insertSelective(Thread record){
        if(record!=null){
            return threadMapper.insertSelective(record);
        }
        return -1;
    }
    @Override
    public void updateByIdSelective(Thread record){
        if(record!=null){
            threadMapper.updateByPrimaryKeySelective(record);
        }
    }

    @Override
    public void updateByKey(Thread record){
        if(record!=null){
            threadMapper.updateByPrimaryKey(record);
        }
    }

    /**
     * 6/20
     * @param threadStream
     * @return
     */
    @Override
    public int replyToThread(ThreadStream threadStream){
        if(threadStream!=null){
            return threadMapper.insertReply(threadStream);
        }
        return -1;
    }

    /**
     * 6/18
     * @param schoolId
     * @param tagId
     * @return
     */
    @Override
    public List<Thread> findBySchoolAndTag(Integer schoolId, Integer tagId){
        if(schoolId!=null && tagId!=null){
            return threadMapper.selectBySchoolIdAndTag(schoolId, tagId);
        }
        return null;
    }

    @Override
    public List<ThreadStream> getThreadReplies(Integer threadId){
        if(threadId!=null){
            return threadMapper.selectRepliesById(threadId);
        }
        return  null;
    }

    /**
     * 6/19
     * @param threadId
     * @param userId
     */
    @Override
    public void likeThread(Integer threadId, Integer userId){
        if(threadId!=null && userId!=null){
            threadMapper.likeThread(threadId,userId);
        }
    }

    /**
     * 6/19
     * @param threadId
     * @param userId
     */
    @Override
    public void dislikeThread(Integer threadId, Integer userId){
        if(threadId!=null && userId!=null){
            threadMapper.dislikeThread(threadId,userId);
        }
    }

    /**
     * 6/20
     * @param threadId
     * @param replyId
     * @param userId
     */
    @Override
    public void likeReplyOfThread(Integer threadId, Integer replyId, Integer userId){
        if(threadId!=null && replyId!=null && userId!= null){
            threadMapper.likeReplyOfThread(threadId,replyId,userId);
        }
    }

    /**
     * 6/20
     * @param threadId
     * @param replyId
     * @param userId
     */
    @Override
    public void dislikeReplyOfThread(Integer threadId, Integer replyId, Integer userId){
        if(threadId!=null && replyId!=null && userId!= null){
            threadMapper.dislikeReplyOfThread(threadId,replyId,userId);
        }
    }

    /**
     *
     * @param userId
     * @return
     */
    @Override
    public ThreadUserSelectedSubjects findUsersSubjects(Integer userId){
        return null;
    }

}
