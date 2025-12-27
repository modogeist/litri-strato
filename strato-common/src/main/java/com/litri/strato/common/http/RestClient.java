package com.litri.strato.common.http;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import org.apache.commons.lang3.StringUtils;
import org.apache.hc.client5.http.auth.AuthScope;
import org.apache.hc.client5.http.auth.UsernamePasswordCredentials;
import org.apache.hc.client5.http.impl.DefaultClientConnectionReuseStrategy;
import org.apache.hc.client5.http.impl.auth.BasicCredentialsProvider;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManagerBuilder;
import org.apache.hc.client5.http.io.HttpClientConnectionManager;
import org.apache.hc.client5.http.ssl.DefaultClientTlsStrategy;
import org.apache.hc.client5.http.ssl.NoopHostnameVerifier;
import org.apache.hc.core5.http.HttpHost;
import org.apache.hc.core5.ssl.SSLContexts;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

public class RestClient {

	private static final int CONNECT_TIMEOUT = 15000;
	private static final int READ_TIMEOUT = 15000;

	public static HttpClientBuilder clientBuilder() throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
		SSLContext sslContext = SSLContexts.custom().loadTrustMaterial(null, (x509Certificates, s) -> true).build();
		HostnameVerifier hostnameVerifier = new NoopHostnameVerifier();
		HttpClientConnectionManager connectionManager = PoolingHttpClientConnectionManagerBuilder
				.create()
				.setTlsSocketStrategy(new DefaultClientTlsStrategy(sslContext, hostnameVerifier))
				.build();
		return HttpClients.custom().setConnectionManager(connectionManager);
	}
	
	public static TemplateBuilder templateBuilder() {
		return new TemplateBuilder();
	}

	public static class TemplateBuilder {
		
		private Integer maxTotal;
		private Integer maxPerRoute;
		private Integer connectTimeout;
		private Integer readTimeout;
		private String proxyHost;
		private Integer proxyPort;
		private String username;
		private String password;
		
		public TemplateBuilder maxTotal(Integer maxTotal) {
			this.maxTotal = maxTotal;
			return this;
		}
		
		public TemplateBuilder maxPerRoute(Integer maxPerRoute) {
			this.maxPerRoute = maxPerRoute;
			return this;
		}
		
		public TemplateBuilder connectTimeout(Integer connectTimeout) {
			this.connectTimeout = connectTimeout;
			return this;
		}
		
		public TemplateBuilder readTimeout(Integer readTimeout) {
			this.readTimeout = readTimeout;
			return this;
		}
		
		public TemplateBuilder proxy(String proxyHost, Integer proxyPort) {
			this.proxyHost = proxyHost;
			this.proxyPort = proxyPort;
			return this;
		}
		
		public TemplateBuilder credential(String username, String password) {
			this.username = username;
			this.password = password;
			return this;
		}

		public RestTemplate build() throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
			SSLContext sslContext = SSLContexts.custom().loadTrustMaterial(null, (x509Certificates, s) -> true).build();
			HostnameVerifier hostnameVerifier = new NoopHostnameVerifier();
			HttpClientConnectionManager connectionManager;
			if (maxTotal != null && maxPerRoute != null) {
				connectionManager = PoolingHttpClientConnectionManagerBuilder
						.create()
						.setMaxConnTotal(maxTotal)
						.setMaxConnPerRoute(maxPerRoute)
						.setTlsSocketStrategy(new DefaultClientTlsStrategy(sslContext, hostnameVerifier))
						.build();
			} else {
				connectionManager = PoolingHttpClientConnectionManagerBuilder
						.create()
						.setTlsSocketStrategy(new DefaultClientTlsStrategy(sslContext, hostnameVerifier))
						.build();
			}

			HttpClientBuilder clientBuilder = HttpClients.custom();
			clientBuilder.setConnectionManager(connectionManager);

			if (this.maxTotal != null || this.maxPerRoute != null) {
				clientBuilder.setConnectionReuseStrategy(DefaultClientConnectionReuseStrategy.INSTANCE);
			}

			if (StringUtils.isNotBlank(this.proxyHost) && Objects.nonNull(this.proxyPort)) {
				HttpHost proxy = new HttpHost(this.proxyHost, this.proxyPort);
				clientBuilder.setProxy(proxy);
			}
			
			if (StringUtils.isNotBlank(this.username) && StringUtils.isNotBlank(this.password)) {
				BasicCredentialsProvider credentialsProvider = new BasicCredentialsProvider();
				credentialsProvider.setCredentials(new AuthScope(null, -1), new UsernamePasswordCredentials(this.username, this.password.toCharArray()));
				clientBuilder.setDefaultCredentialsProvider(credentialsProvider);
			}

			HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(clientBuilder.build());
			requestFactory.setConnectTimeout(Optional.ofNullable(this.connectTimeout).orElse(CONNECT_TIMEOUT));
			requestFactory.setConnectionRequestTimeout(Optional.ofNullable(this.readTimeout).orElse(READ_TIMEOUT));
			RestTemplate restTemplate = new RestTemplate(new BufferingClientHttpRequestFactory(requestFactory));
			restTemplate.setInterceptors(Arrays.asList(new LoggingInterceptor()));
			return restTemplate;
		}
		
	}

}
