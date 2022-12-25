package com.example.samplepipeline;

import bootiful.asciidoctor.DocumentsPublishedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.batch.JobExecutionEvent;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.eclipse.jgit.transport.*;

import java.util.List;

@Slf4j
@SpringBootApplication
public class SamplePipelineApplication {

	public static void main(String[] args) {
		SpringApplication.run(SamplePipelineApplication.class, args);
	}

	@Bean
	UsernamePasswordCredentialsProvider usernamePasswordCredentialsProvider(@Value("${GIT_USERNAME}") String user,
			@Value("${GIT_PASSWORD}") String pw) {
		return new UsernamePasswordCredentialsProvider(user, pw);
	}

	@Bean
	ApplicationListener<DocumentsPublishedEvent> documentsPublishedListener() {
		return event -> {
			log.info("Ding! The files are ready!");
			event.getSource().forEach((key, value) -> log.info(key + '=' + value));
		};
	}

	@Bean
	ApplicationListener<ApplicationReadyEvent> applicationReadyListener(Environment environment) {
		return event -> List.of("pipeline.job.root", "publication.root", "publication.code").forEach(
				propertyName -> log.info("test " + propertyName + "=" + environment.getProperty(propertyName)));
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
