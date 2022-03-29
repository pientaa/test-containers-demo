package com.pientaa.demo

import io.kotest.core.spec.style.FunSpec
import io.kotest.core.test.TestCase
import io.kotest.core.test.TestResult
import org.flywaydb.core.Flyway

abstract class MigrationSpec(body: FunSpec.() -> Unit = {}) : FunSpec(body) {

    override fun afterTest(testCase: TestCase, result: TestResult) {
        val flyway = Flyway.configure()
            .dataSource("jdbc:tc:postgresql:12:///test_db", "test_user", "test_password")
            .load()
        // These two methods will completely erase and recreate the schema of our test DB instance
        flyway.clean()
        flyway.migrate()
    }
}