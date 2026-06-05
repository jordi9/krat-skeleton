package com.jordi9.skeleton.feature.item.outbound

import com.jordi9.skeleton.feature.item.domain.ItemPriority

internal object ItemPriorityMapper {
  fun toDatabase(priority: ItemPriority): String = when (priority) {
    ItemPriority.LOW -> "low"
    ItemPriority.NORMAL -> "normal"
    ItemPriority.HIGH -> "high"
  }

  fun toDomain(value: String): ItemPriority = when (value) {
    "low" -> ItemPriority.LOW
    "normal" -> ItemPriority.NORMAL
    "high" -> ItemPriority.HIGH
    else -> error("Unknown item priority in database: $value")
  }
}
