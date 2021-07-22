package com.ssh.sustain.model.article;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
public class Article {

    // PK, auto_increment
    private Integer art_id;

    private String art_title;

    private String art_content;

    // FK : User email
    private String art_writer;

    // Default current_timestamp
    private Date create_date;

    // Default current_timestamp
    private Date modify_date;

}
