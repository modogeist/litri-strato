package com.litri.strato.csm.controller.sync;

import com.litri.strato.dsm.Info;
import com.litri.strato.dsm.sync.ServiceDetail;
import com.litri.strato.csm.service.sync.ServiceService;
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
public class ServiceController {

	@Autowired
	ServiceService serviceService;

	@GetMapping(value = "/services", produces = {MediaType.APPLICATION_JSON_VALUE})
	public List<Info<ServiceDetail>> getServices() {
		return this.serviceService.getAll().stream()
				.collect(Collectors.toList());
	}
	
	@GetMapping(value = "/services/{serviceId}", produces = {MediaType.APPLICATION_JSON_VALUE})
	public Info<ServiceDetail> getService(@PathVariable("serviceId") UUID serviceId) {
		return this.serviceService.get(serviceId);
	}
	
	@PostMapping(value = "/services", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
	public Info<ServiceDetail> addService(@RequestBody ServiceDetail serviceDetail) {
		return this.serviceService.init(serviceDetail);
	}
	
	@PutMapping(value = "/services", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
	public Info<ServiceDetail> updateService(@RequestBody Info<ServiceDetail> serviceInfo) {
		return this.serviceService.update(serviceInfo.getId(), serviceInfo.getDetail());
	}
	
	@DeleteMapping(value = "/services/{serviceId}", produces = {MediaType.APPLICATION_JSON_VALUE})
	public Info<ServiceDetail> deleteService(@PathVariable("serviceId") UUID serviceId) {
		return this.serviceService.fini(serviceId);
	}
	
}
