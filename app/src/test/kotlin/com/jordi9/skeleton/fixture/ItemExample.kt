package com.jordi9.skeleton.fixture

import com.jordi9.skeleton.feature.item.domain.ItemId
import com.jordi9.skeleton.feature.item.domain.ItemPriority
import com.jordi9.skeleton.sharedClock
import java.time.Instant
import java.util.UUID

data class ItemExample(
  val id: ItemId = itemId(),
  val name: String = "Test Item",
  val description: String? = null,
  val priority: ItemPriority = ItemPriority.NORMAL,
  val createdAt: Instant = sharedClock().now(),
  val updatedAt: Instant = createdAt
)

fun itemId(): ItemId = ItemId("item_${suffix()}")

private fun suffix(): String = UUID.randomUUID().toString().replace("-", "").take(21)
