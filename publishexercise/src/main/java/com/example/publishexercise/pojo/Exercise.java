package com.example.publishexercise.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Exercise {
    private Integer id;
    private Integer videoId;
    private String content;

}
