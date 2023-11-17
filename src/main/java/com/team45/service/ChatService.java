package com.team45.service;

import com.team45.entity.ChatMessage;
import com.team45.entity.ChatRoom;

import java.util.List;

public interface ChatService {
    public List<ChatRoom> chatRoomProductList(int pno);
    public ChatRoom chatRoomGetNo(int roomNo);
    public ChatRoom chatRoomInsert(String memId, int pno);
    public int chatRoomBlockUpdate(int roomNo);

    public List<ChatMessage> chatMessageList(int roomNo);
    public ChatMessage chatMessageInsert(ChatMessage chatMessage);
    public int chatMessageReadUpdate(int chatNo, String sender);
    public int chatMessageReadUpdates(int roomNo, String sender);
    public int chatMessageRemoveUpdate(int chatNo);
}
