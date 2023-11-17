package com.team45.service;

import com.team45.entity.ChatMessage;
import com.team45.mapper.ChatMessageMapper;
import com.team45.mapper.ChatRoomMapper;
import com.team45.entity.ChatRoom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ChatServiceImpl implements ChatService {
    @Autowired
    ChatRoomMapper roomMapper;
    @Autowired
    ChatMessageMapper chatMapper;

    @Override
    public List<ChatRoom> chatRoomProductList(int pno) {
        return roomMapper.chatRoomProductList(pno);
    }

    @Override
    public ChatRoom chatRoomGetNo(int roomNo) {
        return roomMapper.chatRoomGet(roomNo);
    }

    @Override
    public ChatRoom chatRoomInsert(String memId, int pno) {
        if(roomMapper.chatRoomGetUnique(memId, pno)<1){
            roomMapper.chatRoomInsert(memId, pno);
        }

        return roomMapper.chatRoomGetId(pno, memId);
    }

    @Override
    public int chatRoomBlockUpdate(int roomNo) {
        return roomMapper.chatRoomBlockUpdate(roomNo);
    }

    @Override
    public List<ChatMessage> chatMessageList(int roomNo) {
        return chatMapper.chatMessageList(roomNo);
    }

    @Override
    public ChatMessage chatMessageInsert(ChatMessage chatMessage) {
        int roomNo = chatMessage.getRoomNo();
        ChatRoom room = roomMapper.chatRoomGet(roomNo);
        if(room.getStatus().equals("BLOCK")){
            return null; // 차단된 경우에는 메시지 전송하지 않음.
        }
        chatMapper.chatMessageInsert(chatMessage);
        return chatMapper.chatMessageGetLast();
    }

    @Override
    public int chatMessageReadUpdate(int chatNo, String sender) {
        return chatMapper.chatMessageReadUpdate(chatNo, sender);
    }

    @Override
    public int chatMessageReadUpdates(int roomNo, String sender) {
        return chatMapper.chatMessageReadUpdates(roomNo, sender);
    }

    @Override
    public int chatMessageRemoveUpdate(int chatNo) {
        return chatMapper.chatMessageRemoveUpdate(chatNo);
    }
}
