package com.ssh.sustain.dto.article;

import lombok.Builder;
import lombok.Getter;

@Getter
public class SaveArticle {

    private Integer art_id;

    private Integer bo_id;

    private Integer tag_id;

    private String art_title;

    private String art_content;

    private String art_writer;

    @Builder
    public SaveArticle(Integer bo_id, Integer tag_id, String art_title, String art_content, String art_writer) {
        this.art_id = art_id;
        this.bo_id = bo_id;
        this.tag_id = tag_id;
        this.art_title = art_title;
        this.art_content = art_content;
        this.art_writer = art_writer;
    }


}
