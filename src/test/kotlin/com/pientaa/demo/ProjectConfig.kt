package com.pientaa.demo

import io.kotest.core.config.AbstractProjectConfig
import io.kotest.extensions.spring.SpringExtension
import io.kotest.extensions.spring.SpringTestExtension
import io.kotest.extensions.spring.SpringTestLifecycleMode

internal object TestContainersProjectConfig : AbstractProjectConfig() {
    override fun extensions() = listOf(SpringExtension, SpringTestExtension(SpringTestLifecycleMode.Test))
}
