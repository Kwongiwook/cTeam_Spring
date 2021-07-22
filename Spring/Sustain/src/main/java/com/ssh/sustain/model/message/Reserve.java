package com.ssh.sustain.model.message;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
public class Reserve {

    // PK, auto_increment
    private Integer res_id;

    // FK : Department dep_id
    private Integer dep_id;

    // FK : User email
    private String res_client;

    private Date res_date;

    // FK : Department_menu menu_id
    private Integer res_menu;

    private String res_detail;

    // Default current_timestamp
    private Date create_date;

    // Default current_timestamp
    private Date modify_date;

}
