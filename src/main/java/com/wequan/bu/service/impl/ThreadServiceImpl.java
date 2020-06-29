package com.wequan.bu.service.impl;

import com.wequan.bu.repository.dao.ThreadMapper;
import com.wequan.bu.repository.model.Thread;
import com.wequan.bu.repository.model.ThreadStream;
import com.wequan.bu.repository.model.ThreadUserSelectedSubjects;
import com.wequan.bu.repository.model.extend.ThreadStats;
import com.wequan.bu.service.AbstractService;
import com.wequan.bu.service.ThreadService;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Service
public class ThreadServiceImpl extends AbstractService<Thread> implements ThreadService {
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

    /**
     * 6/18
     * @param record
     * @return
     */
    @Override
    public int insert(Thread record){
        if(record!=null){
            return threadMapper.insertThread(record);
        }
        return -1;
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
    public List<Thread> findBySchoolAndTag(Integer schoolId, Integer tagId, Integer pageNum, Integer pageSize){
        if(schoolId!=null && tagId!=null){
            if(pageNum==null){
                pageNum=1;
            }
            if(pageSize==null){
                pageSize=10;
            }
            RowBounds rowBounds = new RowBounds(pageNum,pageSize);
            return threadMapper.selectBySchoolIdAndTag(schoolId, tagId, rowBounds);
        }
        return null;
    }

    /**
     * 6/22
     * @param schoolId
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public List<Thread> findByOtherSchoolId(Integer schoolId, Integer pageNum, Integer pageSize){
        if(schoolId!=null){
            if(pageNum==null){
                pageNum=1;
            }
            if(pageSize==null){
                pageSize=10;
            }
            RowBounds rowBounds = new RowBounds(pageNum, pageSize);
            return threadMapper.selectBySchoolId(schoolId,rowBounds);
        }
        return null;
    }

    /**
     * 6/18
     * @param threadId
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public List<ThreadStream> getDirectThreadReplies(Integer threadId, Integer pageNum, Integer pageSize){
        if(threadId!=null){
            if(pageNum==null){
                pageNum=1;
            }
            if(pageSize==null){
                pageSize=10;
            }
            RowBounds r = new RowBounds(pageNum,pageSize);
            return threadMapper.selectDirectRepliesById(threadId, r);
        }
        return  null;
    }

    /**
     * 6/22
     * @param threadId
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public List<ThreadStream> getIndirectThreadReplies(Integer threadId,Integer pageNum, Integer pageSize){
        if(threadId!=null){
            if(pageNum==null){
                pageNum=2;
            }
            if(pageSize==null){
                pageSize=10;
            }
            RowBounds r = new RowBounds(pageNum,pageSize);
            return threadMapper.selectDirectRepliesById(threadId, r);
        }
        return null;
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
     *6/22
     * @param userId
     * @return
     */
    @Override
    public ThreadUserSelectedSubjects findUsersSelectedSubjects(Integer userId){
        if(userId!=null){
            return threadMapper.selectUserSelectedSubjectsById(userId);
        }
        return null;
    }

    /**
     * 6/22
     * @param userId
     * @param subjectsId
     */
    @Override
    public void addUserSelectedSubjects(Integer userId, String subjectsId){
        if(userId!=null && subjectsId!=null){
            threadMapper.addUserInterestedSubjects(userId, subjectsId);
        }
    }

    /**
     * 6/22
     * @param userId
     */
    @Override
    public void deleteUserSelectedSubjects(Integer userId){
        if(userId!=null){
            threadMapper.deleteUserSelectedSubjectsById(userId);
        }
    }

    /**
     * 6/22
     * @param userId
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public List<Thread> findByUserFollowingId(Integer userId, Integer pageNum, Integer pageSize){
        if(userId!=null){
            if(pageNum==null){
                pageNum=1;
            }
            if(pageSize==null){
                pageSize=10;
            }
            RowBounds rowBounds = new RowBounds(pageNum,pageSize);
            return threadMapper.selectByUserFollowingId(userId, rowBounds);
        }
        return null;
    }

    /**
     * 6/23
     * @param schoolId
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public List<Thread> findBySchoolIdOrderByView(Integer schoolId, Integer pageNum, Integer pageSize){
        if(schoolId!=null){
            if(pageNum==null){
                pageNum=1;
            }
            if(pageSize==null){
                pageSize=10;
            }
            RowBounds rowBounds = new RowBounds(pageNum,pageSize);
            return threadMapper.selectedBySchoolIdOrderedByView(schoolId, rowBounds);
        }
        return null;
    }

    /**
     * 6/23
     * @param threadId
     * @param userId
     * @param reason
     */
    @Override
    public void reportThread(Integer threadId, Integer userId, String reason){
        if(threadId!=null && userId!=null && reason!=null){
            Date reportDate = new Date(new java.util.Date().getTime());
            threadMapper.reportThread(threadId, userId, reason, reportDate);
        }
    }

    /**
     * 6/23
     * @param threadId
     * @param replyId
     * @param userId
     * @param reason
     */
    @Override
    public void reportReplyToThread(Integer threadId, Integer replyId, Integer userId, String reason){
        if(threadId!=null && replyId!=null && userId!=null && reason!=null){
            Date reportDate = new Date(new Date().getTime());
            threadMapper.reportReplyToThread(threadId, replyId, userId, reason, reportDate);
        }
    }

    /**
     * 6/26
     * @param userId
     * @param subjectIds
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public List<Thread> getUserInterestedStudyHelpThreads(Integer userId, String subjectIds, Integer pageNum, Integer pageSize){
        if(userId!=null && subjectIds !=null){
            if(pageNum==null){
                pageNum=1;
            }
            if(pageSize==null){
                pageSize=10;
            }
            RowBounds rowBounds = new RowBounds(pageNum,pageSize);
            try{
                List<String> result = Arrays.asList(subjectIds.split(","));
                List<Integer> res = new LinkedList<Integer>();
                for(String subjectId : result){
                    res.add(Integer.valueOf(subjectId));
                }
                return threadMapper.getUserInterestedStudyHelpThreadsByIds(userId, res, rowBounds);
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public List<ThreadStats> getUserThreads(Integer userId, Integer pageNum, Integer pageSize) {
        RowBounds rowBounds = new RowBounds(pageNum, pageSize);
        return threadMapper.selectByUserId(userId, rowBounds);
    }
}
