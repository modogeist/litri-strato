package com.litri.strato.csm.service.sync;

import com.litri.strato.dsm.sync.ServiceDetail;
import com.litri.strato.csm.service.InfoService;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ServiceService extends InfoService<ServiceDetail> {

	public ServiceService(long timeout) {
		super(timeout);
	}

}
