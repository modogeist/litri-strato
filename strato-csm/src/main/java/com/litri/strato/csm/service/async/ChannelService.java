package com.litri.strato.csm.service.async;

import com.litri.strato.dsm.async.ChannelDetail;
import com.litri.strato.csm.service.InfoService;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ChannelService extends InfoService<ChannelDetail> {

	public ChannelService(long timeout) {
		super(timeout);
	}

}
