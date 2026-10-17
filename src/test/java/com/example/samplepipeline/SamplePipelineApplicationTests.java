package com.example.samplepipeline;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = { "spring.batch.job.enabled=false", "publication.mobi.enabled=false", "GIT_USERNAME=test",
		"GIT_PASSWORD=test", "AWS_ACCESS_KEY_ID=test", "AWS_SECRET_ACCESS_KEY=test", "AWS_REGION=us-east-1" })
class SamplePipelineApplicationTests {

	@Test
	void contextLoads() {
	}

}
