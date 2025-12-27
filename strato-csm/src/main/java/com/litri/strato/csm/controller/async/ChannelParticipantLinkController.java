package com.litri.strato.csm.controller.async;

import com.litri.strato.dsm.Info;
import com.litri.strato.dsm.async.ChannelParticipantLink;
import com.litri.strato.csm.service.async.ChannelParticipantLinkService;
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
public class ChannelParticipantLinkController {
	
	@Autowired
	ChannelParticipantLinkService channelParticipantLinkService;

	@GetMapping(value = "/channelparticipantlinks", produces = {MediaType.APPLICATION_JSON_VALUE})
	public List<Info<ChannelParticipantLink>> getLinks() {
		return this.channelParticipantLinkService.getAll().stream()
				.collect(Collectors.toList());
	}
	
	@GetMapping(value = "/channelparticipantlinks/{linkId}", produces = {MediaType.APPLICATION_JSON_VALUE})
	public Info<ChannelParticipantLink> getLink(@PathVariable("linkId") UUID linkId) {
		return this.channelParticipantLinkService.get(linkId);
	}
	
	@PostMapping(value = "/channelparticipantlinks", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
	public Info<ChannelParticipantLink> addLink(@RequestBody ChannelParticipantLink linkDetail) {
		return this.channelParticipantLinkService.init(linkDetail);
	}
	
	@PutMapping(value = "/channelparticipantlinks", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
	public Info<ChannelParticipantLink> updateLink(@RequestBody Info<ChannelParticipantLink> linkInfo) {
		return this.channelParticipantLinkService.update(linkInfo.getId(), linkInfo.getDetail());
	}
	
	@DeleteMapping(value = "/channelparticipantlinks/{linkId}", produces = {MediaType.APPLICATION_JSON_VALUE})
	public Info<ChannelParticipantLink> deleteLink(@PathVariable("linkId") UUID linkId) {
		return this.channelParticipantLinkService.fini(linkId);
	}

}
