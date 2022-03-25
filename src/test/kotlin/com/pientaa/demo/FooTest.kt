package com.pientaa.demo

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldNotBe
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration
import org.testcontainers.junit.jupiter.Testcontainers

@SpringBootTest
@ContextConfiguration(initializers = [TestContainersProjectConfig.DockerPostgreDataSourceInitializer::class])
@Testcontainers
class FooTest(private val personRepository: PersonRepository) : FunSpec() {
    init {

        test("foo") {
            personRepository.findByEmail("pientaa@pientaa.pl") shouldNotBe null
        }
    }
}