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

    private String createByName;
    private String tagName;
    private Integer numberOfView;
    private Integer numberOfReply;

}
