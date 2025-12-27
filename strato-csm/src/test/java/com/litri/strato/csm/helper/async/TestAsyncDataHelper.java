package com.litri.strato.csm.helper.async;

import com.litri.strato.dsm.BaseSchema;
import com.litri.strato.dsm.async.BrokerSchema;
import com.litri.strato.dsm.async.ChannelSchema;
import com.litri.strato.dsm.async.MessageSchema;
import com.litri.strato.dsm.async.ParticipantSchema;

public class TestAsyncDataHelper {

	public static MessageSchema getMinMessageSchema() {
		return MessageSchema.builder()
				.asyncapi("asyncapi-test")
				.info(BaseSchema.Info.builder()
						.title("title-test")
						.version("version-test")
						.build())
				.build();
	}

	public static ParticipantSchema getMinParticipantSchema() {
		return ParticipantSchema.builder()
				.asyncapi("asyncapi-test")
				.info(BaseSchema.Info.builder()
						.title("title-test")
						.version("version-test")
						.build())
				.build();
	}

	public static ChannelSchema getMinChannelSchema() {
		return ChannelSchema.builder()
				.asyncapi("asyncapi-test")
				.info(BaseSchema.Info.builder()
						.title("title-test")
						.version("version-test")
						.build())
				.build();
	}

	public static BrokerSchema getMinBrokerSchema() {
		return BrokerSchema.builder()
				.asyncapi("asyncapi-test")
				.info(BaseSchema.Info.builder()
						.title("title-test")
						.version("version-test")
						.build())
				.build();
	}

}
