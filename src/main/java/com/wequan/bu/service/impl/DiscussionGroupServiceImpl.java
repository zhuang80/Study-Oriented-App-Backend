package com.wequan.bu.service.impl;

import com.github.pagehelper.PageHelper;
import com.wequan.bu.controller.vo.DiscussionGroupMemberIdsWrapper;
import com.wequan.bu.repository.dao.DiscussionGroupMapper;
import com.wequan.bu.repository.model.DiscussionGroup;
import com.wequan.bu.repository.model.DiscussionGroupMember;
import com.wequan.bu.repository.model.User;
import com.wequan.bu.repository.model.extend.DiscussionGroupBriefInfo;
import com.wequan.bu.repository.model.extend.DiscussionGroupMemberInfo;
import com.wequan.bu.service.AbstractService;
import com.wequan.bu.service.DiscussionGroupService;
import com.wequan.bu.service.UserService;
import com.wequan.bu.util.AvailabilityStatus;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author ChrisChen
 */
@Service
public class DiscussionGroupServiceImpl extends AbstractService<DiscussionGroup> implements DiscussionGroupService {

    private static final Logger log = LoggerFactory.getLogger(DiscussionGroupServiceImpl.class);

    @Autowired
    private DiscussionGroupMapper discussionGroupMapper;

    @Autowired
    private UserService userService;

    @PostConstruct
    public void postConstruct(){
        this.setMapper(discussionGroupMapper);
    }

    @Override
    public List<DiscussionGroup> getUserDiscussionGroups(Integer userId, Integer pageNum, Integer pageSize) {
        RowBounds rowBounds = new RowBounds(pageNum, pageSize);
        return discussionGroupMapper.selectByUserId(userId, rowBounds);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void doUserAction(Integer userId, Integer dgId, Short action) throws Exception {
        DiscussionGroup discussionGroup = discussionGroupMapper.selectByPrimaryKey(dgId);
        User user = userService.findById(userId);

        //check whether the group is full yet
        if(discussionGroup.getCurrentHeadcount() >= discussionGroup.getGroupCapacity()){
            throw new Exception("The group is full now.");
        }

        //check whether the group is visible to the user
        if(!discussionGroup.getVisible() && !user.getSchoolId().equals(discussionGroup.getBelongSchoolId())){
            throw new Exception("The group is not visible for your school.");
        }

        DiscussionGroupMember discussionGroupMember = new DiscussionGroupMember();
        discussionGroupMember.setDiscussionGroupId(dgId);
        discussionGroupMember.setMemberId(userId);
        discussionGroupMember.setAction(action);
        discussionGroupMember.setActionTime(new Date());
        discussionGroupMapper.insertOrUpdateActionByUserId(discussionGroupMember);

        //update discussion group current headcount
        discussionGroupMapper.updateCurrentHeadcountByPrimaryKey(dgId);
    }

    @Override
    public List<DiscussionGroup> search(String whereCondition, String orderCondition, Map<String, Integer> pageCondition) {
        List<DiscussionGroup> discussionGroups = null;
        discussionGroups = discussionGroupMapper.selectByConditions(whereCondition, orderCondition,
                new RowBounds(pageCondition.get("pageNo"), pageCondition.get("pageSize")));
        return discussionGroups;
    }

    @Override
    public List<DiscussionGroup> findAll(Integer pageNum, Integer pageSize) {
        if(pageNum == null || pageNum < 0){
            pageNum = 1;
        }
        if(pageSize == null || pageSize < 0){
            pageSize = 10;
        }
        PageHelper.startPage(pageNum, pageSize);
        return discussionGroupMapper.selectAll();
    }

    @Override
    public List<DiscussionGroup> findBySchoolId(Integer schoolId, Integer pageNum, Integer pageSize) {
        if(pageNum == null || pageNum < 0){
            pageNum = 1;
        }
        if(pageSize == null || pageSize < 0){
            pageSize = 10;
        }
        PageHelper.startPage(pageNum, pageSize);
        return discussionGroupMapper.selectBySchoolId(schoolId);
    }

    @Override
    public List<Integer> findMemberIdsByDiscussionGroupId(Integer id) {
        return discussionGroupMapper.selectMemberIdsByDiscussionGroupId(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateAvailability(Integer id, Short action) throws Exception {
        DiscussionGroup discussionGroup = discussionGroupMapper.selectByPrimaryKey(id);

        if(discussionGroup == null){
            throw new Exception("No such discussion group.");
        }

       if(action == AvailabilityStatus.AVAILABLE.getValue()){
           discussionGroup.setStatus(AvailabilityStatus.AVAILABLE.getValue());
       }else if(action == AvailabilityStatus.UNAVAILABLE.getValue()){
           discussionGroup.setStatus(AvailabilityStatus.UNAVAILABLE.getValue());
       }else{
           throw new Exception("No such action.");
       }
       discussionGroup.setUpdateTime(LocalDateTime.now());
       discussionGroupMapper.updateByPrimaryKeySelective(discussionGroup);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateName(Integer id, String name) throws Exception {
        DiscussionGroup discussionGroup = discussionGroupMapper.selectByPrimaryKey(id);

        if(discussionGroup == null){
            throw new Exception("No such discussion group.");
        }

        discussionGroup.setName(name);
        discussionGroup.setUpdateTime(LocalDateTime.now());
        discussionGroupMapper.updateByPrimaryKeySelective(discussionGroup);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateVisibility(Integer id, boolean visibility) throws Exception {
        DiscussionGroup discussionGroup = discussionGroupMapper.selectByPrimaryKey(id);

        if(discussionGroup == null){
            throw new Exception("No such discussion group.");
        }

        discussionGroup.setVisible(visibility);
        discussionGroup.setUpdateTime(LocalDateTime.now());
        discussionGroupMapper.updateByPrimaryKeySelective(discussionGroup);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateBriefDescription(Integer id, String briefDescription) throws Exception {
        DiscussionGroup discussionGroup = discussionGroupMapper.selectByPrimaryKey(id);

        if(discussionGroup == null){
            throw new Exception("No such discussion group.");
        }

        discussionGroup.setBriefDescription(briefDescription);
        discussionGroup.setUpdateTime(LocalDateTime.now());
        discussionGroupMapper.updateByPrimaryKeySelective(discussionGroup);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateGroupMessage(Integer id, String groupMessage) throws Exception {
        DiscussionGroup discussionGroup = discussionGroupMapper.selectByPrimaryKey(id);

        if(discussionGroup == null){
            throw new Exception("No such discussion group.");
        }

        discussionGroup.setGroupMessage(groupMessage);
        discussionGroup.setUpdateTime(LocalDateTime.now());
        discussionGroupMapper.updateByPrimaryKeySelective(discussionGroup);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateTag(Integer id, Short tagId) throws Exception {
        DiscussionGroup discussionGroup = discussionGroupMapper.selectByPrimaryKey(id);

        if(discussionGroup == null){
            throw new Exception("No such discussion group.");
        }

        discussionGroup.setTagId(tagId);
        discussionGroup.setUpdateTime(LocalDateTime.now());
        discussionGroupMapper.updateByPrimaryKeySelective(discussionGroup);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateLogo(Integer id, String logoUrl) throws Exception {
        DiscussionGroup discussionGroup = discussionGroupMapper.selectByPrimaryKey(id);

        if(discussionGroup == null){
            throw new Exception("No such discussion group.");
        }

        discussionGroup.setLogo(logoUrl);
        discussionGroup.setUpdateTime(LocalDateTime.now());
        discussionGroupMapper.updateByPrimaryKeySelective(discussionGroup);
    }

    @Override
    public List<DiscussionGroupMemberIdsWrapper> findMemberIdsForAllDiscussionGroup() {
        Map<Integer, List<Integer>> result = new HashMap<>();
        List<DiscussionGroupMember> discussionGroupMembers = discussionGroupMapper.selectDiscussionGroupMembers();
        for(DiscussionGroupMember m: discussionGroupMembers){
            List<Integer> members = result.getOrDefault(m.getDiscussionGroupId(), new ArrayList<>());
            members.add(m.getMemberId());
            result.put(m.getDiscussionGroupId(), members);
        }

        List<DiscussionGroupMemberIdsWrapper> memberIdsWrappers = new ArrayList<>();
        for(Map.Entry<Integer, List<Integer>> entry: result.entrySet()){
            DiscussionGroupMemberIdsWrapper wrapper = new DiscussionGroupMemberIdsWrapper();
            wrapper.setId(entry.getKey());
            wrapper.setMemberIds(entry.getValue());
            memberIdsWrappers.add(wrapper);
        }
        return memberIdsWrappers;
    }

    @Override
    public DiscussionGroup findDetailById(Integer id) {
        DiscussionGroupBriefInfo discussionGroupBriefInfo = discussionGroupMapper.selectDetailById(id);
        List<DiscussionGroupMemberInfo> groupMembers = discussionGroupBriefInfo.getGroupMembers();
        if (groupMembers != null && groupMembers.size() > 0) {
            List<DiscussionGroupMemberInfo> sortedGroupMembers = groupMembers.stream().sorted(Comparator.comparing(DiscussionGroupMember::getActionTime).reversed()).collect(Collectors.toList());
            discussionGroupBriefInfo.setGroupMembers(sortedGroupMembers);
        }
        return discussionGroupBriefInfo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(DiscussionGroup discussionGroup){
        User user = userService.findById(discussionGroup.getCreateBy());

        if(user != null){
            discussionGroup.setBelongSchoolId(user.getSchoolId());
            discussionGroup.setCreateTime(LocalDateTime.now());
            discussionGroup.setStatus((short) AvailabilityStatus.AVAILABLE.getValue());
            discussionGroup.setGroupManagerId(discussionGroup.getCreateBy());
            discussionGroup.setGuid(String.valueOf(UUID.randomUUID()));
            discussionGroupMapper.insertSelective(discussionGroup);
        }
    }
}
