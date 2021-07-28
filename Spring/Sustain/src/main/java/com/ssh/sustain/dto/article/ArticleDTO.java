package com.ssh.sustain.dto.article;

import lombok.Getter;

import java.sql.Date;

/**
 * 사용자들이 열람할 Article의 DTO.
 * GET : /(art_id)으로 받아보게 되는 데이터다.
 */
@Getter
public class ArticleDTO {

    private Integer art_id;

    private String art_title;

    private String art_content;

    private String art_writer;

    private Date create_date;

}
