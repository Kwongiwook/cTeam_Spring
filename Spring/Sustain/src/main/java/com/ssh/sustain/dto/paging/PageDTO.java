package com.ssh.sustain.dto.paging;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class PageDTO {

    private final int startPage;

    private int endPage;

    private final boolean prev;

    private final boolean next;

    private final int total;

    private final Criteria criteria;

    public PageDTO(int total, Criteria criteria) {
        this.total = total;
        this.criteria = criteria;

        this.endPage = (int) (Math.ceil(criteria.getPage() / 10.0)) * 10;
        this.startPage = this.endPage - (criteria.getAmount() - 1);

        int count = (int) (Math.ceil((total * 1.0) / criteria.getAmount()));
        if (count < this.endPage) {
            this.endPage = count;
        }
        this.prev = this.startPage > 1;
        this.next = this.endPage < count;
    }
}
