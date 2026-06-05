package com.jordi9.skeleton.feature.item.domain

import java.time.Instant

data class Item(
  val id: ItemId,
  val name: String,
  val description: String?,
  val priority: ItemPriority,
  val createdAt: Instant,
  val updatedAt: Instant
)
