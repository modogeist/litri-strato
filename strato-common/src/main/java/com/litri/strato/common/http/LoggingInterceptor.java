package com.litri.strato.common.http;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

@Slf4j
public class LoggingInterceptor implements ClientHttpRequestInterceptor {
	
	private ObjectMapper objectMapper = new ObjectMapper();

	@Override
	public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
		traceRequest(request, body);
		ClientHttpResponse response = execution.execute(request, body);
		traceResponse(response);
		return response;
	}

	private void traceRequest(HttpRequest request, byte[] body) throws IOException {
		Map<String, Object> map = Map.of(
				"uri", request.getURI(),
				"method", request.getMethod(),
				"headers", request.getHeaders(),
				"body", new String(body, "UTF-8")
		);
		log.trace(this.objectMapper.writeValueAsString(map));
	}

	private void traceResponse(ClientHttpResponse response) throws IOException {
		StringBuilder stringBuilder = new StringBuilder();
		try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(response.getBody(), "UTF-8"))) {
			String line = bufferedReader.readLine();
			while (line != null) {
				stringBuilder.append(line);
				stringBuilder.append('\n');
				line = bufferedReader.readLine();
			}
			Map<String, Object> map = Map.of(
					"statusCode", response.getStatusCode(),
					"statusText", response.getStatusText(),
					"headers", response.getHeaders(),
					"body", stringBuilder.toString()
			);
			log.trace(this.objectMapper.writeValueAsString(map));
		}
	}

}