package com.wequan.bu.repository.model.extend;

import com.wequan.bu.repository.model.ThreadStream;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author ChrisChen
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ThreadStreamStats extends ThreadStream {

    private Integer numberOfReply;
    private UserBriefInfo userBriefInfo;
    private ThreadBriefInfo threadBriefInfo;

}
