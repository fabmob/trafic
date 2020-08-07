package com.datex.batch;

import com.datex.batch.model.Datex;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@SpringBootApplication
@EnableScheduling
public class BatchApplication {

    @Autowired
    JobLauncher jobLauncher;

    @Autowired
    @Qualifier("readDatexJob")
    Job job;

    @Autowired
    @Qualifier("logTaskletJob")
    Job jobLog;

    @Autowired
    @Qualifier("readMeteoJob")
    Job jobMeteo;

    @Autowired
    @Qualifier("readTelraamJob")
    Job jobTelraam;


    public static void main(String[] args) {
        SpringApplication.run(BatchApplication.class, args);
    }



    @Scheduled(cron = "0 */5 * * * ?")
    public void perform() throws Exception {
        JobParameters params = null;
        String tmsp = String.valueOf(System.currentTimeMillis());
        for (String url : Datex.RESOURCE_MAP.keySet()) {
            params = new JobParametersBuilder()
                    .addString("JobID", tmsp + url.substring(url.length() - 2))
                    .addString("targetUrl", url)
                    .addString("targetFile", "out/"+Datex.RESOURCE_MAP.get(url))
                    .toJobParameters();
            jobLauncher.run(job, params);
        }

    }

    @Scheduled(cron = "0 */5 * * * ?")
    public void performMeteo() throws Exception {
        JobParameters params = null;
        String tmsp = String.valueOf(System.currentTimeMillis());
        params = new JobParametersBuilder()
                .addString("JobID", tmsp)
                .toJobParameters();
        jobLauncher.run(jobMeteo, params);
    }

    @Scheduled(cron = "0 */1 * * * ?")
    public void performTelraam() throws Exception {
        JobParameters params = null;
        String tmsp = String.valueOf(System.currentTimeMillis());
        params = new JobParametersBuilder()
                .addString("JobID", tmsp)
                .toJobParameters();
        jobLauncher.run(jobTelraam, params);
    }

    @Scheduled(cron = "0 */5 * * * ?")
    public void perform2() throws Exception {
        JobParameters params = null;
        String tmsp = String.valueOf(System.currentTimeMillis());
        params = new JobParametersBuilder()
                .addString("JobID", tmsp + "log")
                .toJobParameters();
        jobLauncher.run(jobLog, params);

    }

}
