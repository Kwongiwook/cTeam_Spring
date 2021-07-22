package com.ssh.sustain.model.article;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ArticleTag {

    // FK : Article art_id
    private Integer art_id;

    // FK : Tag tag_id
    private Integer tag_id;

}
