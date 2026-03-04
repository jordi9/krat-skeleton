package com.jordi9.skeleton.feature.item.application

import com.jordi9.skeleton.Registry
import com.jordi9.skeleton.feature.item.domain.Item
import com.jordi9.skeleton.feature.item.domain.ItemId
import com.jordi9.skeleton.feature.item.domain.ItemNotFoundException
import com.jordi9.skeleton.feature.item.outbound.ItemRepository

class GetItemUseCase(
  private val items: ItemRepository
) {
  suspend operator fun invoke(id: ItemId): Item =
    items.findById(id) ?: throw ItemNotFoundException("Item not found: ${id.value}")
}

fun GetItemUseCase(registry: Registry) = GetItemUseCase(
  items = ItemRepository(registry)
)
