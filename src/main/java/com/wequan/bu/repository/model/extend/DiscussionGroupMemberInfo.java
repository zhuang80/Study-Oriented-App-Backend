package com.wequan.bu.repository.model.extend;

import com.wequan.bu.repository.model.DiscussionGroupMember;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author ChrisChen
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class DiscussionGroupMemberInfo extends DiscussionGroupMember {

    private DiscussionGroupBriefInfo discussionGroupInfo;
    private UserBriefInfo memberInfo;
}
