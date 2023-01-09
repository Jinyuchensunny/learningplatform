package com.learningplatform.mapper;

import com.learningplatform.pojo.Edu_videosource;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface EduVideoSourceMapper {

    @Select("select * from Edu_videosource where studioId = #{studioId}")
    public List<Edu_videosource> findVideoByStudioId(Integer studioId);

    @Select("select * from Edu_videosource where videoId = #{videoId}")
    public Edu_videosource findVideoById(Integer videoId);

    @Select("select * from Edu_videosource ")
    public List<Edu_videosource> findAllVideo();
}
