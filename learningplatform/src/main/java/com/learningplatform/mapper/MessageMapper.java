package com.learningplatform.mapper;

import com.learningplatform.pojo.Message;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface MessageMapper {
    @Insert("insert into Message(realName,email,userName,content,createTime) values (#{realName},#{email},#{userName},#{content},#{createTime})")
    public int addMessage(Message message);

    @Select("select * from Message ")
    public List<Message> findAllMessage();
}
