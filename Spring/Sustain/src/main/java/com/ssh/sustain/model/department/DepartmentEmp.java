package com.ssh.sustain.model.department;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
public class DepartmentEmp {

    // FK : Department dpe_id
    private Integer dep_id;

    // FK : User email
    private String emp_id;

    // Default current_timestamp
    private Date create_date;

}
