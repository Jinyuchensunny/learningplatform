package com.example.allcolleage.mapper;

import com.example.allcolleage.pojo.Colleage;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ColleageMapper {
    @Insert("insert into Colleage(colleage_name,longitude,latitude,address,detail) values (#{colleage_name},#{longitude},#{latitude},#{address},#{detail})")
    public int addColleage(Colleage colleage);

    @Select("select * from Colleage ")
    public List<Colleage> findAllColleage();

    @Select("select colleage_name from colleage where id = #{colleageId}")
    public String findColleageNameByColleageId(Integer colleageId);

    @Select("select * from colleage where id = #{colleageId}")
    public Colleage findColleageByColleageId(Integer colleageId);
}
