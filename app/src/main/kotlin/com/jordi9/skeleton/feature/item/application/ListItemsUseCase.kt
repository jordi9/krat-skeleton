package com.jordi9.skeleton.feature.item.application

import com.jordi9.skeleton.Registry
import com.jordi9.skeleton.feature.item.domain.Item
import com.jordi9.skeleton.feature.item.outbound.ItemRepository

class ListItemsUseCase(
  private val items: ItemRepository
) {
  suspend operator fun invoke(): List<Item> = items.findAll()
}

fun ListItemsUseCase(registry: Registry) = ListItemsUseCase(
  items = ItemRepository(registry)
)
