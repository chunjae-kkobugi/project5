package com.team45.mapper;

import com.team45.entity.Member;

import java.time.LocalDateTime;
import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long>{
        List<Member> findByStatusAndDormantStartTimeBefore(String status, LocalDateTime thresholdTime);
}
