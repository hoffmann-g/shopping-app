package com.hoffmann_g.cart_service;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Import(TestcontainersConfiguration.class)
@SpringBootTest
class CartServiceApplicationTests {

	@Test
	void contextLoads() {
	}

}
