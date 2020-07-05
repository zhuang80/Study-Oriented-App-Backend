package com.wequan.bu.repository.model.extend;

import com.wequan.bu.repository.model.ThreadStream;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author ChrisChen
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ThreadStreamBriefInfo extends ThreadStream {

    private ThreadBriefInfo thread;

}
