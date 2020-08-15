package com.wequan.bu.repository.model.extend;

import com.wequan.bu.repository.model.DiscussionGroup;
import com.wequan.bu.repository.model.FavoriteDiscussionGroup;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author ChrisChen
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class FavoriteDiscussionGroupBriefInfo extends FavoriteDiscussionGroup {

    private UserBriefInfo userInfo;
    private DiscussionGroup discussionGroupInfo;

}
