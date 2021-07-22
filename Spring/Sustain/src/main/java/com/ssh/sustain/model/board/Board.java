package com.ssh.sustain.model.board;

import lombok.Getter;
import lombok.Setter;

/**
 * 게시판을 구분하기 위한 테이블
 */
@Getter
@Setter
public class Board {

    // PK, auto_increment
    private Integer bo_id;

    private String bo_name;

    private String bo_info;

}
