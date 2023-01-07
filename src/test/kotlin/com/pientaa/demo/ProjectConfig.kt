package com.pientaa.demo

import com.pientaa.demo.Util.attachBasicPostgresContainer
import io.kotest.core.config.AbstractProjectConfig
import io.kotest.core.listeners.TestListener
import io.kotest.core.spec.Spec
import io.kotest.core.test.TestCase
import io.kotest.core.test.TestResult
import io.kotest.extensions.spring.SpringExtension
import io.kotest.extensions.spring.SpringTestExtension
import io.kotest.extensions.spring.SpringTestLifecycleMode
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
        super.beforeSpec(spec)
        spec.attachBasicPostgresContainer()
    }
}

object DatabaseCleanUpListener : TestListener {
    override suspend fun afterTest(testCase: TestCase, result: TestResult) {
//      Huge disadvantage: when there is already plenty of data and plenty of flyways already applied
//      to the production server. It makes no sense to snapshot schema then, because flyways will be run despite that.
//      So I propose to clear database not using flyway, but using some other tool instead.
//      It can be e.g simple truncate.

        Util.testFlyway.clean()
        Util.testFlyway.migrate()

        Util.testFlyway.configuration.dataSource.connection.createStatement()
            .execute(
                File("src/main/resources/import.sql").readText()
            )
        super.afterTest(testCase, result)
    }
}
