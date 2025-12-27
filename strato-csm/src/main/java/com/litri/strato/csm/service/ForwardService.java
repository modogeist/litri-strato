package com.litri.strato.csm.service;

import com.litri.strato.dsm.Info;
import com.litri.strato.dsm.SchemaId;
import com.litri.strato.dsm.sync.ProviderDetail;
import com.litri.strato.dsm.sync.ProviderSchema;
import com.litri.strato.dsm.sync.ServiceDetail;
import com.litri.strato.dsm.sync.ServiceProviderLink;
import com.litri.strato.dsm.sync.SyncSchema;
import com.litri.strato.csm.service.sync.ProviderService;
import com.litri.strato.csm.service.sync.ServiceProviderLinkService;
import com.litri.strato.csm.service.sync.ServiceService;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ForwardService {
	
	@Autowired
	ServiceService serviceService;
	
	@Autowired
	ServiceProviderLinkService serviceProviderLinkService;
	
	@Autowired
	ProviderService providerService;
	
	@Autowired
	SchemaService<ProviderSchema> providerSchemaService;
	
	public String getProviderUrl(UUID serviceInfoId) throws Exception {
		Info<ServiceDetail> serviceInfo = this.serviceService.get(serviceInfoId);
		if (Objects.isNull(serviceInfo)) {
			log.warn("Service not found for service id {}", serviceInfoId);
			return null;
		}
		
		Collection<Info<ServiceProviderLink>> serviceProviderLinkInfos = this.serviceProviderLinkService.getByServiceInfoId(serviceInfoId);
		List<Info<ProviderDetail>> providerInfos = serviceProviderLinkInfos.stream()
				.map(linkInfo -> this.providerService.get(linkInfo.getDetail().getProviderInfoId()))
				.filter(Objects::nonNull)
				.collect(Collectors.toList());
		if (providerInfos.isEmpty()) {
			log.warn("Providers not found for service id {}", serviceInfoId);
			return null;
		}
		
		// TODO: Need to have policy to pick provider.
		String url = null;
		for (Info<ProviderDetail> providerInfo : providerInfos) {
			SchemaId schemaId = providerInfo.getDetail().getProviderSchemaId();
			try {
				ProviderSchema providerSchema = this.providerSchemaService.get(schemaId);
				SyncSchema.Server server = providerSchema.getServers().get(providerInfo.getDetail().getServerIndex());
				url = server.getUrl();
			} catch (Exception e) {
				log.error("Provider schema not valid", e);
			}
			if (Objects.nonNull(url)) {
				break;
			}
		}
		if (Objects.isNull(url)) {
			log.warn("Providers not valid for service id {}", serviceInfoId);
			return null;
		}
		
		return url;
	}
	
}
