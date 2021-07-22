package com.ssh.sustain.model.message;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
public class Message {

    // PK, auto_increment
    private Integer msg_id;

    // FK : User email
    private String msg_sender;

    // FK : User email
    private String msg_target;

    private String msg_title;

    private String msg_content;

    // Default current_timestamp
    private Date create_date;

    // Default current_timestamp
    private Date open_date;

}
