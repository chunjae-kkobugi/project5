package com.team45.mapper;

import com.team45.entity.Member;

import java.sql.Timestamp;
import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long>{
        List<Member> timebefore(String status, Timestamp thresholdTime);
}
