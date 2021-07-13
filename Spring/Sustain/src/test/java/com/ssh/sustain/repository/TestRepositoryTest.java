package com.ssh.sustain.repository;

import com.ssh.sustain.model.TestModel;
import lombok.extern.log4j.Log4j2;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Log4j2
@RunWith(SpringRunner.class)
@DataJpaTest
public class TestRepositoryTest {

    @Autowired
    TestRepository repository;

    @Test
    public void save() {
        // given
        TestModel testModel = new TestModel();
        testModel.setName("testing now");
        assertNull(testModel.getId());

        // when
        TestModel save = repository.save(testModel);

        // then
        assertNotNull(save.getId());
    }

    @Test
    public void findByNameContainsIgnoreCase() {
        TestModel testModel = new TestModel();
        testModel.setName("testing now");
        assertNull(testModel.getId());

        TestModel save = repository.save(testModel);

        assertNotNull(save.getId());

        List<TestModel> test = repository.findByNameContainsIgnoreCase("TeSt");
        test.forEach(log::info);
    }

}