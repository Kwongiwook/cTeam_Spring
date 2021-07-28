package com.ssh.sustain.dto.paging;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Criteria {

    private int page;

    private int amount;

    public Criteria() {
        this(0, 9);
    }

    public Criteria(int page, int amount) {
        this.page = page;
        this.amount = amount;
    }
}
