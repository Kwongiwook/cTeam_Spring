package com.ssh.sustain.model.article;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
public class Reply {

    // PK, auto_increment
    private Integer reply_id;

    // FK : User email
    private String reply_writer;

    // FK : Article art_id
    private Integer art_id;

    private String reply_content;

    // Default current_timestamp
    private Date create_date;

    // Default current_timestamp
    private Date modify_date;

}
