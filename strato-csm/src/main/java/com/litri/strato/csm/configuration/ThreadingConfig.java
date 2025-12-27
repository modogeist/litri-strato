package com.litri.strato.csm.configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;

@Configuration
public class ThreadingConfig {
	
	@Value("${threading.task.count}")
	private int taskCount;
	
	@Value("${threading.async.count}")
	private int asyncCount;
	
	@Bean
	public TaskScheduler taskScheduler() {
		return new ConcurrentTaskScheduler(new ScheduledThreadPoolExecutor(this.taskCount));
	}
	
	@Bean(name = "asyncExecutor")
	public ExecutorService asyncExecutor() {
		// Use fork/join for work stealing
		return new ForkJoinPool(this.asyncCount);
	}

}
