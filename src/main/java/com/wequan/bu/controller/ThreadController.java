package com.wequan.bu.controller;

import com.wequan.bu.repository.model.Thread;
import com.wequan.bu.controller.vo.result.Result;
import com.wequan.bu.controller.vo.result.ResultGenerator;
import com.wequan.bu.repository.model.ThreadStream;
import com.wequan.bu.repository.model.ThreadUserSelectedSubjects;
import com.wequan.bu.service.ThreadService;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author ChrisChen
 */
@RestController
@Api(tags = "Thread")
public class ThreadController {

    private static final Logger log = LoggerFactory.getLogger(ThreadController.class);

    private ThreadService threadService;

    /**
     * 6/23
     * @param schoolId
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping("/thread/myschool/top")
    @ApiOperation(value = "a list of top thread for user's school", notes = "根据school id获取thread列表，按查看次数排序")
    @ApiResponses(
            @ApiResponse(code = 200, message = "a list of thread brief info (title, # of views, # of replies, thread id)")
    )
    public Result<List<Thread>> getMySchoolTopThreads(@RequestParam("schoolId") Integer schoolId,
                                                      @RequestParam(value = "pageNum", required = false) Integer pageNum,
                                                      @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        List<Thread> result = threadService.findBySchoolIdOrderByView(schoolId, pageNum, pageSize);
        return ResultGenerator.success(result);
    }

    /**
     * 6/18
     * @param schoolId
     * @param tagId
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping("/thread/myschool")
    @ApiOperation(value = "a list of thread for user's school and selected tag", notes = "根据school id, tag id获取thread列表，按创建时间排序")
    @ApiResponses(
            @ApiResponse(code = 200, message = "a list of thread card (title, thread id, user name, user id, tag, created time, " +
                    "first 100 words, first 3 photos, # of likes, # of dislikes, # of views,\n" +
                    "school id, study points reward, status) sorted by created time")
    )
    public Result<List<Thread>> getMySchoolThreadsWithTag(@RequestParam("schoolId") Integer schoolId,
                                                          @RequestParam("tagId") Integer tagId,
                                                          @RequestParam(value = "pageNum", required = false) Integer pageNum,
                                                          @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        List<Thread> result = threadService.findBySchoolAndTag(schoolId,tagId, pageNum, pageSize);
        return ResultGenerator.success(result);
    }

    /**
     * 6/18
     * @param threadId
     * @return
     */
    @GetMapping("/thread/{id}")
    @ApiOperation(value = "thread detail", notes = "返回帖子详情")
    @ApiResponses(
            @ApiResponse(code = 200, message = "title, created time, category, tag, user name,\n avatar, text body, pictures/doc, # of like, " +
                    "# of dislikes, # of replies, # of views, status, study points reward")
    )
    public Result<Thread> getThread(@PathVariable("id") Integer threadId) {
        Thread result = threadService.findByPrimaryKey(threadId);
        return ResultGenerator.success(result);
    }

    /**
     * 6/22
     * @param threadId
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping("/thread/{id}/direct_replies")
    @ApiOperation(value = "a list of direct replies to the thread", notes = "返回第一层（直接回复帖子的）回帖列表")
    @ApiResponses(
            @ApiResponse(code = 200, message = "a list of replies (user name, created time, avatar, # of likes, # of dislikes, text body,\n" +
                    "pictures/doc, # of replies, if reward)")
    )
    public Result<List<ThreadStream>> getThreadDirectReplies(@PathVariable("id") Integer threadId,
                                                             @RequestParam(value = "pageNum", required = false) Integer pageNum,
                                                             @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        List<ThreadStream> result = threadService.getDirectThreadReplies(threadId, pageNum, pageSize);
        return ResultGenerator.success(result);
    }

    /**
     * 6/22
     * @param threadId
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping("/thread/{id}/indirect_replies")
    @ApiOperation(value = "a list of indirect replies to the thread", notes = "返回第二层（间接回复帖子的）回帖列表")
    @ApiResponses(
            @ApiResponse(code = 200, message = "a list of replies (user name, created time,\n avatar, # of likes, # of dislikes, text body, pictures/doc, # of replies)")
    )
    public Result<List<ThreadStream>> getThreadIndirectReplies(@PathVariable("id") Integer threadId,
                                                               @RequestParam(value = "pageNum", required = false) Integer pageNum,
                                                               @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        List<ThreadStream> result = threadService.getIndirectThreadReplies(threadId, pageNum,pageSize);;
        return ResultGenerator.success(result);
    }

    /**
     * 6/19
     * @param thread
     * @return
     */
    @PostMapping("/thread")
    @ApiOperation(value = "add thread", notes = "返回创建帖子成功与否")
    public Result addThread(@RequestBody Thread thread) {
        if(threadService.insert(thread)>0){
            return ResultGenerator.success();
        }
        return null;
    }

    /**
     * 6/22
     * @param threadStream
     * @return
     */
    @PostMapping("/thread/reply")
    @ApiOperation(value = "reply to the thread", notes = "包括直接/间接回复，返回回帖成功与否")
    public Result addThreadReply(@RequestBody ThreadStream threadStream) {
        int result = threadService.insertReply(threadStream);
        if(result>0){
            return ResultGenerator.success(result);
        }
        return null;
    }

    /**
     * 6/23
     * @param threadId
     * @param userId
     * @param reason
     * @return
     */
    @PostMapping("/thread/report")
    @ApiOperation(value = "report thread", notes = "对帖子进行举报")
    public Result reportThread(@RequestParam("threadId") Integer threadId,
                               @RequestParam("userId") Integer userId,
                               @RequestParam("reason") String reason) {
        threadService.reportThread(threadId, userId, reason);
        Result result = ResultGenerator.success();
        return result;
    }

    @PostMapping("/thread/reply/report")
    @ApiOperation(value = "report the reply to thread", notes = "对帖子回复进行举报")
    public Result reportProfessorReview(@RequestParam("threadId") Integer threadId,
                                        @RequestParam("replyId") Integer replyId,
                                        @RequestParam("userId") Integer userId,
                                        @RequestParam("reason") String reason) {
        Result result = ResultGenerator.success();
        return result;
    }

    /**
     * 6/19
     * @param threadId
     * @param userId
     * @return
     */
    @PostMapping("/thread/like")
    @ApiOperation(value = "like thread", notes = "对帖子点赞")
    public Result likeThread(@RequestParam("threadId") Integer threadId,
                             @RequestParam("userId") Integer userId) {
        threadService.likeThread(threadId,userId);
        Result result = ResultGenerator.success();
        return result;
    }

    /**
     * 6/19
     * @param threadId
     * @param userId
     * @return
     */
    @PostMapping("/thread/dislike")
    @ApiOperation(value = "dislike thread", notes = "对帖子拍砖")
    public Result dislikeThread(@RequestParam("threadId") Integer threadId,
                                @RequestParam("userId") Integer userId) {
        threadService.dislikeThread(threadId,userId);
        Result result = ResultGenerator.success();
        return result;
    }

    /**
     * 6/20
     * @param threadId
     * @param replyId
     * @param userId
     * @return
     */
    @PostMapping("/thread/reply/like")
    @ApiOperation(value = "like the reply of thread", notes = "对帖子回复点赞")
    public Result likeThreadReply(@RequestParam("threadId") Integer threadId,
                                  @RequestParam("replyId") Integer replyId,
                                  @RequestParam("userId") Integer userId) {
        threadService.likeReplyOfThread(threadId,replyId,userId);
        Result result = ResultGenerator.success();
        return result;
    }

    /**
     * 6/19
     * @param threadId
     * @param replyId
     * @param userId
     * @return
     */
    @PostMapping("/thread/reply/dislike")
    @ApiOperation(value = "dislike the reply of thread", notes = "对帖子回复拍砖")
    public Result dislikeThreadReply(@RequestParam("threadId") Integer threadId,
                                     @RequestParam("replyId") Integer replyId,
                                     @RequestParam("userId") Integer userId) {
        threadService.dislikeReplyOfThread(threadId,replyId,userId);
        Result result = ResultGenerator.success();
        return result;
    }

    /**
     * 6/22
     * @param userId
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping("/thread/following")
    @ApiOperation(value = "a list of thread for user's following users", notes = "根据用户关注的人，获取thread列表，按创建时间排序")
    @ApiResponses(
            @ApiResponse(code = 200, message = "a list of thread card (title, thread id, user name, user id, tag, created time, first 100 words, \n" +
                    "first 3 photos, # of likes, # of dislikes, # of views, school id, study points reward, status) sorted by created time")
    )
    public Result<List<Thread>> getFollowingThreads(@RequestParam("userId") Integer userId,
                                                    @RequestParam(value = "pageNum", required = false) Integer pageNum,
                                                    @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        List<Thread> result = threadService.findByUserFollowingId(userId, pageNum, pageSize);
        return ResultGenerator.success(result);
    }

    /**
     * 6/22
     * @param schoolId
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping("/thread/explore")
    @ApiOperation(value = "a list of thread for other school", notes = "根据school id获取thread列表，按创建时间排序")
    @ApiResponses(
            @ApiResponse(code = 200, message = "a list of thread card (title, thread id, user name, user id, tag, created time, first 100 words, \n" +
                    "first 3 photos, # of likes, # of dislikes, # of views, school id, study points reward, status) sorted by created time")
    )
    public Result<List<Thread>> getOtherSchoolThreads(@RequestParam("schoolId") Integer schoolId,
                                                      @RequestParam(value = "pageNum", required = false) Integer pageNum,
                                                      @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        List<Thread> result = threadService.findByOtherSchoolId(schoolId, pageNum, pageSize);
        return ResultGenerator.success(result);
    }

    /**
     * 6/22
     * @param userId
     * @return
     */
    @GetMapping("/thread/study_help/subjects")
    @ApiOperation(value = "a list of user interested subjects", notes = "根据user id获取之前感兴趣的科目列表")
    public Result<ThreadUserSelectedSubjects> getUserSelectedSubjects(@RequestParam("userId") Integer userId) {

        ThreadUserSelectedSubjects result = threadService.findUsersSelectedSubjects(userId);
        return ResultGenerator.success(result);
    }

    /**
     * 6/22
     * @param userId
     * @param subjectIds
     * @return
     */
    @PutMapping("/thread/study_help/subject")
    @ApiOperation(value = "update user interested subjects", notes = "根据user id更新感兴趣的科目列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "subjectIds", value = "多个subject id用逗号分隔", required = true)
    })
    public Result addUserSelectedSubjects(@RequestParam("userId") Integer userId,
                                          @RequestParam("subjectIds") String subjectIds) {
        threadService.addUserSelectedSubjects(userId, subjectIds);
        return ResultGenerator.success();
    }

    /**
     * 6/22
     * @param userId
     * @return
     */
    @DeleteMapping("/thread/study_help/subject")
    @ApiOperation(value = "delete user interested subjects", notes = "根据user id删除感兴趣的科目列表")
    public Result deleteUserSelectedSubjects(@RequestParam("userId") Integer userId) {
        threadService.deleteUserSelectedSubjects(userId);
        return ResultGenerator.success();
    }

    @GetMapping("/thread/study_help")
    @ApiOperation(value = "a list of threads according to user interested subjects", notes = "根据用户选择的subject ids，获取帖子列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "subjectIds", value = "多个subject id用逗号分隔", required = true)
    })
    @ApiResponses(
            @ApiResponse(code = 200, message = "a list of thread card (title, thread id, user name, user id, tag, created time, first 100 words, \n" +
                    "first 3 photos, # of likes, # of dislikes, # of views, school id, study points reward, status) sorted by created time")
    )
    public Result<List<Thread>> getUserInterestedStudyHelpThreads(@RequestParam("userId") Integer userId,
                                                                  @RequestParam("subjectIds") String subjectIds,
                                                                  @RequestParam(value = "pageNum", required = false) Integer pageNum,
                                                                  @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        List<Thread> result = null;
        return ResultGenerator.success(result);
    }

    @GetMapping("/thread/{id}/sse/likes")
    @ApiOperation(value = "# of likes for thread in real time", notes = "实时显示帖子likes数")
    public SseEmitter sseEmitterForThreadLikes(@PathVariable("id") Integer threadId) {
        SseEmitter emitter = new SseEmitter();
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            try {
                for (int i = 0; true; i++) {
                    SseEmitter.SseEventBuilder event = SseEmitter.event()
                            .data(setData(threadId))
                            .id(String.valueOf(i))
                            .name("sse event - mvc");
                    emitter.send(event);
                    java.lang.Thread.sleep(1000);
                }
            } catch (Exception ex) {
                emitter.completeWithError(ex);
            }
        });
        return emitter;
    }

    private Object setData(Integer threadId) {
        Map<Integer, Integer> sseThreadLikes = new ConcurrentHashMap<>();
        return sseThreadLikes.get(threadId);
    }

}
