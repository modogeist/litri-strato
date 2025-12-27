package com.litri.strato.csm.service.async;

import com.litri.strato.dsm.async.ParticipantDetail;
import com.litri.strato.csm.service.InfoService;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ParticipantService extends InfoService<ParticipantDetail> {

	public ParticipantService(long timeout) {
		super(timeout);
	}

}
