package com.wequan.bu.repository.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wequan.bu.json.CustomLocalDateTimeSerializer;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author Zhaochao Huang
 */
@Data
public class School {
    private Short id;
    private String name;
    private Integer createBy;
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    private LocalDateTime createTime;
}
