package com.litri.strato.csm.controller.sync;

import com.litri.strato.dsm.Info;
import com.litri.strato.dsm.sync.ProviderDetail;
import com.litri.strato.csm.service.sync.ProviderService;
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
public class ProviderController {

	@Autowired
	ProviderService providerService;

	@GetMapping(value = "/providers", produces = {MediaType.APPLICATION_JSON_VALUE})
	public List<Info<ProviderDetail>> getProviders() {
		return this.providerService.getAll().stream()
				.collect(Collectors.toList());
	}
	
	@GetMapping(value = "/providers/{providerId}", produces = {MediaType.APPLICATION_JSON_VALUE})
	public Info<ProviderDetail> getProvider(@PathVariable("providerId") UUID providerId) {
		return this.providerService.get(providerId);
	}
	
	@PostMapping(value = "/providers", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
	public Info<ProviderDetail> addProvider(@RequestBody ProviderDetail providerDetail) {
		return this.providerService.init(providerDetail);
	}
	
	@PutMapping(value = "/providers", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
	public Info<ProviderDetail> updateProvider(@RequestBody Info<ProviderDetail> providerInfo) {
		return this.providerService.update(providerInfo.getId(), providerInfo.getDetail());
	}
	
	@DeleteMapping(value = "/providers/{providerId}", produces = {MediaType.APPLICATION_JSON_VALUE})
	public Info<ProviderDetail> deleteProvider(@PathVariable("providerId") UUID providerId) {
		return this.providerService.fini(providerId);
	}
	
}
