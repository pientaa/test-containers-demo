package com.pientaa.demo

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.nulls.shouldNotBeNull
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles

@SpringBootTest
@ActiveProfiles("test")
class FooTest(private val personRepository: PersonRepository) : FunSpec({
    beforeAny {
        PersonInitializer(personRepository).init()
    }
    test("PersonInitializer data has ben saved") {
        withContext(Dispatchers.IO) { personRepository.findByEmail("pientaa@pientaa.pl") }.shouldNotBeNull()
    }
    test("import.sql data has ben saved") {
        withContext(Dispatchers.IO) { personRepository.findByEmail("import@import.pl") }.shouldNotBeNull()
    }
})