package com.litri.strato.csm.controller;

import com.litri.strato.csm.service.ForwardService;
import jakarta.servlet.http.HttpServletRequest;
import java.net.URI;
import java.util.List;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@Slf4j
public class ForwardController {
	
	@Autowired
	ForwardService forwardService;
	
	RestTemplate restTemplate = new RestTemplate();

	@RequestMapping(value = "/forwards/{serviceInfoId}/**")
	public ResponseEntity<?> forward(
			HttpServletRequest request,
			@PathVariable UUID serviceInfoId,
			@RequestHeader HttpHeaders headers,
			@RequestBody(required = false) byte[] body
	) throws Exception {
		URI forwardUri = this.getForwardUri(serviceInfoId, request);
		HttpMethod forwardMethod = this.getForwardMethod(request);
		HttpHeaders forwardHeaders = this.filterHeaders(headers, List.of(HttpHeaders.HOST));
		HttpEntity<byte[]> forwardEntity = new HttpEntity<>(body, forwardHeaders);

		ResponseEntity<byte[]> response = restTemplate.exchange(forwardUri, forwardMethod, forwardEntity, byte[].class);
		HttpHeaders returnHeaders = this.filterHeaders(response.getHeaders(), List.of(HttpHeaders.TRANSFER_ENCODING));

		return ResponseEntity
				.status(response.getStatusCode())
				.headers(returnHeaders)
				.body(response.getBody());
	}
	
	private URI getForwardUri(UUID serviceInfoId, HttpServletRequest request) throws Exception {
		String baseUrl = forwardService.getProviderUrl(serviceInfoId);
		if (baseUrl == null) {
			throw new IllegalArgumentException("No provider URL found for serviceInfoId: " + serviceInfoId);
		}

		String requestPath = request.getRequestURI()
				.replaceFirst("^/forwards/" + serviceInfoId.toString(), "");

		String query = request.getQueryString();
		String fullUrl = baseUrl + requestPath + (query != null ? "?" + query : "");
		return URI.create(fullUrl);
	}
	
	private HttpMethod getForwardMethod(HttpServletRequest request) {
		String methodOverride = request.getHeader("X-HTTP-Method-Override");
		return HttpMethod.valueOf(methodOverride != null
				? methodOverride
				: request.getMethod());
	}

	private HttpHeaders filterHeaders(HttpHeaders headers, List<String> excludes) {
		HttpHeaders newHeaders = new HttpHeaders();
		headers.forEach((key, value) -> {
			if (!excludes.contains(key)) {
				newHeaders.put(key, value);
			}
		});
		return newHeaders;
	}

}
