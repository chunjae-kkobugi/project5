package com.team45.mapper;

import com.team45.entity.Keyword;
import com.team45.util.Page;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface KeywordMapper {

    @Select({
            "<script>",
            "SELECT * FROM keyword",
            "WHERE uid = #{uid}",
            "<if test='page.searchType != null and page.searchType != \"\"'>",
            "   AND ${page.searchType} LIKE CONCAT('%', #{page.searchKeyword}, '%')",
            "</if>",
            "ORDER BY kno DESC LIMIT #{page.postStart}, #{page.postScreen}",
            "</script>"
    })
    List<Keyword> keywordsByUid(@Param("uid") String uid, @Param("page") Page page);

    @Insert("INSERT INTO keyword VALUES (default, #{word}, #{uid})")
    void keywordInsert(Keyword keyword);

    @Update("UPDATE keyword SET word=#{word} WHERE kno=#{kno}")
    void keywordUpdate(Keyword keyword);

    @Delete("DELETE FROM keyword WHERE kno=#{kno}")
    void keywordDelete(Long kno);
}
