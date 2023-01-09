package com.learningplatform.pojo;

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

    public Integer getStudio_id() {
        return studio_id;
    }

    public void setStudio_id(Integer studio_id) {
        this.studio_id = studio_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getStudio_kind() {
        return studio_kind;
    }

    public void setStudio_kind(String studio_kind) {
        this.studio_kind = studio_kind;
    }

    public Integer getStudio_adminid() {
        return studio_adminid;
    }

    public void setStudio_adminid(Integer studio_adminid) {
        this.studio_adminid = studio_adminid;
    }

    public String getCourseids() {
        return courseids;
    }

    public void setCourseids(String courseids) {
        this.courseids = courseids;
    }

    public String getBooks() {
        return books;
    }

    public void setBooks(String books) {
        this.books = books;
    }

    public String getLogo_url() {
        return logo_url;
    }

    public void setLogo_url(String logo_url) {
        this.logo_url = logo_url;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Timestamp getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Timestamp create_time) {
        this.create_time = create_time;
    }

    public String getStudio_url() {
        return studio_url;
    }

    public void setStudio_url(String studio_url) {
        this.studio_url = studio_url;
    }

    public String getStudio_tel() {
        return studio_tel;
    }

    public void setStudio_tel(String studio_tel) {
        this.studio_tel = studio_tel;
    }

    public String getStudio_email() {
        return studio_email;
    }

    public void setStudio_email(String studio_email) {
        this.studio_email = studio_email;
    }

    public Integer getHot() {
        return hot;
    }

    public void setHot(Integer hot) {
        this.hot = hot;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public Integer getColleageId() {
        return colleageId;
    }

    public void setColleageId(Integer colleageId) {
        this.colleageId = colleageId;
    }
}
