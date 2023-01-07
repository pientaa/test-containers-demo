package com.pientaa.demo

class PersonInitializer(
    private val personRepository: PersonRepository
) {
    fun init() {
        personRepository.save(Person("pientaa@pientaa.pl", "pienta"))
    }
}