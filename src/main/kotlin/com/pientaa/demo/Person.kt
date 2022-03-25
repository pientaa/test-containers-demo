package com.pientaa.demo

import org.springframework.data.jpa.repository.JpaRepository
import javax.persistence.Entity
import javax.persistence.Id

@Entity
class Person(
    @Id
    val email: String,
    val name: String
)

interface PersonRepository : JpaRepository<Person, String> {
    fun findByEmail(email: String): Person?
}