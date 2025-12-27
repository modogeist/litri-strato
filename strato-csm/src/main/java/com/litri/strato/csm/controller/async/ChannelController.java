package com.litri.strato.csm.controller.async;

import com.litri.strato.dsm.Info;
import com.litri.strato.dsm.async.ChannelDetail;
import com.litri.strato.csm.service.async.ChannelService;
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
public class ChannelController {
	
	@Autowired
	ChannelService channelService;

	@GetMapping(value = "/channels", produces = {MediaType.APPLICATION_JSON_VALUE})
	public List<Info<ChannelDetail>> getChannels() {
		return this.channelService.getAll().stream()
				.collect(Collectors.toList());
	}
	
	@GetMapping(value = "/channels/{channelId}", produces = {MediaType.APPLICATION_JSON_VALUE})
	public Info<ChannelDetail> getChannel(@PathVariable("channelId") UUID channelId) {
		return this.channelService.get(channelId);
	}
	
	@PostMapping(value = "/channels", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
	public Info<ChannelDetail> addChannel(@RequestBody ChannelDetail channelDetail) {
		return this.channelService.init(channelDetail);
	}
	
	@PutMapping(value = "/channels", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
	public Info<ChannelDetail> updateChannel(@RequestBody Info<ChannelDetail> channelInfo) {
		return this.channelService.update(channelInfo.getId(), channelInfo.getDetail());
	}
	
	@DeleteMapping(value = "/channels/{channelId}", produces = {MediaType.APPLICATION_JSON_VALUE})
	public Info<ChannelDetail> deleteChannel(@PathVariable("channelId") UUID channelId) {
		return this.channelService.fini(channelId);
	}
	
}
