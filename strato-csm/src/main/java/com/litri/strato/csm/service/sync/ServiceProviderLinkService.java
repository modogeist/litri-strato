package com.litri.strato.csm.service.sync;

import com.litri.strato.dsm.Info;
import com.litri.strato.dsm.sync.ServiceProviderLink;
import com.litri.strato.csm.service.InfoService;
import java.util.Collection;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ServiceProviderLinkService extends InfoService<ServiceProviderLink> {

	public ServiceProviderLinkService(long timeout) {
		super(timeout);
	}

	public Collection<Info<ServiceProviderLink>> getByServiceInfoId(UUID serviceInfoId) {
		return this.infos.values().stream()
				.filter(info -> Objects.equals(info.getDetail().getServiceInfoId(), serviceInfoId))
				.collect(Collectors.toList());
	}

}
