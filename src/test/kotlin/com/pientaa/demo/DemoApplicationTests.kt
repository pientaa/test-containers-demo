package com.pientaa.demo

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.testcontainers.junit.jupiter.Testcontainers

@SpringBootTest
@ActiveProfiles("test")
@ContextConfiguration(initializers = [TestContainersProjectConfig.DockerPostgreDataSourceInitializer::class])
@Testcontainers
class DemoApplicationTests {

    @Test
    fun contextLoads() {
    }
}
