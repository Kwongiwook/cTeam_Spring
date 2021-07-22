package com.ssh.sustain.model.department;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Department {

    // PK, auto_increment
    private Integer dep_ip;

    private String dep_city;

    private String dep_district;

    private String dep_address;

    private String dep_name;

}
