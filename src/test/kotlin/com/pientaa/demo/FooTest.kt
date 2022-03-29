package com.pientaa.demo

import com.pientaa.demo.Util.attachBasicPostgresContainer
import io.kotest.core.extensions.install
import io.kotest.extensions.testcontainers.JdbcTestContainerExtension
import io.kotest.extensions.testcontainers.LifecycleMode
import io.kotest.matchers.shouldBe
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles

@SpringBootTest
@ActiveProfiles("test")
class FooTest(private val personRepository: PersonRepository) : MigrationSpec() {
    val postgresContainer = attachBasicPostgresContainer()

    init {
        install(JdbcTestContainerExtension(postgresContainer, LifecycleMode.Leaf))

        test("foo") {
            personRepository.findByEmail("pientaa@pientaa.pl") shouldBe null
        }
        test("add person") {
            personRepository.save(Person("pientaa@pientaa.pl", "pienta"))
        }
    }
}