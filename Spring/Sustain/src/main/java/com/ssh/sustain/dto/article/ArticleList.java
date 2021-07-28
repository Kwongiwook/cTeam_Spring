package com.ssh.sustain.dto.article;

import lombok.Getter;

import java.sql.Date;

@Getter
public class ArticleList {

    private Integer art_id;

    private String art_title;

    private String art_writer;

    private Date create_date;

}
