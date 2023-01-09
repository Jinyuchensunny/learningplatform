package com.example.addstudio.mapper;

import com.example.addstudio.pojo.Edu_studio;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface EduStudioMapper {
    @Select("select * from Edu_studio where colleageId = #{colleageId}")
    public List<Edu_studio> findStudioByColleageId(Integer colleageId);

    @Select("select * from Edu_studio where studio_id = #{studioId}")
    public Edu_studio findStudioByStudioId(Integer studioId);

    @Select("select * from Edu_studio ")
    public List<Edu_studio> findAllStudio();

    @Insert("insert into Edu_studio(name,content,create_time,colleageId) values (#{name},#{content},#{create_time},#{colleageId})")
    public int addStudio(Edu_studio studio);
}
