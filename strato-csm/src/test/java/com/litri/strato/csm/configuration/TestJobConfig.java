package com.litri.strato.csm.configuration;

import com.litri.strato.common.concurrency.DirectExecutorService;
import com.litri.strato.csm.service.JobService;
import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class TestJobConfig {
	
	@Bean
	public JobService jobService() {
		return Mockito.spy(new JobService(new DirectExecutorService(), 1000));
	}
	
}
