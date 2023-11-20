package com.team45.mapper;


import com.team45.entity.Notice;
import com.team45.util.Page;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
public interface NoticeMapper {
    @Select("select * from notice")
    public List<Notice> boardList();

    @Select("select * from notice where no=#{no}")
    public Notice boardGet(int no);

    @Insert("insert into notice values(default, #{title}, #{content}, #{author}, #{img}, default)")
    public void boardAdd(Notice notice);

    @Delete("delete from notice where no=#{no}")
    public void boardDel(int no);

    @Select({"<script>","SELECT * FROM notice" +
            "<if test='searchType != null and searchType != \"\"'> WHERE ${searchType} LIKE CONCAT('%', #{searchKeyword}, '%')" +
            "</if>" +
            " ORDER BY resdate ASC LIMIT #{postStart}, #{postCount}", "</script>"})
    public List<Notice> boardPage(Page page);
}
