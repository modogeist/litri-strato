package com.litri.strato.csm.controller.sync;

import com.litri.strato.dsm.Info;
import com.litri.strato.dsm.sync.ServiceProviderLink;
import com.litri.strato.csm.service.sync.ServiceProviderLinkService;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ServiceProviderLinkController {
	
	@Autowired
	ServiceProviderLinkService serviceProviderLinkService;

	@GetMapping(value = "/serviceproviderlinks", produces = {MediaType.APPLICATION_JSON_VALUE})
	public List<Info<ServiceProviderLink>> getLinks() {
		return this.serviceProviderLinkService.getAll().stream()
				.collect(Collectors.toList());
	}
	
	@GetMapping(value = "/serviceproviderlinks/{linkId}", produces = {MediaType.APPLICATION_JSON_VALUE})
	public Info<ServiceProviderLink> getLink(@PathVariable("linkId") UUID linkId) {
		return this.serviceProviderLinkService.get(linkId);
	}
	
	@PostMapping(value = "/serviceproviderlinks", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
	public Info<ServiceProviderLink> addLink(@RequestBody ServiceProviderLink linkDetail) {
		return this.serviceProviderLinkService.init(linkDetail);
	}
	
	@PutMapping(value = "/serviceproviderlinks", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
	public Info<ServiceProviderLink> updateLink(@RequestBody Info<ServiceProviderLink> linkInfo) {
		return this.serviceProviderLinkService.update(linkInfo.getId(), linkInfo.getDetail());
	}
	
	@DeleteMapping(value = "/serviceproviderlinks/{linkId}", produces = {MediaType.APPLICATION_JSON_VALUE})
	public Info<ServiceProviderLink> deleteLink(@PathVariable("linkId") UUID linkId) {
		return this.serviceProviderLinkService.fini(linkId);
	}

}
