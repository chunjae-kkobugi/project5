package com.team45.service;

import com.team45.entity.Member;
import com.team45.util.Page;

import java.time.LocalDateTime;
import java.util.List;

public interface MemberService {
    public List<Member> memberList(Page page);
    List<Member> memberList2();
//    public void checkloginAt();
    public Member memberGet(String id);
    public void memberInsert(Member member);
    public void memberUpdate(Member member);
    public void memberRemoveUpdate(String id);
    public void memberDelete(String id);
    public boolean idCheck (String id);
    public int loginPro(String id, String pw);
    public void memberactive(String id);
    public void memberOutside(String id);
    public void change(String id, LocalDateTime createAt);
    public void loginAt(String id);
    public int memberCount(Page page);
    List<Member> memberCreateStats();
}
