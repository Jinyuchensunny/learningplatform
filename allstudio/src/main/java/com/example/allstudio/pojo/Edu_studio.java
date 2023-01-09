package com.example.allstudio.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Edu_studio {
    private Integer studio_id;
    private String name;
    private String location;
    private String studio_kind;
    private Integer studio_adminid;
    private String courseids;
    private String books;
    private String logo_url;
    private String content;
    private Timestamp create_time;
    private String studio_url;
    private String studio_tel;
    private String studio_email;
    private Integer hot;
    private Integer num;
    private String keyword;
    private Integer colleageId;

}
