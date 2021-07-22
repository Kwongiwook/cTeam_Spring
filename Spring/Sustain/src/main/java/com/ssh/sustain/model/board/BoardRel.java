package com.ssh.sustain.model.board;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoardRel {

    // FK : Board bo_id
    private Integer bo_id;

    // FK : Article art_id
    private Integer art_id;

}
