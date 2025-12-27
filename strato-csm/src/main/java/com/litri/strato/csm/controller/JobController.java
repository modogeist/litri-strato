package com.litri.strato.csm.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.litri.strato.dsm.Info;
import com.litri.strato.csm.service.JobService;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@Slf4j
public class JobController {
	
	@Autowired
	JobService jobService;
	
	RestTemplate restTemplate = new RestTemplate();
	
	@RequestMapping(value = "/jobs/**", method = {RequestMethod.GET})
	public Info<Object> handleGet(
			@RequestParam MultiValueMap<String, String> queries,
			@RequestHeader MultiValueMap<String, String> headers,
			HttpServletRequest request) throws Exception {
		Info<Object> responseInfo = this.getResponseInfo(queries, headers, request, null);
		UUID infoId = responseInfo.getId();
		synchronized (responseInfo) {
			if (Objects.nonNull(responseInfo.getLastDate())) {
				this.jobService.fini(infoId);
				this.jobService.remove(infoId);
			}
			
			return responseInfo;
		}
	}
	
	@RequestMapping(value = "/jobs/**", method = {RequestMethod.POST, RequestMethod.PUT, RequestMethod.PATCH, RequestMethod.DELETE})
	public Info<Object> handleMethod(
			@RequestParam MultiValueMap<String, String> queries,
			@RequestHeader MultiValueMap<String, String> headers,
			HttpServletRequest request) throws Exception {
		Info<Object> requestInfo = this.getRequestInfo(request);
		Info<Object> responseInfo = Objects.isNull(requestInfo.getId())
				? this.getResponseInfo(queries, headers, request, requestInfo.getDetail())
				: this.jobService.get(requestInfo.getId());
		UUID infoId = responseInfo.getId();
		synchronized (responseInfo) {
			if (Objects.nonNull(responseInfo.getLastDate())) {
				this.jobService.fini(infoId);
				this.jobService.remove(infoId);
			}
			
			return responseInfo;
		}
	}
	
	private Info<Object> getResponseInfo(
			MultiValueMap<String, String> queries,
			MultiValueMap<String, String> headers,
			HttpServletRequest request,
			Object requestDetail) throws Exception {
		String url = this.getUrl(request);
		HttpMethod method = HttpMethod.valueOf(request.getMethod());
		HttpEntity entity = this.getEntity(headers, requestDetail);
		
		Callable<ResponseEntity> restCallable = () -> this.restTemplate.exchange(url, method, entity, Object.class, queries);
		Callable<Object> jobCallable = () -> this.getResponse(restCallable);
		
		Info<Object> responseInfo = this.jobService.submit(jobCallable);
		synchronized (responseInfo) {
			if (Objects.isNull(responseInfo.getLastDate())) {
				// TODO: Configure wait time.
				responseInfo.wait(5000);
			}
		}
		
		return responseInfo;
	}
	
	private Info<Object> getRequestInfo(HttpServletRequest request) throws Exception {
		String body = request.getReader().lines().collect(Collectors.joining());
		Info<Object> requestInfo = new ObjectMapper().readValue(body, new TypeReference<Info<Object>>() {});
		return requestInfo;
	}
	
	private String getUrl(HttpServletRequest request) throws Exception {
		String url = request.getRequestURL().toString().replaceFirst("/jobs", "");
		return url;
	}
	
	private HttpEntity getEntity(MultiValueMap<String, String> headers, Object body) throws Exception {
		HttpEntity entity = Objects.isNull(body)
				? new HttpEntity(headers)
				: new HttpEntity(body, headers);
		return entity;
	}
	
	private Object getResponse(Callable<ResponseEntity> restCallable) {
		try {
			ResponseEntity responseEntity = restCallable.call();
			// TODO: Handle rest error somehow.
			return Objects.equals(responseEntity.getStatusCode(), HttpStatus.OK)
					? responseEntity.getBody()
					: null;
		} catch (Exception e) {
			log.error("Calling rest service", e);
		}
		
		return null;
	}

}
