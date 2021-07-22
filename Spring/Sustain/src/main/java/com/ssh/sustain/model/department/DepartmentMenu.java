package com.ssh.sustain.model.department;

import lombok.Getter;
import lombok.Setter;

import java.sql.Time;

@Getter
@Setter
public class DepartmentMenu {

    // PK : (dep_id, menu_id)

    // FK : Department dep_id
    private Integer dep_id;

    // FK : Article art_id
    private Integer menu_id;

    private String menu_title;

    private Time menu_time;

}
