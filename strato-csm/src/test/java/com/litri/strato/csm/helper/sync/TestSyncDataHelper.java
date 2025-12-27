package com.litri.strato.csm.helper.sync;

import com.litri.strato.dsm.BaseSchema;
import com.litri.strato.dsm.sync.DataSchema;
import com.litri.strato.dsm.sync.ProviderSchema;
import com.litri.strato.dsm.sync.ServiceSchema;

public class TestSyncDataHelper {

	public static DataSchema getMinDataSchema() {
		return DataSchema.builder()
				.openapi("openapi-test")
				.info(BaseSchema.Info.builder()
						.title("title-test")
						.version("version-test")
						.build())
				.build();
	}

	public static ServiceSchema getMinServiceSchema() {
		return ServiceSchema.builder()
				.openapi("openapi-test")
				.info(BaseSchema.Info.builder()
						.title("title-test")
						.version("version-test")
						.build())
				.build();
	}

	public static ProviderSchema getMinProviderSchema() {
		return ProviderSchema.builder()
				.openapi("openapi-test")
				.info(BaseSchema.Info.builder()
						.title("title-test")
						.version("version-test")
						.build())
				.build();
	}

}
