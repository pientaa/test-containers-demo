package com.pientaa.demo

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.testcontainers.junit.jupiter.Testcontainers

@SpringBootTest
@ActiveProfiles("test")
@ContextConfiguration(initializers = [TestContainersProjectConfig.DockerPostgreDataSourceInitializer::class])
@Testcontainers
class FooTest(private val personRepository: PersonRepository) : FunSpec() {
    init {

        test("foo") {
            personRepository.findByEmail("pientaa@pientaa.pl") shouldBe null
        }
        test("add person") {
            personRepository.save(Person("pientaa@pientaa.pl", "pienta"))
        }
    }
}