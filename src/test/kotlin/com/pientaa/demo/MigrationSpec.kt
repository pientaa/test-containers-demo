package com.pientaa.demo

import com.pientaa.demo.Util.testFlyway
import io.kotest.core.spec.style.FunSpec
import io.kotest.core.test.TestCase
import io.kotest.core.test.TestResult

abstract class MigrationSpec(body: FunSpec.() -> Unit = {}) : FunSpec(body) {

    override fun afterTest(testCase: TestCase, result: TestResult) {
        testFlyway.clean()
        testFlyway.migrate()
    }
}