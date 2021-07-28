package com.ssh.sustain.dto.article;

import lombok.Getter;

import java.sql.Date;
import java.util.List;

/**
 * 특정한 게시판(Board)의 게시글 리스트를 담을 DTO
 */
@Getter
public class BoardArticleList {

    private Integer bo_id;

    private List<ArticleList> articleLists;

}
