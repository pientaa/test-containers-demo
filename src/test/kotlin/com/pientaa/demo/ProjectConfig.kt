package com.pientaa.demo

import com.pientaa.demo.Util.attachBasicPostgresContainer
import io.kotest.core.config.AbstractProjectConfig
import io.kotest.core.listeners.TestListener
import io.kotest.core.spec.Spec
import io.kotest.extensions.spring.SpringExtension
import io.kotest.extensions.spring.SpringTestExtension
import io.kotest.extensions.spring.SpringTestLifecycleMode
import io.kotest.extensions.testcontainers.TestContainerExtension
import org.testcontainers.containers.KafkaContainer
import org.testcontainers.utility.DockerImageName
import java.io.File

internal object TestContainersProjectConfig : AbstractProjectConfig() {
    override fun extensions() =
        listOf(
            SpringExtension,
            SpringTestExtension(SpringTestLifecycleMode.Test),
            TestContainerListener,
            DatabaseCleanUpListener
        )
}

object TestContainerListener : TestListener {
    override suspend fun beforeSpec(spec: Spec) {
        spec.attachBasicPostgresContainer()
        super.beforeSpec(spec)
    }
}

object DatabaseCleanUpListener : TestListener {

    override suspend fun beforeSpec(spec: Spec) {
        super.beforeSpec(spec)
        val connection = Util.testFlyway.configuration.dataSource.connection
        connection.createStatement()
            .execute(File("src/main/resources/import.sql").readText())
    }

    override suspend fun afterSpec(spec: Spec) {
        val connection = Util.testFlyway.configuration.dataSource.connection
        connection.createStatement().execute(
            """
                DO LANGUAGE plpgsql ${'$'}${'$'}
                    DECLARE statements CURSOR FOR SELECT table_name FROM information_schema.tables WHERE table_schema = 'public';
                    BEGIN
                        FOR s IN statements LOOP
                                EXECUTE 'TRUNCATE TABLE ' || s.table_name || ' CASCADE;';
                            END LOOP;
                    END ${'$'}${'$'};
            """.trimIndent()
        )
        super.afterSpec(spec)
    }
}
