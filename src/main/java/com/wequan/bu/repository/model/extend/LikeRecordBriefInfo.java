package com.wequan.bu.repository.model.extend;

import com.wequan.bu.repository.model.LikeRecord;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author ChrisChen
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class LikeRecordBriefInfo extends LikeRecord {

    private Object resource;

}
