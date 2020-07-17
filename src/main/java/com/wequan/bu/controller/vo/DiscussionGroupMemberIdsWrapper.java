package com.wequan.bu.controller.vo;

import lombok.Data;

import java.util.List;

/**
 * @author Zhaochao Huang
 */
@Data
public class DiscussionGroupMemberIdsWrapper {
    private Integer id;
    private String guid;
    private List<Integer> memberIds;
}
