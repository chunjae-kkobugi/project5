package com.team45.mapper;

import com.team45.entity.Member;
import com.team45.util.Page;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.*;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface MemberMapper {
    // 리스트의 페이지 처리
    @Select({"<script>","SELECT * FROM member WHERE",
            "<if test='searchType != null and searchType != \"\"'> ${searchType} LIKE CONCAT('%', #{searchKeyword}, '%') AND</if>",
            "status!='REMOVE'"+
                    "ORDER BY createAt ASC LIMIT #{postStart}, #{postCount}","</script>"})
    List<Member> memberList(Page page);

    @Select("SELECT * FROM member WHERE id=#{id}")
    Member memberGet(String id);

    @Insert("INSERT INTO member VALUES (default, #{id}, #{pw}, #{name}, #{tel}, #{email}, #{addr1}, #{addr2}, #{addr3}, #{postcode}, default, default)")
    void memberInsert(Member member);

    @Update("UPDATE member SET pw=#{pw}, name=#{name}, tel=#{tel}, email=#{email}, addr1=#{addr1}, addr2=#{addr2}, addr3=#{addr3}, postcode=#{postcode} WHERE id=#{id}")
    void memberUpdate(Member member);

    @Update("UPDATE member SET status='REMOVE' WHERE id=#{id}")
    void memberRemoveUpdate(String id);

    @Delete("DELETE FROM member WHERE id=#{id}")
    void memberDelete(String id);

    @Select("SELECT COUNT(*) FROM member WHERE id = #{id}")
    int idCheck (String id);

    @Update("UPDATE member SET status=default WHERE id=#{id}")
    void memberactive(String id);

    @Update("UPDATE member SET status='OUTSIDE' WHERE id=#{id}")
    void memberOutside(String id);

    List<Member> statuschange(String status, LocalDateTime createAt);

    @Update("UPDATE member SET status='REST' WHERE id=#{id}")
    void change(String id);

}
