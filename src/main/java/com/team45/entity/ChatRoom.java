package com.team45.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatRoom {
    private int roomNo;
    private String userId;
    private int pno;
    private String status = "ON";
}
