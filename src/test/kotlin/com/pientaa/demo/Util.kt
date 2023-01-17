package com.pientaa.demo

import io.kotest.core.spec.Spec
import io.kotest.extensions.testcontainers.perSpec
import org.flywaydb.core.Flyway
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.utility.DockerImageName
import org.testcontainers.utility.MountableFile

object Util {
    private val POSTGRES_IMAGE: DockerImageName = DockerImageName.parse("postgres:12")

    val testFlyway: Flyway
        get() = Flyway.configure()
            .dataSource("jdbc:tc:postgresql:12:///test_db", "test_user", "test_password")
            .load()

    val postgresContainer = PostgreSQLContainer<Nothing>(POSTGRES_IMAGE).apply {
        withDatabaseName("test_db")
        withUsername("test_user")
        withPassword("test_password")
//    This won't work :/ schema need to be validated earlier
        withCopyFileToContainer(
            MountableFile.forClasspathResource("/db/migration/test/V1__schema_snapshot.sql"),
            "/docker-entrypoint-initdb.d/"
        )
    }

    fun Spec.attachBasicPostgresContainer(): PostgreSQLContainer<Nothing> {
        listener(postgresContainer.perSpec())
        return postgresContainer
    }
}
