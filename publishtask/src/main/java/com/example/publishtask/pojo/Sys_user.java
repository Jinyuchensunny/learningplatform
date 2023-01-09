package com.example.publishtask.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Sys_user {
    private Integer user_id;
    private String login_name;
    private String login_pwd;
    private String user_name;
    private Integer status;
    private Timestamp last_login_time;
    private String last_login_ip;
    private Timestamp create_time;
    private String email;
    private String tel;
    private Integer role_id;
    private Integer bindingRole;
    private String uuid;
    private Integer is_examine;

}
