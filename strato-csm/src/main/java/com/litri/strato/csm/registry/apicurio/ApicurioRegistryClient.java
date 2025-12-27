package com.litri.strato.csm.registry.apicurio;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.litri.strato.common.http.RestClient;
import com.litri.strato.dsm.SchemaId;
import com.litri.strato.csm.registry.RegistryClient;
import java.net.URI;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.ToString;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Builder
public class ApicurioRegistryClient<T> implements RegistryClient<T> {
	
	@NonNull
	private String url;
	@NonNull
	private Integer connectTimeout;
	@NonNull
	private Integer readTimeout;
	@NonNull
	private Map<String, String> headers;
	
	@NonNull
	private Class<T> responseType;
	
	private List<String> labels;
	private Map<String, Object> properties;
	
	private RestTemplate restTemplate;

	@Override
	public List<SchemaId> getSchemaIds() throws Exception {
		// TODO: Remove when proper Apicurio endpoints exposed.
		if (true) {
			return List.of(SchemaId.builder()
					.groupId("MEMBRAIN")
					.artifactId("000001")
					.version("1")
					.build());
		}
		
		List<Artifact> artifacts = this.getArtifacts(this.labels, this.properties);
		
		Map<Artifact, List<Version>> artifactVersions = new HashMap();
		for (Artifact artifact : artifacts) {
			artifactVersions.put(artifact, this.getVersions(artifact.getGroupId(), artifact.getId()));
		}
		
		List<SchemaId> schemaIds = new ArrayList();
		artifactVersions.forEach((artifact, versions) ->
				versions.stream()
						.forEach(version -> schemaIds.add(SchemaId.builder()
								.groupId(artifact.getGroupId())
								.artifactId(artifact.getId())
								.version(version.getVersion())
								.build())));
		
		return schemaIds;
	}
	
	@Override
	public List<T> getSchemas() throws Exception {
		List<SchemaId> schemaIds = this.getSchemaIds();
		List<T> schemas = new ArrayList();
		for (SchemaId schemaId : schemaIds) {
			schemas.add(this.getSchema(schemaId));
		}
		return schemas;
	}
	
	@Override
	public T getSchema(SchemaId schemaId) throws Exception {
		URI uri = this.getUriSchema(this.url, schemaId.getGroupId(), schemaId.getArtifactId(), schemaId.getVersion());
		return this.getRestTemplate()
				.exchange(uri, HttpMethod.GET, this.getHttpEntity(), this.responseType).getBody();
	}

	@Override
	public void putSchema(SchemaId schemaId, T schema) throws Exception {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public void removeSchema(SchemaId schemaId) throws Exception {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public void removeSchemas() throws Exception {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	private List<Artifact> getArtifacts(List<String> labels, Map<String, Object> properties)
			throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
		URI uri = this.getUriArtifacts(this.url, labels, properties);
		return this.getRestTemplate()
				.exchange(uri, HttpMethod.GET, this.getHttpEntity(), new ParameterizedTypeReference<List<Artifact>>() {}).getBody();
	}
	
	private List<Version> getVersions(String groupId, String artifactId)
			throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
		URI uri = this.getUriVersions(this.url, groupId, artifactId);
		return this.getRestTemplate()
				.exchange(uri, HttpMethod.GET, this.getHttpEntity(), new ParameterizedTypeReference<List<Version>>() {}).getBody();
	}
	
	private URI getUriArtifacts(String url, List<String> labels, Map<String, Object> properties) {
		UriComponentsBuilder uriBuilder = UriComponentsBuilder
				.fromHttpUrl(url)
				.pathSegment("search", "artifacts");
		if (Objects.nonNull(labels) && !labels.isEmpty()) {
			uriBuilder = uriBuilder.queryParam("labels", labels);
		}
		if (Objects.nonNull(properties) && !properties.isEmpty()) {
			uriBuilder = uriBuilder.queryParam("properties", properties);
		}
		return uriBuilder.build().toUri();
	}
	
	private URI getUriVersions(String url, String groupId, String artifactId) {
		UriComponentsBuilder uriBuilder = UriComponentsBuilder
				.fromHttpUrl(url)
				.pathSegment("groups", groupId, "artifacts", artifactId, "versions");
		return uriBuilder.build().toUri();
	}
	
	private URI getUriSchema(String url, String groupId, String artifactId, String version) {
		UriComponentsBuilder uriBuilder = UriComponentsBuilder
				.fromHttpUrl(url)
				.pathSegment("schema", groupId, artifactId, version);
		return uriBuilder.build().toUri();
	}

	private HttpEntity getHttpEntity() {
		return this.getHttpEntity(this.headers);
	}

	private HttpEntity getHttpEntity(Map<String, String> headerParams) {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		headerParams.forEach(httpHeaders::add);
		return new HttpEntity(httpHeaders);
	}

	private synchronized RestTemplate getRestTemplate()
			throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
		if (this.restTemplate == null) {
			this.restTemplate = RestClient.templateBuilder().build();
			this.restTemplate.getMessageConverters().add(0, createHttpMessageConverter());
		}
		return this.restTemplate;
	}

	private HttpMessageConverter createHttpMessageConverter() {
		MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
		converter.setObjectMapper(new ObjectMapper());
		return converter;
	}
	
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	@ToString
	@Getter
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class Artifact {
		private String groupId;
		private String id;
		private List<String> labels;
		private Map<String, String> properties;
	}
	
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	@ToString
	@Getter
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class Version {
		private String version;
	}
	
}
