package com.team45.service;

import com.team45.entity.Member;
import com.team45.util.Page;

import java.util.List;

public interface MemberService {
    public List<Member> memberList(Page page);
    public int memberCount(Page page);
    public Member memberGet(String id);
    public int memberInsert(Member member);
    public int memberUpdate(Member member);
    public int memberRemoveUpdate(String id);
    public int memberDelete(String id);
}
