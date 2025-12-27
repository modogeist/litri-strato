package com.litri.strato.csm.service.async;

import com.litri.strato.dsm.async.ChannelParticipantLink;
import com.litri.strato.csm.service.InfoService;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ChannelParticipantLinkService extends InfoService<ChannelParticipantLink> {

	public ChannelParticipantLinkService(long timeout) {
		super(timeout);
	}

}
