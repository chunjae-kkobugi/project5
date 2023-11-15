package com.team45.service;

import com.team45.entity.Member;
import com.team45.mapper.MemberMapper;
import com.team45.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberServiceImpl implements MemberService{
    @Autowired
    private MemberMapper memberMapper;

    @Override
    public List<Member> memberList(Page page) {
        return memberMapper.memberList(page);
    }
    @Override
    public int memberCount(Page page) {
        return memberMapper.memberCount(page);
    }

    @Override
    public Member memberGet(String id) {
        return memberMapper.memberGet(id);
    }

    @Override
    public int memberInsert(Member member) {
        return memberMapper.memberInsert(member);
    }

    @Override
    public int memberUpdate(Member member) {
        return memberMapper.memberUpdate(member);
    }

    @Override
    public int memberRemoveUpdate(String id) {
        return memberMapper.memberRemoveUpdate(id);
    }

    @Override
    public int memberDelete(String id) {
        return memberMapper.memberDelete(id);
    }
}
