package com.pientaa.demo

import io.kotest.core.spec.style.FunSpec
import io.kotest.extensions.testcontainers.perTest
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.utility.DockerImageName

object Util {
    private val POSTGRES_IMAGE: DockerImageName
        get() = DockerImageName.parse("postgres:12")

    fun FunSpec.attachBasicPostgresContainer(): PostgreSQLContainer<Nothing> {

        val postgresContainer = PostgreSQLContainer<Nothing>(POSTGRES_IMAGE).apply {
            withDatabaseName("test_db")
            withUsername("test_user")
            withPassword("test_password")
        }

        listener(postgresContainer.perTest())
        return postgresContainer
    }
}
