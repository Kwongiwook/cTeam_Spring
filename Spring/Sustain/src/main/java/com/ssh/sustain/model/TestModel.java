package com.ssh.sustain.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Setter
@Getter
@Entity
public class TestModel {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String name;

}
