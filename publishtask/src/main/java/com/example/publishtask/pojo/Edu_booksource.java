package com.example.publishtask.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Edu_booksource {
    private Integer bookId;
    private String name;
    private String url;
    private Integer courseId;
    private Integer studioId;

}
