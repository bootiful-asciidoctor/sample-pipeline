package com.example.samplepipeline;

import bootiful.asciidoctor.DocumentsPublishedEvent;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.batch.JobExecutionEvent;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

@Log4j2
@SpringBootApplication
public class SamplePipelineApplication {

    public static void main(String[] args) {
        SpringApplication.run(SamplePipelineApplication.class, args);
    }

    @Bean
    ApplicationListener<DocumentsPublishedEvent> documentsPublishedListener() {
        return event -> {
            log.info("Ding! The files are ready!");
            for (var e : event.getSource().entrySet())
                log.info(e.getKey() + '=' + e.getValue());
        };
    }

    @Bean
    ApplicationListener<ApplicationReadyEvent> applicationReadyListener(Environment environment) {
        return event -> {
            for (var propertyName : new String[]{"pipeline.job.root", "publication.root", "publication.code"}) {
                log.info("test: " + propertyName + "=" + environment.getProperty(propertyName));
            }
        };
    }

    @Bean
    ApplicationListener<JobExecutionEvent> batchJobListener() {
        return event -> {
            var jobExecution = event.getJobExecution();
            var createTime = jobExecution.getCreateTime();
            var endTime = jobExecution.getEndTime();
            var jobName = jobExecution.getJobInstance().getJobName();
            log.info("job (" + jobName + ") start time: " + createTime.toString());
            log.info("job (" + jobName + ") stop time: " + endTime.toString());
        };
    }

}
