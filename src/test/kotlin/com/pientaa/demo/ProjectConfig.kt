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
        Util.testFlyway.clean()
        Util.testFlyway.migrate()
        super.afterTest(testCase, result)
    }
}
