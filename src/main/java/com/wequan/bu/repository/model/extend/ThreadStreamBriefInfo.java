package com.wequan.bu.repository.model.extend;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.wequan.bu.repository.model.Thread;
import com.wequan.bu.repository.model.ThreadStream;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author ChrisChen
 */
@Data
@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties({"thread.content", "thread.studyPointsBonus", "thread.createTime",
        "thread.updateTime", "thread.likes", "thread.dislikes"})
public class ThreadStreamBriefInfo extends ThreadStream {

    private Thread thread;

}
