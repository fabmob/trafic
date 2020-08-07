package com.datex.batch.tasklet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@Component
public class LogTasklet implements Tasklet {
    @Autowired
    public DataSource dataSource;

    private static final Logger log = LoggerFactory.getLogger(LogTasklet.class);

    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {

        log.info("############# LOG TASKLET BEGIN  ############");
        ResultSet rs = null;
        try (Connection con = dataSource.getConnection(); Statement stmt = con.createStatement();) {
            rs = stmt.executeQuery("select count(*) as count from trafic_time");
            while (rs.next()) {
                log.info("Count Row =" + rs.getInt("count"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        log.info("############# LOG TASKLET END    ############");
        return null;
    }
}
