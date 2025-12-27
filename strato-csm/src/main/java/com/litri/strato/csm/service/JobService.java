package com.litri.strato.csm.service;

import com.litri.strato.dsm.Info;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JobService {
	
	ExecutorService executor;
	InfoService<Object> infoService;
	
	public JobService(ExecutorService executor, long timeout) {
		this.executor = executor;
		this.infoService = new InfoService(timeout);
	}
	
	public Info<Object> submit(final Callable callable) {
		Info<Object> responseInfo = this.init(null);
		this.executor.submit(() -> {
			try {
				Object result = callable.call();
				synchronized (responseInfo) {
					this.infoService.update(responseInfo.getId(), result);
					responseInfo.notify();
				}
			} catch (Exception e) {
				log.error("Running job {}", responseInfo.getId(), e);
			}
		});
		return responseInfo;
	}
	
	public Info<Object> init(Object detail) {
		return this.infoService.init(detail);
	}
	
	public Info<Object> get(UUID id) {
		return this.infoService.get(id);
	}
	
	public Info<Object> fini(UUID id) {
		return this.infoService.fini(id);
	}
	
	public Info<Object> remove(UUID id) {
		return this.infoService.remove(id);
	}
	
}
