package com.pientaa.demo

import io.kotest.core.spec.Spec
import io.kotest.extensions.testcontainers.perSpec
import org.flywaydb.core.Flyway
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.utility.DockerImageName

object Util {
    private val POSTGRES_IMAGE: DockerImageName = DockerImageName.parse("migrated-postgres:1.0")
        .asCompatibleSubstituteFor("postgres")
//    Also above could be simple postgres and just setting `withFileSystemBind()` in `attachBasicPostgresContainer()`

    val testFlyway: Flyway
        get() = Flyway.configure()
            .dataSource("jdbc:tc:postgresql:12:///test_db", "test_user", "test_password")
            .load()

    fun Spec.attachBasicPostgresContainer(): PostgreSQLContainer<Nothing> {
        val postgresContainer = PostgreSQLContainer<Nothing>(POSTGRES_IMAGE).apply {
            withDatabaseName("test_db")
            withUsername("test_user")
            withPassword("test_password")
        }

        listener(postgresContainer.perSpec())
        return postgresContainer
    }
}
