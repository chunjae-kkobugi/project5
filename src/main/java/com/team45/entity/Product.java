package com.team45.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    private Long pno;
    private String pname;
    private String content;
    private String category;
    private String seller;
    private int price = 0;
    private String proaddr;
    private Long image;
    private Date createAt;
    private Date baseAt;
    private String status = "SALE";
    private int visited = 0;
}
