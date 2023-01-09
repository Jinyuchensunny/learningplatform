package com.example.publishexercise.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Edu_videosource {
    private Integer videoId;
    private String name;
    private String videoSize;
    private Timestamp addTime;
    private String status;
    private String videoLength;
    private String idVarchar;
    private Integer videoDuration;
    private String imageUrl;
    private Integer fileType;
    private Integer initType;
    private Integer uploadUserId;
    private String videoType;
    private String url;
    private Integer courseId;
    private Integer studioId;

}
