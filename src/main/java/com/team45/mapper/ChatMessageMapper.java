package com.team45.mapper;

import com.team45.entity.ChatMessage;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ChatMessageMapper {
    @Select("SELECT * FROM chatMessage WHERE roomNo=#{roomNo} AND status!='REMOVE'")
    public List<ChatMessage> chatMessageList(int roomNo);

    @Select("SELECT COUNT(*) FROM chatMessage WHERE roomNo=#{roomNo} AND status='UNREAD'")
    public int chatMessageUnread(int roomNo);
    @Select("SELECT * FROM chatMessage ORDER BY chatNo DESC LIMIT 1")
    public ChatMessage chatMessageGetLast();

    @Insert("INSERT INTO chatMessage(type, roomNo, sender, message) VALUES(#{type}, #{roomNo}, #{sender}, #{message})")
    public int chatMessageInsert(ChatMessage chatMessage);

    @Update("UPDATE chatMessage SET status='READ' WHERE chatNo=#{chatNo} AND sender!=#{sender}")
    public int chatMessageReadUpdate(int chatNo, String sender);

    @Update("UPDATE chatMessage SET status='READ' WHERE roomNo=#{roomNo} AND sender!=#{sender}")
    public int chatMessageReadUpdates(int roomNo, String sender);

    @Update("UPDATE chatMessage SET status='REMOVE' WHERE chatNo=#{chatNo}")
    public int chatMessageRemoveUpdate(int chatNo);

    @Delete("DELETE FROM chatMessage WHERE chatNo=#{chatNo}")
    public int chatMessageDelete(int chatNo);
}
