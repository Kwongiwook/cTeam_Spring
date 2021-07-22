package com.ssh.sustain.model.article;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
public class AttachFile {

    // FK : Article art_id
    private Integer art_id;

    // Default current_timestamp
    private Date file_id;

    private String file_name;

    private String file_path;

    private String file_origin;

    private Integer file_size;

}
