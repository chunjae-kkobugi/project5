package com.team45.mapper;

import com.team45.entity.FileData;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface FileDataMapper {
    @Select("SELECT * FROM fileData WHERE tableName=#{tableName} AND boardNo=#{boardNo}")
    public List<FileData> fileDataBoardList(String tableName, int boardNo);

    @Select("SELECT * FROM fileData WHERE fileNo=#{fileNo}")
    public FileData fileDataGet(int fileNo);

    @Insert("INSERT INTO fileData (tableName, boardNo, originName, saveName, savePath, fileType, authority) VALUES (#{tableName}, #{boardNo}, #{originName}, #{saveName}, #{savePath}, #{fileType}, #{authority})")
    public int fileDataInsert(FileData fileData);
    @Select("SELECT fileNo FROM fileData ORDER BY fileNo DESC LIMIT 1")
    public int fileDataGetLast();

    @Update("UPDATE fileData SET tableName=#{tableName}, boardNo=#{boardNo} WHERE fileNo=#{fileNo}")
    public int fileDataUpdate(FileData fileData);

    @Update("UPDATE fileData SET authority=#{authority} WHERE fileNo=#{fileNo}")
    public int fileDataAuthorityUpdate(String authority, int fileNo);
    @Update("UPDATE fileData SET authority='REMOVE' WHERE fileNo=#{fileNo}")
    public int fileDataRemoveUpdate(int fileNo);
    @Update("UPDATE fileData SET authority='REMOVE' WHERE boardNo=#{boardNo}")
    public int fileDataBoardRemoveUpdate(int boardNo);
    @Delete("DELETE FROM fileData WHERE fileNo=#{fileNo}")
    public int fileDataDelete(int fileNo);
}
