package com.ssh.sustain.persistence;

import lombok.Setter;
import lombok.extern.log4j.Log4j;
import org.junit.Test;
import org.junit.internal.runners.statements.Fail;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"file:src/main/webapp/WEB-INF/spring/root-context.xml"})
@Log4j
public class HikariTest {

    @Setter(onMethod_ = {@Autowired})
    private DataSource source;

    @Test
    public void testConnection() {
        try {
            Connection connection = source.getConnection();
            log.info(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
