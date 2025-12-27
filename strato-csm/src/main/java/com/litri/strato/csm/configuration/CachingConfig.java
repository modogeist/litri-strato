package com.litri.strato.csm.configuration;

import com.google.common.cache.CacheBuilder;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CachingConfig {

	private static long VERY_SHORT_TIME = 120;
	private static long SHORT_TIME = 300;
	private static long MEDIUM_TIME = 900;
	private static long LONG_TIME = 1800;
	private static long VERY_LONG_TIME = 3600;

	@Bean
	public SimpleCacheManager cacheManager() {
		SimpleCacheManager cacheManager = new SimpleCacheManager();
		cacheManager.setCaches(Arrays.asList(
				new ConcurrentMapCache("providers", CacheBuilder.newBuilder().expireAfterAccess(VERY_LONG_TIME, TimeUnit.MINUTES).build().asMap(), false),
				new ConcurrentMapCache("schemas", CacheBuilder.newBuilder().expireAfterAccess(VERY_LONG_TIME, TimeUnit.MINUTES).build().asMap(), false)
		));
		return cacheManager;
	}

}
