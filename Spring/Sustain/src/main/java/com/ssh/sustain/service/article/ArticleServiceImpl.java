package com.ssh.sustain.service.article;

import com.ssh.sustain.dto.article.SaveArticle;
import com.ssh.sustain.mapper.article.ArticleMapper;
import io.jsonwebtoken.lang.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ArticleServiceImpl implements ArticleService {

    private final ArticleMapper articleMapper;

    @Autowired
    public ArticleServiceImpl(ArticleMapper articleMapper) {
        this.articleMapper = articleMapper;
    }

    @Transactional
    @Override
    public SaveArticle save(SaveArticle saveArticle) {
        Assert.notNull(saveArticle.getBo_id(), "Board Id is can't be null");
        Assert.notNull(saveArticle.getTag_id(), "Tag Id is can't be null");
        articleMapper.save(saveArticle);
        articleMapper.saveTagRel(saveArticle.getArt_id(), saveArticle.getTag_id());
        articleMapper.saveBoardRel(saveArticle.getBo_id(), saveArticle.getArt_id());

        return saveArticle;
    }

    @Override
    public void delete(Integer art_id) {
        articleMapper.delete(art_id);
    }
}
