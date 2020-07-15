package com.wequan.bu.controller;

import com.wequan.bu.config.handler.MessageHandler;
import com.wequan.bu.controller.vo.ThreadVo;
import com.wequan.bu.controller.vo.result.Result;
import com.wequan.bu.controller.vo.result.ResultGenerator;
import com.wequan.bu.repository.model.Thread;
import com.wequan.bu.repository.model.ThreadResource;
import com.wequan.bu.repository.model.ThreadStream;
import com.wequan.bu.security.CurrentUser;
import com.wequan.bu.service.ThreadResourceService;
import com.wequan.bu.service.ThreadService;
import com.wequan.bu.service.ThreadStreamService;
import com.wequan.bu.util.GeneralTool;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

/**
 * @author ChrisChen
 */
@RestController
@Api(tags = "Thread")
public class ThreadController {

    private static final Logger log = LoggerFactory.getLogger(ThreadController.class);

    @Autowired
    private ThreadService threadService;
    @Autowired
    private ThreadStreamService threadStreamService;
    @Autowired
    private ThreadResourceService threadResourceService;
    @Autowired
    private MessageHandler messageHandler;

    @GetMapping("/thread/myschool/top")
    @ApiOperation(value = "a list of top thread for user's school", notes = "根据school id获取thread列表，按查看次数排序")
    @ApiResponses(
            @ApiResponse(code = 200, message = "a list of thread brief info (title, # of views, # of replies, thread id)")
    )
    public Result<List<Thread>> getMySchoolTopThreads(@RequestParam("schoolId") Integer schoolId,
                                                      @RequestParam(value = "pageNum", required = false) Integer pageNum,
                                                      @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        if (schoolId <= 0) {
            return ResultGenerator.fail(messageHandler.getMessage("40098"));
        }
        if (Objects.isNull(pageNum)) {
            pageNum = 1;
        }
        if (Objects.isNull(pageSize)) {
            pageSize = 0;
        }
        List<Thread> result = threadService.findBySchoolIdOrderByView(schoolId, pageNum, pageSize);
        return ResultGenerator.success(result);
    }

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
        if (schoolId <= 0 || tagId <= 0) {
            return ResultGenerator.fail(messageHandler.getMessage("40098"));
        }
        if (Objects.isNull(pageNum)) {
            pageNum = 1;
        }
        if (Objects.isNull(pageSize)) {
            pageSize = 0;
        }
        List<Thread> result = threadService.findBySchoolAndTag(schoolId,tagId, pageNum, pageSize);
        return ResultGenerator.success(result);
    }

    @GetMapping("/thread/{id}")
    @ApiOperation(value = "thread detail", notes = "返回帖子详情")
    @ApiResponses(
            @ApiResponse(code = 200, message = "title, created time, category, tag, user name,\n avatar, text body, pictures/doc, # of like, " +
                    "# of dislikes, # of replies, # of views, status, study points reward")
    )
    public Result<Thread> getThread(@PathVariable("id") Integer threadId,
                                    @CurrentUser Integer userId) {
        if (threadId <= 0) {
            return ResultGenerator.fail(messageHandler.getMessage("40098"));
        }
        if (Objects.isNull(userId)) {
            // return ResultGenerator.fail(messageHandler.getMessage("40099"));
            userId = 5;
        }
        Thread result = threadService.findByPrimaryKey(threadId);
        return ResultGenerator.success(result);
    }

    @GetMapping("/thread/{id}/direct_replies")
    @ApiOperation(value = "a list of direct replies to the thread", notes = "返回第一层（直接回复帖子的）回帖列表")
    @ApiResponses(
            @ApiResponse(code = 200, message = "a list of replies (user name, created time, avatar, # of likes, # of dislikes, text body,\n" +
                    "pictures/doc, # of replies, if reward)")
    )
    public Result<List<ThreadStream>> getThreadDirectReplies(@PathVariable("id") Integer threadId,
                                                             @RequestParam(value = "pageNum", required = false) Integer pageNum,
                                                             @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        if (threadId <= 0) {
            return ResultGenerator.fail(messageHandler.getMessage("40098"));
        }
        if (Objects.isNull(pageNum)) {
            pageNum = 1;
        }
        if (Objects.isNull(pageSize)) {
            pageSize = 0;
        }
        List<ThreadStream> result = threadStreamService.getDirectThreadReplies(threadId, pageNum, pageSize);
        return ResultGenerator.success(result);
    }

    @GetMapping("/thread/{id}/indirect_replies")
    @ApiOperation(value = "a list of indirect replies to the thread", notes = "返回第二层（间接回复帖子的）回帖列表")
    @ApiResponses(
            @ApiResponse(code = 200, message = "a list of replies (user name, created time, avatar, # of likes, # of dislikes, text body, pictures/doc, # of replies)")
    )
    public Result<List<ThreadStream>> getThreadIndirectReplies(@PathVariable("id") Integer threadId,
                                                               @RequestParam(value = "pageNum", required = false) Integer pageNum,
                                                               @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        if (threadId <= 0) {
            return ResultGenerator.fail(messageHandler.getMessage("40098"));
        }
        if (Objects.isNull(pageNum)) {
            pageNum = 1;
        }
        if (Objects.isNull(pageSize)) {
            pageSize = 0;
        }
        List<ThreadStream> result = threadStreamService.getIndirectThreadReplies(threadId, pageNum,pageSize);;
        return ResultGenerator.success(result);
    }

    @GetMapping("/thread/{id}/direct_reply/{drId}/indirect_replies")
    @ApiOperation(value = "a list of indirect replies to one thread's direct reply", notes = "返回针对直接回帖的第二层间接回帖列表")
    @ApiResponses(
            @ApiResponse(code = 200, message = "a list of replies (user name, created time, avatar, # of likes, # of dislikes, text body, pictures/doc)")
    )
    public Result<List<ThreadStream>> getThreadReplyIndirectReplies(@PathVariable("id") Integer threadId,
                                                                    @PathVariable("drId") Integer directReplyId,
                                                                    @RequestParam(value = "pageNum", required = false) Integer pageNum,
                                                                    @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        if (threadId <= 0 || directReplyId <= 0) {
            return ResultGenerator.fail(messageHandler.getMessage("40098"));
        }
        if (Objects.isNull(pageNum)) {
            pageNum = 1;
        }
        if (Objects.isNull(pageSize)) {
            pageSize = 0;
        }
        List<ThreadStream> result = threadStreamService.getThreadReplyIndirectReplies(threadId, directReplyId, pageNum, pageSize);;
        return ResultGenerator.success(result);
    }

    @PostMapping("/thread")
    @ApiOperation(value = "add thread", notes = "返回创建帖子成功与否")
    @Transactional(rollbackFor = Exception.class)
    public Result addThread(@RequestBody ThreadVo threadVo, @CurrentUser Integer userId) {
        if (!GeneralTool.checkThreadVo(threadVo)) {
            return ResultGenerator.fail(messageHandler.getMessage("40098"));
        }
//        if (threadVo.getCreateBy().compareTo(userId) != 0) {
//            return ResultGenerator.fail(messageHandler.getMessage("40099"));
//        }
        if (threadVo.getStudyPointsBonus() == null) {
            threadVo.setStudyPointsBonus((short) 0);
        }
        int threadId = threadService.addThread(threadVo);
        List<Integer> subjectIds = threadVo.getSubjectIds();
        if (Objects.nonNull(subjectIds) && subjectIds.size() > 0 && threadId > 0) {
            threadService.addThreadSubjects(threadId, subjectIds);
        }
        List<ThreadResource> threadResources = threadVo.getThreadResources();
        if (Objects.nonNull(threadResources) && threadResources.size() > 0 && threadId > 0) {
            threadResources.forEach(e -> {
                e.setBelongId(threadId);
                e.setType((short)1);
            });
            threadResourceService.save(threadResources);
        }
        return ResultGenerator.success();
    }

    @PostMapping("/thread/reply")
    @ApiOperation(value = "reply to the thread", notes = "包括直接/间接回复，返回回帖成功与否")
    @Transactional(rollbackFor = Exception.class)
    public Result addThreadReply(@RequestBody ThreadStream threadStream, @CurrentUser Integer userId) {
        if (!GeneralTool.checkThreadStream(threadStream)) {
            return ResultGenerator.fail(messageHandler.getMessage("40098"));
        }
//        if (threadStream.getUserId().compareTo(userId) != 0) {
//            return ResultGenerator.fail(messageHandler.getMessage("40099"));
//        }
        int threadStreamId = threadStreamService.addThreadReply(threadStream);
        List<ThreadResource> threadResources = threadStream.getThreadResources();
        if (Objects.nonNull(threadResources) && threadResources.size() > 0 && threadStreamId > 0) {
            threadResources.forEach(e -> {
                e.setBelongId(threadStreamId);
                e.setType((short)2);
            });
            threadResourceService.save(threadResources);
        }
        return ResultGenerator.success();
    }

    @PostMapping("/thread/report")
    @ApiOperation(value = "report thread", notes = "对帖子进行举报")
    @Transactional(rollbackFor = Exception.class)
    public Result reportThread(@RequestParam("threadId") Integer threadId,
                               @RequestParam("userId") Integer userId,
                               @RequestParam("reason") String reason) {
        if (threadId <= 0 || userId <= 0 || !StringUtils.hasText(reason)) {
            return ResultGenerator.fail(messageHandler.getMessage("40098"));
        }
        threadService.reportThread(threadId, userId, reason);
        return ResultGenerator.success();
    }

    @PostMapping("/thread/reply/report")
    @ApiOperation(value = "report the reply to thread", notes = "对帖子回复进行举报")
    public Result reportThreadReply(@RequestParam("threadId") Integer threadId,
                                    @RequestParam("replyId") Integer replyId,
                                    @RequestParam("userId") Integer userId,
                                    @RequestParam("reason") String reason) {
        if (threadId <= 0 || replyId <= 0 || userId <= 0 || !StringUtils.hasText(reason)) {
            return ResultGenerator.fail(messageHandler.getMessage("40098"));
        }
        threadService.reportThreadReply(threadId, replyId, userId, reason);
        return ResultGenerator.success();
    }

    @PostMapping("/thread/like")
    @ApiOperation(value = "like thread", notes = "对帖子点赞")
    public Result likeThread(@RequestParam("threadId") Integer threadId,
                             @RequestParam("userId") Integer userId) {
        if (threadId <= 0 || userId <= 0) {
            return ResultGenerator.fail(messageHandler.getMessage("40098"));
        }
        threadService.likeThread(threadId, userId);
        return ResultGenerator.success();
    }

    @PostMapping("/thread/dislike")
    @ApiOperation(value = "dislike thread", notes = "对帖子拍砖")
    public Result dislikeThread(@RequestParam("threadId") Integer threadId,
                                @RequestParam("userId") Integer userId) {
        if (threadId <= 0 || userId <= 0) {
            return ResultGenerator.fail(messageHandler.getMessage("40098"));
        }
        threadService.dislikeThread(threadId, userId);
        return ResultGenerator.success();
    }

    @PostMapping("/thread/reply/like")
    @ApiOperation(value = "like the reply of thread", notes = "对帖子回复点赞")
    public Result likeThreadReply(@RequestParam("threadId") Integer threadId,
                                  @RequestParam("replyId") Integer replyId,
                                  @RequestParam("userId") Integer userId) {
        if (threadId <= 0 || replyId <= 0 || userId <= 0) {
            return ResultGenerator.fail(messageHandler.getMessage("40098"));
        }
        threadStreamService.likeThreadReply(threadId, replyId, userId);
        return ResultGenerator.success();
    }

    @PostMapping("/thread/reply/dislike")
    @ApiOperation(value = "dislike the reply of thread", notes = "对帖子回复拍砖")
    public Result dislikeThreadReply(@RequestParam("threadId") Integer threadId,
                                     @RequestParam("replyId") Integer replyId,
                                     @RequestParam("userId") Integer userId) {
        if (threadId <= 0 || replyId <= 0 || userId <= 0) {
            return ResultGenerator.fail(messageHandler.getMessage("40098"));
        }
        threadStreamService.dislikeThreadReply(threadId,replyId,userId);
        return ResultGenerator.success();
    }

    @GetMapping("/thread/following")
    @ApiOperation(value = "a list of thread for user's following users", notes = "根据用户关注的人，获取thread列表，按创建时间排序")
    @ApiResponses(
            @ApiResponse(code = 200, message = "a list of thread card (title, thread id, user name, user id, tag, created time, first 100 words, \n" +
                    "first 3 photos, # of likes, # of dislikes, # of views, school id, study points reward, status) sorted by created time")
    )
    public Result<List<Thread>> getFollowingThreads(@RequestParam("userId") Integer userId,
                                                    @RequestParam(value = "pageNum", required = false) Integer pageNum,
                                                    @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        if (userId <= 0) {
            return ResultGenerator.fail(messageHandler.getMessage("40098"));
        }
        if (Objects.isNull(pageNum)) {
            pageNum = 1;
        }
        if (Objects.isNull(pageSize)) {
            pageSize = 0;
        }
        List<Thread> result = threadService.getUserFollowingThreads(userId, pageNum, pageSize);
        return ResultGenerator.success(result);
    }

    @GetMapping("/thread/explore")
    @ApiOperation(value = "a list of thread for other school", notes = "根据school id获取thread列表，按创建时间排序")
    @ApiResponses(
            @ApiResponse(code = 200, message = "a list of thread card (title, thread id, user name, user id, tag, created time, first 100 words, \n" +
                    "first 3 photos, # of likes, # of dislikes, # of views, school id, study points reward, status) sorted by created time")
    )
    public Result<List<Thread>> getOtherSchoolThreads(@RequestParam("schoolId") Integer schoolId,
                                                      @RequestParam(value = "pageNum", required = false) Integer pageNum,
                                                      @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        if (schoolId <= 0) {
            return ResultGenerator.fail(messageHandler.getMessage("40098"));
        }
        if (Objects.isNull(pageNum)) {
            pageNum = 1;
        }
        if (Objects.isNull(pageSize)) {
            pageSize = 0;
        }
        List<Thread> result = threadService.findByOtherSchoolId(schoolId, pageNum, pageSize);
        return ResultGenerator.success(result);
    }

    @GetMapping("/thread/study_help/subjects")
    @ApiOperation(value = "a list of user interested subjects", notes = "根据user id获取之前感兴趣的科目列表")
    public Result<String> getUserSelectedSubjects(@RequestParam("userId") Integer userId) {
        if (userId <= 0) {
            return ResultGenerator.fail(messageHandler.getMessage("40098"));
        }
        String subjectIds = threadService.findUserSelectedSubjects(userId);
        return ResultGenerator.success(subjectIds);
    }

    @PutMapping("/thread/study_help/subject")
    @ApiOperation(value = "update user interested subjects", notes = "根据user id更新感兴趣的科目列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "subjectIds", value = "多个subject id用逗号分隔", required = true)
    })
    public Result addUserSelectedSubjects(@RequestParam("userId") Integer userId,
                                          @RequestParam("subjectIds") String subjectIds) {
        if (userId <= 0 || !StringUtils.hasText(subjectIds)) {
            return ResultGenerator.fail(messageHandler.getMessage("40098"));
        }
        threadService.addUserSelectedSubjects(userId, subjectIds);
        return ResultGenerator.success();
    }

    @DeleteMapping("/thread/study_help/subject")
    @ApiOperation(value = "delete user interested subjects", notes = "根据user id删除感兴趣的科目列表")
    public Result deleteUserSelectedSubjects(@RequestParam("userId") Integer userId) {
        if (userId <= 0) {
            return ResultGenerator.fail(messageHandler.getMessage("40098"));
        }
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
        if (userId <= 0 || !StringUtils.hasText(subjectIds)) {
            return ResultGenerator.fail(messageHandler.getMessage("40098"));
        }
        if (Objects.isNull(pageNum)) {
            pageNum = 1;
        }
        if (Objects.isNull(pageSize)) {
            pageSize = 0;
        }
        List<Thread> threads = threadService.getUserInterestedStudyHelpThreads(userId, subjectIds, pageNum, pageSize);
        return ResultGenerator.success(threads);
    }

}
