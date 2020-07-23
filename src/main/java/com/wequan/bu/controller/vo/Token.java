package com.wequan.bu.controller.vo;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

/**
 * @author ChrisChen
 */
@Data
@Builder
public class Token {
    String token;
    String subject;
    Date issueDate;
    Date expiryDate;
}
