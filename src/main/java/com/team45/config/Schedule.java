package com.team45.config;


import com.team45.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


@Component
public class Schedule {

    private MemberService memberService;

        @Autowired
        public Schedule(MemberService memberService) {
            this.memberService = memberService;
        }

        @Scheduled(cron = "0 0 0 * * *") // 매일 자정에 실행
        public void scheduleDormantAccountCheck() {
            memberService.statuschange();
        }
}

