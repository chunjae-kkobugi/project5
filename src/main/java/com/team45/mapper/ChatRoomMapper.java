package com.team45.mapper;

import com.team45.entity.ChatRoom;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ChatRoomMapper {
    @Select("SELECT * FROM chatRoom")
    public List<ChatRoom> chatRoomList();

    @Select("SELECT * FROM chatRoom WHERE pno=#{pno} AND status!='BLOCK'")
    public List<ChatRoom> chatRoomProductList(int pno);

    @Select("SELECT * FROM chatRoom where roomNo=#{roomNo}")
    public ChatRoom chatRoomGet(int roomNo);

    @Select("SELECT * FROM chatRoom WHERE pno=#{pno} AND userId=#{userId}")
    public ChatRoom chatRoomGetId(int pno, String userId);

    @Select("SELECT COUNT(*) FROM chatRoom WHERE userId=#{userId} AND pno=#{pno}")
    public int chatRoomGetUnique(String userId, int pno);

    @Insert("INSERT INTO chatRoom(userId, pno) VALUES(#{userId}, #{pno})")
    public void chatRoomInsert(String userId, int pno);
    @Update("UPDATE chatRoom SET status='BLOCK' WHERE roomNo=#{roomNo}")
    public int chatRoomBlockUpdate(int roomNo);

    @Delete("DELETE FROM chatroom WHERE roomNo=#{roomNo}")
    public int chatRoomDelete(int roomNo);
}
