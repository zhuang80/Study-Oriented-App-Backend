package com.wequan.bu.repository.model.extend;

import com.wequan.bu.repository.model.Thread;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author ChrisChen
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ThreadStats extends Thread {

    private String subjectIds;
    private Integer numberOfView;
    private Integer numberOfReply;
    private UserBriefInfo createByUser;

}
