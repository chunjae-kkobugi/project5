package com.team45.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Recomment {
    private int no;
    private String mem_id;
    private String comment;
}
