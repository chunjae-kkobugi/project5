package com.team45.mapper;

import com.team45.entity.Member;
import com.team45.util.Page;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface MemberMapper {
    // 리스트의 페이지 처리
    @Select({"<script>","SELECT * FROM member WHERE",
            "<if test='searchType != null and searchType != \"\"'> ${searchType} LIKE CONCAT('%', #{searchKeyword}, '%') AND</if>",
            "status!='REMOVE'"+
                    "ORDER BY createAt ASC LIMIT #{postStart}, #{postCount}","</script>"})
    public List<Member> memberList(Page page);

    @Select("SELECT * FROM member WHERE id=#{id}")
    public Member memberGet(String id);

    @Insert("INSERT INTO member (id, pw, name, tel, email, addr1, addr2, addr3, postcode) VALUES (#{id}, #{pw}, #{name}, #{tel}, #{email}, #{addr1}, #{addr2}, #{addr3}, #{postcode})")
    public int memberInsert(Member member);

    @Update("UPDATE member SET pw=#{pw}, name=#{name}, tel=#{tel}, email=#{email}, addr1=#{addr1}, addr2=#{addr2}, addr3=#{addr3}, postcode=#{postcode} WHERE id=#{id}")
    public int memberUpdate(Member member);

    @Update("UPDATE member SET status='REMOVE' WHERE id=#{id}")
    public int memberRemoveUpdate(String id);

    @Delete("DELETE FROM member WHERE id=#{id}")
    public int memberDelete(String id);

    @Select("SELECT COUNT(*) FROM member WHERE id = #{id}")
    public int idCheck (String id);
}
