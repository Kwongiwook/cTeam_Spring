package com.ssh.sustain.dto.article;

import lombok.Getter;

import java.sql.Date;
import java.util.List;

/***
 * 특정한 태그를 포함한 게시글 리스트를 담는 DTO.
 */
@Getter
public class TagArticleList {

    private Integer tag_id;

    private List<ArticleList> articleLists;

}
