package com.team45.service;

import com.team45.entity.Member;
import com.team45.util.Page;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.time.LocalDateTime;
import java.util.List;

public interface MemberService {
    public List<Member> memberList(Page page);
    public Member memberGet(String id);
    public void memberInsert(Member member);
    public void memberUpdate(Member member);
    public void memberRemoveUpdate(String id);
    public void memberDelete(String id);
    public boolean idCheck (String id);
    public int loginPro(String id, String pw);
    public void memberactive(String id);
    void memberOutside(String id);
    void statuschange();
    void change(String id, LocalDateTime createAt);


}
