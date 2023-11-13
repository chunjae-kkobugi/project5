package com.team45.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileData {
    private int fileNo;
    private String tableName;
    private int columnNo;
    private String originName;
    private String saveName;
    private String savePath;
    private String fileType;
    private String status = "ACTIVE";
}
