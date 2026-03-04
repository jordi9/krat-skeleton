package com.jordi9.skeleton

import io.kotest.core.config.AbstractProjectConfig

class TestingConfig : AbstractProjectConfig() {

  override val extensions = listOf(
    InMemoryDBExtension,
    SkeletonTestAppExtension,
    ResetStubsExtension,
    DeleteTablesExtension
  )
}
