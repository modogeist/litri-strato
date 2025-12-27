package com.litri.strato.vdm.compute;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.litri.strato.vdm.ValueTree;
import com.litri.strato.vdm.ValueType;
import com.litri.strato.vdm.value.Value;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@Slf4j
public class WebhookBiFunction extends BaseBiFunction {

	public static final String CONFIG_URL = "url";
	public static final String CONFIG_METHOD = "method";
	public static final String CONFIG_USERNAME = "username";
	public static final String CONFIG_PASSWORD = "password";
	public static final String CONFIG_INPUT = "input";
	public static final String CONFIG_OUTPUT_PATH = "outputPath";
	
	private final ObjectMapper objectMapper = new ObjectMapper();
	private final TypeReference<Map<String, Object>> mapTypeReference = new TypeReference<Map<String, Object>>() {};
	
	private RestTemplate restTemplate;
	
	public WebhookBiFunction() {
		this.configTypes = Map.of(
				CONFIG_URL, Set.of(ValueType.String),
				CONFIG_METHOD, Set.of(ValueType.String));
		this.inputMinCount = 0L;
		this.inputMaxCount = 0L;
		this.inputTypes = ValueType.NONE;
		this.outputTypes = ValueType.ALL;
	}

	@Override
	protected Value applyInternal(Context functionContext) {
		try {
			Map<String, Value> configs = functionContext.getConfigs();
			String url = configs.get(CONFIG_URL).asString();
			String method = configs.get(CONFIG_METHOD).asString();
			HttpMethod httpMethod = this.getHttpMethod(method);
			
			HttpHeaders httpHeaders = new HttpHeaders();
			httpHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));
			
			if (configs.containsKey(CONFIG_USERNAME) && configs.containsKey(CONFIG_PASSWORD)) {
				String username = configs.get(CONFIG_USERNAME).asString();
				String password = configs.get(CONFIG_PASSWORD).asString();
				httpHeaders.setBasicAuth(username, password);
			}
			
			HttpEntity httpEntity = null;
			if (configs.containsKey(CONFIG_INPUT)) {
				UUID inputId = configs.getOrDefault(CONFIG_INPUT, new Value()).asUid();
				// TODO: Input value may not be valid.
				ValueTree.Node inputValueNode = functionContext.getValueNode(inputId).get();
				Value inputValue = inputValueNode.getValue();
				httpHeaders.setContentType(MediaType.APPLICATION_JSON);
				httpEntity = new HttpEntity(inputValue.asObject(), httpHeaders);
			} else {
				httpEntity = new HttpEntity(httpHeaders);
			}
			
			// TODO: Input may be part of url.
			// TODO: TemplateBuilder may need more parameters.
			RestTemplate restTemplate = this.getRestTemplate();
			ResponseEntity<String> responseEntity = restTemplate.exchange(url, httpMethod, httpEntity, String.class);
//			if (!Objects.equals(responseEntity.getStatusCode(), HttpStatus.OK)) {
//				return null;
//			}
			if (!responseEntity.hasBody()) {
				return null;
			}
			
			String text = responseEntity.getBody();
			Object object = null;
			if (configs.containsKey(CONFIG_OUTPUT_PATH) && this.isJson(text)) {
				String outputPath = configs.getOrDefault(CONFIG_OUTPUT_PATH, new Value()).asString();
				try {
					Map<String, Object> body = this.objectMapper.readValue(text, this.mapTypeReference);
					object = this.getObject(outputPath, body);
				} catch (Exception e) {
					log.error("Applying selection (path={}) to nonJSON data (text={})", outputPath, text);
					return null;
				}
			} else {
				object = text;
			}
			
			if (Objects.isNull(object)) {
				log.error("Processing webhook results");
				return null;
			}
			
			Value value = new Value(object);
			return value;
		} catch (Exception e) {
			log.error("Processing webhook using {}", this.configTypes);
		}
		
		return null;
	}
	
	private HttpMethod getHttpMethod(String method) {
		return HttpMethod.valueOf(method.toUpperCase());
	}
	
	protected synchronized RestTemplate getRestTemplate() throws Exception {
		if (Objects.isNull(this.restTemplate)) {
//			this.restTemplate = RestClient.templateBuilder().build();
			this.restTemplate = new RestTemplate();
		}
		return this.restTemplate;
	}
	
	private boolean isJson(String text) {
		return text.startsWith("{") && text.endsWith("}");
	}
	
	private Object getObject(String outputPath, Map<String, Object> body) {
		Object object = body;
		String[] paths = outputPath.split("\\.");
		for (String path : paths) {
			// TODO: Finish logic.
			if (object instanceof Map) {
				object = ((Map) object).get(path);
			} else if (object instanceof List) {
				int idx = Integer.parseInt(path);
				object = ((List) object).get(idx);
			} else if (Objects.isNull(object)) {
				return object;
			}
		}
		return object;
	}
	
	/**
	AuthorizationCodeResourceDetails resourceDetails = new AuthorizationCodeResourceDetails();
	resourceDetails.setClientId("<client-id>");
	resourceDetails.setClientSecret("<client-secret>");
	resourceDetails.setAccessTokenUri("<access-token-uri>");
	resourceDetails.setUserAuthorizationUri("<user-authorization-uri>");
	resourceDetails.setScope(Arrays.asList("read", "write"));
	 
	OAuth2RestTemplate restTemplate = new OAuth2RestTemplate(resourceDetails);
	 */
	
	/**
	SSLContext sslContext = SSLContextBuilder
		.create()
		.loadKeyMaterial(keyStore.getFile(), keyStorePassword.toCharArray(), keyPassword.toCharArray())
		.loadTrustMaterial(trustStore.getFile(), trustStorePassword.toCharArray())
		.build();

	HttpClient client = HttpClients.custom()
		.setSSLContext(sslContext)
		.build();

	HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory(client);

	RestTemplate restTemplate = new RestTemplate(factory);
	 */
	
	/**
	RestTemplate restTemplate = new RestTemplate();
	HttpHeaders headers = new HttpHeaders();
	headers.set("Authorization", "Bearer " + jwtToken);
	HttpEntity<String> entity = new HttpEntity<>("parameters", headers);
	restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
	 */
	
	/**
	String username = config.get("username");
	String password = config.get("password");
	restTemplate.getInterceptors().add(new BasicAuthenticationInterceptor(username, password));
	 */
	
}
