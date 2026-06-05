package com.jordi9.skeleton.feature.item.inbound

import com.jordi9.skeleton.feature.item.domain.Item
import com.jordi9.skeleton.feature.item.domain.ItemPriority
import kotlinx.serialization.Serializable

@Serializable
data class ItemResponse(
  val id: String,
  val name: String,
  val description: String?,
  val priority: String,
  val createdAt: String,
  val updatedAt: String
)

fun Item.toResponse() = ItemResponse(
  id = id.value,
  name = name,
  description = description,
  priority = priority.toResponse(),
  createdAt = createdAt.toString(),
  updatedAt = updatedAt.toString()
)

private fun ItemPriority.toResponse(): String = when (this) {
  ItemPriority.LOW -> "low"
  ItemPriority.NORMAL -> "normal"
  ItemPriority.HIGH -> "high"
}
