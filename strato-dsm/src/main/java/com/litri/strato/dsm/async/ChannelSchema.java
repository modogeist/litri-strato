package com.litri.strato.dsm.async;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.litri.strato.dsm.BaseSchema.Info;
import com.litri.strato.dsm.RefOption;
import com.litri.strato.dsm.async.AsyncSchema.Channel;
import com.litri.strato.dsm.async.AsyncSchema.Components;
import com.litri.strato.dsm.async.AsyncSchema.Operation;
import com.litri.strato.dsm.async.AsyncSchema.Server;
import java.util.Map;
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
public class ChannelSchema {
	
	@NonNull
	private String asyncapi;
	@NonNull
	private Info info;
	private String id;
	
//	private Map<String, RefOption<Server>> servers;
	private Map<String, String> defaultContentType;
	private Map<String, RefOption<Channel>> channels;
//	private Map<String, RefOption<Operation>> operations;
//	private Components components;

}
