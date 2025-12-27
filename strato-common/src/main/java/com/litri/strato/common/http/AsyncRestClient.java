package com.litri.strato.common.http;

import io.netty.channel.ChannelOption;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import io.netty.handler.timeout.ReadTimeoutHandler;
import java.util.List;
import java.util.function.Consumer;
import javax.net.ssl.SSLException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.http.client.reactive.ReactorResourceFactory;
import org.springframework.http.codec.ClientCodecConfigurer;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;
import reactor.netty.transport.ProxyProvider;

public class AsyncRestClient {

	private static final int CONNECT_TIMEOUT = 15000;
	private static final int READ_TIMEOUT = 15000;
	
	public static ClientBuilder clientBuilder() {
		return new ClientBuilder();
	}

	public static class ClientBuilder {
		private Integer connectTimeout;
		private Integer readTimeout;
		private String proxyHost;
		private Integer proxyPort;
		private ReactorResourceFactory resourceFactory;
		private Consumer<List<ExchangeFilterFunction>> filters;
		private Consumer<ClientCodecConfigurer> codecs;

		public ClientBuilder connectTimeout(Integer connectTimeout) {
			this.connectTimeout = connectTimeout;
			return this;
		}

		public ClientBuilder readTimeout(Integer readTimeout) {
			this.readTimeout = readTimeout;
			return this;
		}

		public ClientBuilder proxy(String proxyHost, Integer proxyPort) {
			this.proxyHost = proxyHost;
			this.proxyPort = proxyPort;
			return this;
		}

		public ClientBuilder resourceFactory(ReactorResourceFactory resourceFactory) {
			this.resourceFactory = resourceFactory;
			return this;
		}

		public ClientBuilder filters(Consumer<List<ExchangeFilterFunction>> filters) {
			this.filters = filters;
			return this;
		}

		public ClientBuilder codecs(Consumer<ClientCodecConfigurer> codecs) {
			this.codecs = codecs;
			return this;
		}

		public WebClient build() throws SSLException {
			SslContext sslContext = SslContextBuilder
					.forClient()
					.trustManager(InsecureTrustManagerFactory.INSTANCE)
					.build();
			
			HttpClient httpClient = this.resourceFactory == null
					? HttpClient.create()
					: HttpClient
							.create(this.resourceFactory.getConnectionProvider())
							.runOn(this.resourceFactory.getLoopResources());
			httpClient = httpClient
					.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, this.connectTimeout == null ? CONNECT_TIMEOUT : this.connectTimeout)
					.doOnConnected(conn -> conn.addHandlerLast(new ReadTimeoutHandler((this.readTimeout == null ? READ_TIMEOUT : this.readTimeout) / 1000)))
					.secure(spec -> spec.sslContext(sslContext));
			httpClient = StringUtils.isBlank(this.proxyHost) || this.proxyPort == null
					? httpClient
					: httpClient.proxy(spec -> spec.type(ProxyProvider.Proxy.HTTP).host(this.proxyHost).port(this.proxyPort));
			
			ReactorClientHttpConnector clientConnector = new ReactorClientHttpConnector(httpClient);
			WebClient.Builder clientBuilder = WebClient.builder().clientConnector(clientConnector);
			clientBuilder = this.filters == null ? clientBuilder : clientBuilder.filters(this.filters);
			clientBuilder = this.codecs == null ? clientBuilder : clientBuilder.codecs(this.codecs);

			return clientBuilder.build();
		}
	}

}
