package com.wequan.bu.repository.model.extend;

import com.wequan.bu.repository.model.Thread;
import lombok.Data;

@Data
public class ThreadBriefInfo extends Thread {

    private Integer numberOfView;
    private Integer numberOfReply;

}
