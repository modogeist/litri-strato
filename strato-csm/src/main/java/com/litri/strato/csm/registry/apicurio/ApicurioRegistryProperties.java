package com.litri.strato.csm.registry.apicurio;

import java.util.List;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@ConfigurationProperties(prefix = "registry.apicurio")
@ConfigurationPropertiesScan
@Getter
@Setter
public class ApicurioRegistryProperties {
	
	private String url;
	
	private String wmConsumerId;
	private String wmServiceName;
	private String wmServiceEnv;
	
	private List<String> labels;
	private Map<String, String> properties;	
	
}
