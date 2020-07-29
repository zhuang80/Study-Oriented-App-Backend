package com.wequan.bu.controller;

import com.wequan.bu.config.handler.MessageHandler;
import com.wequan.bu.controller.vo.FavoriteCategory;
import com.wequan.bu.controller.vo.UserVo;
import com.wequan.bu.controller.vo.result.Result;
import com.wequan.bu.controller.vo.result.ResultGenerator;
import com.wequan.bu.exception.NotImplementedException;
import com.wequan.bu.repository.model.*;
import com.wequan.bu.repository.model.extend.*;
import com.wequan.bu.security.CurrentUser;
import com.wequan.bu.service.*;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

/**
 * @author ChrisChen
 */
@RestController
@Api(tags = "User")
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;
    @Autowired
    private AppointmentService appointmentService;
    @Autowired
    private OnlineEventService onlineEventService;
    @Autowired
    private DiscussionGroupService discussionGroupService;
    @Autowired
    private TutorInquiryService tutorInquiryService;
    @Autowired
    private ThreadService threadService;
    @Autowired
    private ThreadStreamService threadStreamService;
    @Autowired
    private MessageHandler messageHandler;
    @Autowired
    private ApplicationContext applicationContext;

    @GetMapping("/user/{id}/profile")
    @ApiOperation(value = "user basic info", notes = "返回用户基本信息")
    @ApiResponses(
            @ApiResponse(code = 200, message = "name, username, school name, school id, subjects, subject id, avatar, " +
                    "brief intro, # of threads, # of following, # of followers, # of groups")
    )
    public Result<UserStats> getUserProfile(@PathVariable("id") Integer userId) {
        UserStats userStats;
        if (userId <= 0) {
            return ResultGenerator.fail(messageHandler.getMessage("40098"));
        }
        userStats = userService.getUserProfile(userId);
        return ResultGenerator.success(userStats);
    }

    @PutMapping(value = "/user/{id}/profile")
    @ApiOperation(value = "update user basic info", notes = "修改用户基本信息")
    public Result updateUserProfile(@PathVariable("id") Integer userId,
                                    @RequestBody UserVo userVo) {
        if (userId <= 0) {
            return ResultGenerator.fail(messageHandler.getMessage("40098"));
        }
        userService.updateUserProfile(userId, userVo);
        return ResultGenerator.success();
    }

    @GetMapping("/user/{id}/tutor/profile")
    @ApiOperation(value = "tutor basic info", notes = "如果用户为tutor则返回用户作为tutor基本信息，否则返回空")
    public Result<TutorBriefInfo> getUserTutorProfile(@PathVariable("id") Integer userId) {
        if (userId <= 0) {
            return ResultGenerator.fail(messageHandler.getMessage("40098"));
        }
        TutorBriefInfo tutorBriefInfo = userService.getUserTutorProfile(userId);
        return ResultGenerator.success(tutorBriefInfo);
    }

    @GetMapping("/user/{id}/appointments")
    @ApiOperation(value = "a list of user’s appointment", notes = "返回用户与Tutor的appointment列表, status 参数可以用来筛选, 0 为代付款,4为已付款,1为已结束,2已退款")
    @ApiResponses(
            @ApiResponse(code = 200, message = "a list of appointments (name, if tutor, appointment id, scheduled time) sorted by incoming time")
    )
    public Result<List<AppointmentBriefInfo>> getAppointments(@PathVariable("id") Integer userId,
                                                              @RequestParam(value = "status", required = false) Short status,
                                                              @RequestParam(value = "pageNum", required = false) Integer pageNum,
                                                              @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        List<AppointmentBriefInfo> appointments;
        if (userId <= 0) {
            return ResultGenerator.fail(messageHandler.getMessage("40098"));
        }
        if (Objects.isNull(pageNum)) {
            pageNum = 1;
        }
        if (Objects.isNull(pageSize)) {
            pageSize = 0;
        }
        appointments = appointmentService.getUserAppointments(userId, pageNum, pageSize, status);
        return ResultGenerator.success(appointments);
    }

    @GetMapping("/user/{id}/online_events")
    @ApiOperation(value = "a list of user’s online event", notes = "返回用户的online event列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "type", value = "0, public class; 1, activity")
    })
    public Result<List<OnlineEvent>> getOnlineEvents(@PathVariable("id") Integer userId,
                                                     @RequestParam(value = "type", required = false) Integer type,
                                                     @RequestParam(value = "pageNum", required = false) Integer pageNum,
                                                     @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        List<OnlineEvent> onlineEvents = null;
        if (userId <= 0) {
            return ResultGenerator.fail(messageHandler.getMessage("40098"));
        }
        if (Objects.isNull(pageNum)) {
            pageNum = 1;
        }
        if (Objects.isNull(pageSize)) {
            pageSize = 0;
        }
        onlineEvents = onlineEventService.getUserOnlineEvents(userId, pageNum, pageSize, type);
        return ResultGenerator.success(onlineEvents);
    }

    @PostMapping("/user/{id}/online_event")
    @ApiOperation(value = "join/quit online event", notes = "加入/退出online event成功与否")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "oeId", value = "online event id"),
            @ApiImplicitParam(name = "action", value = "1 -> join; -1 -> quit")
    })
    public Result doOnlineEvent(@PathVariable("id") Integer userId,
                                @RequestParam("oeId") Integer oeId,
                                @RequestParam("action") Short action) {
        if (userId <= 0 || oeId <= 0 || (action != 1 && action != -1)) {
            return ResultGenerator.fail(messageHandler.getMessage("40098"));
        }
        try {
            onlineEventService.doUserAction(userId, oeId, action);
        }catch(Exception e){
            return ResultGenerator.fail(e.getMessage());
        }
        return ResultGenerator.success();
    }

    @GetMapping("/user/{id}/discussion_groups")
    @ApiOperation(value = "a list of user’s discussion group", notes = "返回用户的discussion group列表")
    public Result<List<DiscussionGroup>> getDiscussionGroups(@PathVariable("id") Integer userId,
                                                             @RequestParam(value = "pageNum", required = false) Integer pageNum,
                                                             @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        List<DiscussionGroup> discussionGroups = null;
        if (userId <= 0) {
            return ResultGenerator.fail(messageHandler.getMessage("40098"));
        }
        if (Objects.isNull(pageNum)) {
            pageNum = 1;
        }
        if (Objects.isNull(pageSize)) {
            pageSize = 0;
        }
        discussionGroups = discussionGroupService.getUserDiscussionGroups(userId, pageNum, pageSize);
        return ResultGenerator.success(discussionGroups);
    }

    @PostMapping("/user/{id}/discussion_group")
    @ApiOperation(value = "join/quit discussion group", notes = "加入/退出discussion group成功与否")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "dgId", value = "discussion group id"),
            @ApiImplicitParam(name = "action", value = "1 -> join; -1 -> quit")
    })
    public Result doDiscussionGroup(@PathVariable("id") Integer userId,
                                    @RequestParam("dgId") Integer dgId,
                                    @RequestParam("action") Short action) {
        if (userId <= 0 || dgId <= 0 || (action != 1 && action != -1)) {
            return ResultGenerator.fail(messageHandler.getMessage("40098"));
        }
        try{
            discussionGroupService.doUserAction(userId, dgId, action);
        }catch(Exception e){
            return ResultGenerator.fail(e.getMessage());
        }

        return ResultGenerator.success();
    }

    @GetMapping("/user/{id}/tutor_inquiries")
    @ApiOperation(value = "a list of user’s tutor inquiry", notes = "返回用户的tutor inquiry列表")
    public Result<List<TutorInquiry>> getTutorInquiries(@PathVariable("id") Integer userId,
                                                          @RequestParam(value = "pageNum", required = false) Integer pageNum,
                                                          @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        List<TutorInquiry> tutorInquiries = null;
        if (userId <= 0) {
            return ResultGenerator.fail(messageHandler.getMessage("40098"));
        }
        if (Objects.isNull(pageNum)) {
            pageNum = 1;
        }
        if (Objects.isNull(pageSize)) {
            pageSize = 0;
        }
        tutorInquiries = tutorInquiryService.getUserTutorInquiries(userId, pageNum, pageSize);
        return ResultGenerator.success(tutorInquiries);
    }

    @GetMapping("/user/{id}/threads")
    @ApiOperation(value = "a list of user’s threads", notes = "返回用户发布的帖子列表")
    @ApiResponses(
            @ApiResponse(code = 200, message = "a list of thread card (title, thread id, user name, user id, tag, created time, first 100 words, \n" +
                    "first 3 photos, # of likes, # of dislikes, # of views, school id, study points reward, status) sorted by created time")
    )
    public Result<List<ThreadStats>> getThreads(@PathVariable("id") Integer userId,
                                                @RequestParam(value = "pageNum", required = false) Integer pageNum,
                                                @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        List<ThreadStats> threads = null;
        if (userId <= 0) {
            return ResultGenerator.fail(messageHandler.getMessage("40098"));
        }
        if (Objects.isNull(pageNum)) {
            pageNum = 1;
        }
        if (Objects.isNull(pageSize)) {
            pageSize = 0;
        }
        threads = threadService.getUserThreads(userId, pageNum, pageSize);
        return ResultGenerator.success(threads);
    }

    @GetMapping("/user/{id}/thread_replies")
    @ApiOperation(value = "a list of user's thread with reply ", notes = "返回针对用户已发布帖子的回帖列表")
    public Result getUserThreadReplies(@PathVariable("id") Integer userId,
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
        List<ThreadStream> threadStreams = threadStreamService.getUserThreadReplies(userId, pageNum, pageSize);
        return ResultGenerator.success(threadStreams);
    }

    @GetMapping("/user/{id}/replies")
    @ApiOperation(value = "a list of user’s replies", notes = "返回用户发布的对帖子的回帖列表")
    public Result<List<ThreadStream>> getThreadReplies(@PathVariable("id") Integer userId,
                                                       @RequestParam(value = "pageNum", required = false) Integer pageNum,
                                                       @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        List<ThreadStream> threadReplies = null;
        if (userId <= 0) {
            return ResultGenerator.fail(messageHandler.getMessage("40098"));
        }
        if (Objects.isNull(pageNum)) {
            pageNum = 1;
        }
        if (Objects.isNull(pageSize)) {
            pageSize = 0;
        }
        threadReplies = threadStreamService.getUserReplies(userId, pageNum, pageSize);
        return ResultGenerator.success(threadReplies);
    }

    @GetMapping("/user/{id}/liked")
    @ApiOperation(value = "a list of user’s resource liked by others", notes = "返回用户资源被点赞的列表，按照资源创建时间排序")
    public Result<List<LikeRecordBriefInfo>> getUserLikedResources(@PathVariable("id") Integer userId,
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
        List<LikeRecordBriefInfo> likeRecords = userService.getUserLikedResources(userId, pageNum, pageSize);
        return ResultGenerator.success(likeRecords);
    }

    @GetMapping("/user/{id}/favorites")
    @ApiOperation(value = "a list of favorites according to category for user", notes = "按类别返回用户收藏列表，格式分别为" +
            "tutor(tutor name, tutor id, rating, subjects); course (course name, course id, course code);" +
            "material (title, if unlock, material type, upload date, # of likes, # of views)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "categoryId", value = "1 -> tutor; 2 -> course; 3 -> material;" +
                    " 4 -> thread; 5 -> professor; 6 -> activity; 7 -> public class; 8 -> thread reply")
    })
    public Result getFavorites(@PathVariable("id") Integer userId,
                               @RequestParam("categoryId") Integer categoryId,
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
        Class<? extends Service> favoriteServiceClazz = FavoriteCategory.getFavoriteService(categoryId);
        if (Objects.isNull(favoriteServiceClazz)) {
            return ResultGenerator.fail(messageHandler.getMessage("40098"));
        }
        Service favoriteService = applicationContext.getBean(favoriteServiceClazz);
        List favorites = favoriteService.findFavorites(userId, pageNum, pageSize);
        return ResultGenerator.success(favorites);
    }

    @GetMapping("/user/{id}/favorite")
    @ApiOperation(value = "favorite or not", notes = "按类别和favoriteId返回用户是否已收藏")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "categoryId", value = "1 -> tutor; 2 -> course; 3 -> material;" +
                    " 4 -> thread; 5 -> professor; 6 -> activity; 7 -> public class; 8 -> thread reply"),
            @ApiImplicitParam(name = "favoriteId", value = "收藏资源的id")
    })
    public Result<Boolean> checkFavorite(@PathVariable("id") Integer userId,
                                         @RequestParam("categoryId") Integer categoryId,
                                         @RequestParam("favoriteId") Integer favoriteId) {
        if (userId <= 0 || favoriteId <= 0) {
            return ResultGenerator.fail(messageHandler.getMessage("40098"));
        }
        Class<? extends Service> favoriteServiceClazz = FavoriteCategory.getFavoriteService(categoryId);
        if (Objects.isNull(favoriteServiceClazz)) {
            return ResultGenerator.fail(messageHandler.getMessage("40098"));
        }
        Service favoriteService = applicationContext.getBean(favoriteServiceClazz);
        boolean favorite = favoriteService.checkFavorite(userId, favoriteId);
        return ResultGenerator.success(favorite);
    }

    @PostMapping("/user/{id}/favorite")
    @ApiOperation(value = "favorite/unfavorite tutor/course/material/thread/professor", notes = "返回用户按类别收藏/取消收藏成功与否")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "categoryId", value = "1 -> tutor; 2 -> course; 3 -> material;" +
                    " 4 -> thread; 5 -> professor; 6 -> activity; 7 -> public class; 8 -> thread reply"),
            @ApiImplicitParam(name = "favoriteId", value = "收藏资源的id"),
            @ApiImplicitParam(name = "action", value = "1 -> 收藏；-1 -> 取消收藏")
    })
    public Result favorite(@PathVariable("id") Integer userId,
                           @RequestParam("categoryId") Integer categoryId,
                           @RequestParam("favoriteId") Integer favoriteId,
                           @RequestParam("action") Integer action) {
        if (userId <= 0 || favoriteId <= 0 || (action != 1 && action != -1)) {
            return ResultGenerator.fail(messageHandler.getMessage("40098"));
        }
        Class<? extends Service> favoriteServiceClazz = FavoriteCategory.getFavoriteService(categoryId);
        if (Objects.isNull(favoriteServiceClazz)) {
            return ResultGenerator.fail(messageHandler.getMessage("40098"));
        }
        Service favoriteService = applicationContext.getBean(favoriteServiceClazz);
        favoriteService.postFavorite(userId, favoriteId, action);
        return ResultGenerator.success();
    }

    @GetMapping("/user/{id}/follow")
    @ApiOperation(value = "get follow relationship with other user", notes = "返回用户同其他用户的关注关系")
    @ApiResponses(
            @ApiResponse(code = 200, message = "1 -> 完全没关注; 2 -> 我关注了他; 3 -> 他关注了我; 4 -> 我们相互关注")
    )
    public Result<Integer> followRelationshipOtherUser(@PathVariable("id") Integer userId,
                                                       @RequestParam("otherUserId") Integer otherUserId) {
        if (userId <= 0 || otherUserId <= 0) {
            return ResultGenerator.fail(messageHandler.getMessage("40098"));
        }
        if (userId - otherUserId == 0) {
            return ResultGenerator.fail(messageHandler.getMessage("40097"));
        }
        Integer relCode = userService.getFollowRelationshipWithOtherUser(userId, otherUserId);
        return ResultGenerator.success(relCode);
    }

    @PostMapping("/user/{id}/follow")
    @ApiOperation(value = "follow/unfollow other user", notes = "返回关注/取关其他用户成功与否")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "action", value = "1 -> 关注；-1 -> 取关")
    })
    public Result followOtherUser(@PathVariable("id") Integer userId,
                                  @RequestParam("otherUserId") Integer otherUserId,
                                  @RequestParam("action") Integer action) {
        if (userId <= 0 || otherUserId <= 0 || (action != 1 && action != -1)) {
            return ResultGenerator.fail(messageHandler.getMessage("40098"));
        }
        if (userId - otherUserId == 0) {
            return ResultGenerator.fail(messageHandler.getMessage("40097"));
        }
        userService.followOtherUser(userId, otherUserId, action);
        return ResultGenerator.success();
    }

    @GetMapping("/user/{id}/following")
    @ApiOperation(value = "a list of following", notes = "返回所关注用户列表")
    @ApiResponses(
            @ApiResponse(code = 200, message = "a list of user cards (name, username, user id, avatar, status)")
    )
    public Result<List<UserFollowBriefInfo>> getUserFollowing(@PathVariable("id") Integer userId,
                                                              @RequestParam(value = "pageNum", required = false) Integer pageNum,
                                                              @RequestParam(value = "pageSize", required = false) Integer pageSize,
                                                              @CurrentUser Integer currentUserId) {
        List<UserFollowBriefInfo> userFollowBriefInfoList = null;
        if (userId <= 0) {
            return ResultGenerator.fail(messageHandler.getMessage("40098"));
        }
        if (Objects.isNull(pageNum)) {
            pageNum = 1;
        }
        if (Objects.isNull(pageSize)) {
            pageSize = 0;
        }
        if (userId.compareTo(currentUserId) == 0) {
            userFollowBriefInfoList = userService.getUserFollowing(userId, pageNum, pageSize);
        } else {
            userFollowBriefInfoList = userService.getOtherUserFollowing(currentUserId, userId, pageNum, pageSize);
        }
        return ResultGenerator.success(userFollowBriefInfoList);
    }

    @GetMapping("/user/{id}/followers")
    @ApiOperation(value = "a list of followers", notes = "返回被关注用户列表")
    public Result<List<UserFollowBriefInfo>> getUserFollowers(@PathVariable("id") Integer userId,
                                                              @RequestParam(value = "pageNum", required = false) Integer pageNum,
                                                              @RequestParam(value = "pageSize", required = false) Integer pageSize,
                                                              @CurrentUser Integer currentUserId) {
        List<UserFollowBriefInfo> userFollowBriefInfoList = null;
        if (userId <= 0) {
            return ResultGenerator.fail(messageHandler.getMessage("40098"));
        }
        if (Objects.isNull(pageNum)) {
            pageNum = 1;
        }
        if (Objects.isNull(pageSize)) {
            pageSize = 0;
        }
        if (userId.compareTo(currentUserId) == 0) {
            userFollowBriefInfoList = userService.getUserFollower(userId, pageNum, pageSize);
        } else {
            userFollowBriefInfoList = userService.getOtherUserFollower(currentUserId, userId, pageNum, pageSize);
        }
        return ResultGenerator.success(userFollowBriefInfoList);
    }

    @GetMapping("/user/{id}/professor/reviews")
    @ApiOperation(value = "a list of user's review for professor", notes = "返回用户对授课教师评价列表")
    public Result<List<ProfessorCourseRate>> getUserProfessorReviews(@PathVariable("id") Integer userId) {
        List<ProfessorCourseRate> result = null;
        throw new NotImplementedException("Not implemented yet in Phase I");
    }

    @GetMapping("/user/{id}/appointment/reviews")
    @ApiOperation(value = "a list of user's review for appointment ", notes = "返回用户对appointment评价列表")
    public Result<List<AppointmentReview>> getUserAppointmentReviews(@PathVariable("id") Integer userId,
                                                                     @RequestParam(value = "pageNum", required = false) Integer pageNum,
                                                                     @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        List<AppointmentReview> appointmentReviews = null;
        if (userId <= 0) {
            return ResultGenerator.fail(messageHandler.getMessage("40098"));
        }
        if (Objects.isNull(pageNum)) {
            pageNum = 1;
        }
        if (Objects.isNull(pageSize)) {
            pageSize = 0;
        }
        appointmentReviews = userService.getUserAppointmentReviews(userId, pageNum, pageSize);
        return ResultGenerator.success(appointmentReviews);
    }
}
