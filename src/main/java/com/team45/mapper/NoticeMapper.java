package com.team45.mapper;


import com.team45.entity.Notice;
import com.team45.util.Page;
import org.apache.ibatis.annotations.*;
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

    @Update("update notice set title=#{title}, content=#{content}, img=#{img} where no=#{no}")
    public void boardEdit(Notice notice);

    @Delete("delete from notice where no=#{no}")
    public void boardDel(int no);

    @Select({"<script>","SELECT * FROM notice" +
            "<if test='searchType != null and searchType != \"\"'> WHERE ${searchType} LIKE CONCAT('%', #{searchKeyword}, '%')" +
            "</if>" +
            " ORDER BY resdate DESC LIMIT #{postStart}, #{postScreen}","</script>"})
    public List<Notice> boardPage(Page page);

    @Select({"<script>","SELECT COUNT(*) FROM notice",
            "<if test='searchType != null and searchType != \"\"'> WHERE ${searchType} LIKE CONCAT('%', #{searchKeyword}, '%')</if>","</script>"})
    public int noticeCount(Page page);
}
