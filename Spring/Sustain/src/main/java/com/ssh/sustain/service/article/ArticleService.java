package com.ssh.sustain.service.article;

import com.ssh.sustain.dto.article.SaveArticle;

public interface ArticleService {

    SaveArticle save(SaveArticle saveArticle);

    void delete(Integer art_id);

}
