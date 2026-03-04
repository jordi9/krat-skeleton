package com.jordi9.skeleton.feature.item.domain

import java.time.Instant

data class Item(
  val id: ItemId,
  val name: String,
  val description: String?,
  val createdAt: Instant,
  val updatedAt: Instant
)
