package com.ssh.sustain.mapper.article;

import com.ssh.sustain.dto.article.ArticleDTO;
import com.ssh.sustain.dto.article.SaveArticle;
import com.ssh.sustain.dto.article.TagArticleList;
import com.ssh.sustain.dto.paging.Criteria;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ArticleMapper {

    // 한 번의 save는 반드시 saveTagRel과 saveBoardRel을 동반해야 한다.
    void save(SaveArticle saveArticle);

    void saveTagRel(Integer art_id, Integer tag_id);

    void saveBoardRel(Integer bo_id, Integer art_id);

    List<ArticleDTO> read();

    TagArticleList findListByTag(Criteria criteria, Integer tag_id);

    void delete(Integer art_id);

}
