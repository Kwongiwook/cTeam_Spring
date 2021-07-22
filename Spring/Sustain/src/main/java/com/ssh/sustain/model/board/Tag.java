package com.ssh.sustain.model.board;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Tag {

    // PK, auto_increment
    private Integer tag_id;

    private String tag_name;

    private String tag_info;

}
