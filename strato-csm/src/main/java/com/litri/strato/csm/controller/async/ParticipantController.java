package com.litri.strato.csm.controller.async;

import com.litri.strato.dsm.Info;
import com.litri.strato.dsm.async.ParticipantDetail;
import com.litri.strato.csm.service.async.ParticipantService;
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
public class ParticipantController {
	
	@Autowired
	ParticipantService participantService;

	@GetMapping(value = "/participants", produces = {MediaType.APPLICATION_JSON_VALUE})
	public List<Info<ParticipantDetail>> getParticipants() {
		return this.participantService.getAll().stream()
				.collect(Collectors.toList());
	}
	
	@GetMapping(value = "/participants/{participantId}", produces = {MediaType.APPLICATION_JSON_VALUE})
	public Info<ParticipantDetail> getParticipant(@PathVariable("participantId") UUID participantId) {
		return this.participantService.get(participantId);
	}
	
	@PostMapping(value = "/participants", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
	public Info<ParticipantDetail> addParticipant(@RequestBody ParticipantDetail participantDetail) {
		return this.participantService.init(participantDetail);
	}
	
	@PutMapping(value = "/participants", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
	public Info<ParticipantDetail> updateParticipant(@RequestBody Info<ParticipantDetail> participantInfo) {
		return this.participantService.update(participantInfo.getId(), participantInfo.getDetail());
	}
	
	@DeleteMapping(value = "/participants/{participantId}", produces = {MediaType.APPLICATION_JSON_VALUE})
	public Info<ParticipantDetail> deleteParticipant(@PathVariable("participantId") UUID participantId) {
		return this.participantService.fini(participantId);
	}
	
}
