package com.jordi9.skeleton.fixture

import com.jordi9.skeleton.sharedClock
import java.time.Instant

data class ItemExample(
  val name: String = "Test Item",
  val description: String? = null,
  val createdAt: Instant = sharedClock().now(),
  val updatedAt: Instant = createdAt
)
