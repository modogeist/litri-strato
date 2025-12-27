package com.litri.strato.csm.service.sync;

import com.litri.strato.dsm.sync.ProviderDetail;
import com.litri.strato.csm.service.InfoService;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ProviderService extends InfoService<ProviderDetail> {

	public ProviderService(long timeout) {
		super(timeout);
	}

}
