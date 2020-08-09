package com.wequan.bu.repository.model.extend;

import com.wequan.bu.repository.model.DiscussionGroup;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author ChrisChen
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class DiscussionGroupBriefInfo extends DiscussionGroup {

    private UserBriefInfo createByUser;
    private UserBriefInfo groupManagerInfo;
    private List<DiscussionGroupMemberInfo> groupMembers;

}
