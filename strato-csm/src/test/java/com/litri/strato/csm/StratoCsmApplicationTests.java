package com.litri.strato.csm;

import com.litri.strato.csm.configuration.TestRegistryConfig;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@SpringBootTest
@Import(TestRegistryConfig.class)
class StratoCsmApplicationTests {

	@Test
	void contextLoads() {
	}

}
