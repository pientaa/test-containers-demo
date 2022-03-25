package com.pientaa.demo

import io.kotest.core.config.AbstractProjectConfig
import io.kotest.extensions.spring.SpringExtension
import io.kotest.extensions.spring.SpringTestExtension
import io.kotest.extensions.spring.SpringTestLifecycleMode
import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.test.context.support.TestPropertySourceUtils
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container

internal object TestContainersProjectConfig : AbstractProjectConfig() {

    override fun extensions() = listOf(SpringExtension, SpringTestExtension(SpringTestLifecycleMode.Root))

    override fun beforeAll() {
        super.beforeAll()
        postgreSQLContainer.start()
    }

    override fun afterAll() {
        super.afterAll()
        postgreSQLContainer.stop()
    }

    @Container
    private val postgreSQLContainer = PostgreSQLContainer<Nothing>("postgres:12")
        .apply { withInitScript("db/migration/V1__create_person_schema.sql") }

    internal object DockerPostgreDataSourceInitializer : ApplicationContextInitializer<ConfigurableApplicationContext> {

        override fun initialize(applicationContext: ConfigurableApplicationContext) {
            postgreSQLContainer.run {
                TestPropertySourceUtils.addInlinedPropertiesToEnvironment(
                    applicationContext,
                    "spring.datasource.url=$jdbcUrl",
                    "spring.datasource.username=$username",
                    "spring.datasource.password=$password"
                );
            }
        }
    }
}
