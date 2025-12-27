package com.litri.strato.csm.configuration;

import com.litri.strato.csm.service.JobService;
import java.util.concurrent.ExecutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JobConfig {
	
	@Value("${job.timeout}")
	private long jobTimeout;
	
	@Autowired
	@Qualifier("asyncExecutor")
	ExecutorService asyncExecutor;
	
	@Bean
	public JobService jobService() {
		return new JobService(this.asyncExecutor, this.jobTimeout);
	}
	
}
