package com.ssh.sustain.mapper.article;

import com.ssh.sustain.dto.article.SaveArticle;
import com.ssh.sustain.dto.article.TagArticleList;
import com.ssh.sustain.dto.paging.Criteria;
import com.ssh.sustain.dto.paging.PageDTO;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.security.RunAs;

import static org.junit.jupiter.api.Assertions.*;

@Log4j2
@RunWith(SpringRunner.class)
@SpringBootTest
class ArticleMapperTest {

    @Autowired
    private ArticleMapper mapper;

    @Test
    void save() {
        SaveArticle saveArticle = SaveArticle.builder()
                .tag_id(1)
                .bo_id(1)
                .art_title("test")
                .art_content("testing")
                .art_writer("gitmiu@gmail.com")
                .build();
        // sequence도 insert all도 없는 Mysql의 슬픔
        mapper.save(saveArticle);
        mapper.saveTagRel(saveArticle.getArt_id(), saveArticle.getTag_id());
        mapper.saveBoardRel(saveArticle.getBo_id(), saveArticle.getArt_id());
    }

    @Test
    void findListByTag() {
        TagArticleList listByTag = mapper.findListByTag(new Criteria(), 1);
        listByTag.getArticleLists().forEach(articleList -> {
            log.info(articleList.getArt_id());
            log.info(articleList.getArt_title());
            log.info(articleList.getArt_writer());
            log.info(articleList.getCreate_date());
        });
    }

    @Test
    void delete() {
        // 현재 FK에 ON DELETE CASCAADE ON UPDATE CASCADE가 걸려있기에 연관된 모든 COLUMN이 동시에 지워짐.
        mapper.delete(9);
    }

    @Test
    void read() {
        mapper.read();
    }

}