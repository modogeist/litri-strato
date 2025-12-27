package com.litri.strato.dsm.sync;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.litri.strato.dsm.BaseSchema.ExternalDocument;
import com.litri.strato.dsm.BaseSchema.Info;
import com.litri.strato.dsm.BaseSchema.Tag;
import com.litri.strato.dsm.sync.SyncSchema.Server;
import java.util.List;
import java.util.Map;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProviderSchema {
		
	@NonNull
	private String openapi;
	@NonNull
	private Info info;
	private String jsonSchemaDialect;
	
	private List<Server> servers;
	private Map<String, List<String>> security;
//	private Map<String, Path> paths;
//	private Map<String, RefOption<Path>> webhooks;
//	private Components components;
	
	private Set<Tag> tags;
	private ExternalDocument externalDocs;

}
