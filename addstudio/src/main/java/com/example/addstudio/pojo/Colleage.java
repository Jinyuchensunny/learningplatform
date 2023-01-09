package com.example.addstudio.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Colleage {
    private Integer id;
    private String colleage_name;
    private double longitude;
    private double  latitude;
    private String address;
    private String detail;

}

