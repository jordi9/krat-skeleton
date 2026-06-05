package com.jordi9.skeleton.feature.item.inbound

import com.jordi9.skeleton.feature.item.domain.ItemPriority

internal fun toItemPriority(value: String): ItemPriority = when (value) {
  "low" -> ItemPriority.LOW
  "normal" -> ItemPriority.NORMAL
  "high" -> ItemPriority.HIGH
  else -> throw IllegalArgumentException("Invalid item priority")
}
