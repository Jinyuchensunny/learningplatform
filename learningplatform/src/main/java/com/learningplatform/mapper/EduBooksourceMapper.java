package com.learningplatform.mapper;

import com.learningplatform.pojo.Edu_booksource;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface EduBooksourceMapper {
    @Select("select * from Edu_booksource where  studioId= #{studioId}")
    public List<Edu_booksource> findBookByStudioId(Integer studioId);

    @Select("select * from Edu_booksource where bookId = #{bookId}")
    public Edu_booksource findBookById(Integer bookId);

    @Select("select * from Edu_booksource ")
    public List<Edu_booksource> findAllBook();
}
