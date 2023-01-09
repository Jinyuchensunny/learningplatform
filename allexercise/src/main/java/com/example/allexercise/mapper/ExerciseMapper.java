package com.example.allexercise.mapper;

import com.example.allexercise.pojo.Exercise;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ExerciseMapper {
    @Select("select * from Exercise where videoId = #{videoId}")
    public List<Exercise> findExerciseByVideoId(Integer videoId);

    @Insert("insert into Exercise(videoId,content) values (#{videoId},#{content})")
    public int addExercise(Exercise exercise);

    @Select("select * from Exercise ")
    public List<Exercise> findAllExercise();
}
