package com.ssh.sustain.model.department;

import lombok.Getter;
import lombok.Setter;

import java.sql.Time;

@Getter
@Setter
public class DepartmentInfo {

    // FK : Department dep_id
    private Integer dep_id;

    // FK : User email
    private String dep_manager;

    private String dep_tel;

    private Time dep_open;

    private Time dep_close;

    private String dep_info;

}
