package com.team45.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatRoom {
    private Long roomNo;
    private String memId;
    private Long pno;
    private String status = "ON";
    private String seller;
}
