package com.wequan.bu.controller.vo;

import lombok.Data;

import java.util.List;

/**
 * @author ChrisChen
 */
@Data
public class DiscussionGroup extends BaseVo {

    private String name;
    private String briefDescription;
    private short status;
    private String groupMessage;
    private int groupManagerId;
    private int groupCapacity;
    private byte[] logo;
    private byte[] image;
    private boolean visible;
    private short belongSchoolId;
    private int numberOfMember;
    private List<Integer> tags;
    private String guid;

}
