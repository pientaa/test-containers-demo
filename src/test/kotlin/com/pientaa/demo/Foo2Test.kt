package com.pientaa.demo

import com.pientaa.demo.Util.attachBasicPostgresContainer
import io.kotest.matchers.shouldBe
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles

@SpringBootTest
@ActiveProfiles("test")
class Foo2Test(
    private val personRepository: PersonRepository
) : MigrationSpec() {
    val postgresContainer = attachBasicPostgresContainer()

    init {
        test("foo") {
            personRepository.findByEmail("pientaa@pientaa.pl") shouldBe null
        }
        test("add person") {
            personRepository.save(Person("pientaa@pientaa.pl", "pienta"))
        }
    }
}