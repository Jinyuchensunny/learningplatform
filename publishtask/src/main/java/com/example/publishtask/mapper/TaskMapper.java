package com.example.publishtask.mapper;

import com.example.publishtask.pojo.Task;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface TaskMapper {
    @Select("select * from Task where userId = #{userId}")
    public List<Task> findTaskByUserId(Integer userId);

    @Update("update Task set status=#{status} where taskId=#{taskId}")
    public int updateTaskStatus(Task task);

    @Update("update Task set status=#{status},answer=#{answer},finishTime=#{finishTime} where taskId=#{taskId}")
    public int updateTaskAnswer(Task task);

    @Select("select * from Task where taskId = #{taskId}")
    public Task findTaskById(Integer taskId);

    @Insert("insert into Task(videoId,userId,status,createTime,detail,type,bookId,level) values (#{videoId},#{userId},#{status},#{createTime},#{detail},#{type},#{bookId},#{level})")
    public int addTask(Task task);

    @Select("select * from Task ")
    public List<Task> findAllTask();

    @Delete("delete from task where user_id = #{userId}")
    public void DeleteTaskByUserId(Integer userId);

    @Update("update task set status=#{status} where taskId=#{taskId}")
    public int updateTask(Task task);
}
