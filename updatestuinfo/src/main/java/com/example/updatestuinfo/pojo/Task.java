package com.example.updatestuinfo.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Task {
    private Integer taskId;
    private Integer videoId;
    private Integer userId;
    private Integer status;
    private Timestamp createTime;
    private Timestamp receiveTime;
    private Timestamp finishTime;
    private String detail;
    private Integer type;
    private Integer bookId;
    private Integer level;
    private String answer;
}
