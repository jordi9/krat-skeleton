package com.jordi9.skeleton.feature.item.application

import com.jordi9.skeleton.Registry
import com.jordi9.skeleton.feature.item.domain.Item
import com.jordi9.skeleton.feature.item.domain.NotificationClient
import com.jordi9.skeleton.feature.item.outbound.ItemRepository

class CreateItemUseCase(
  private val items: ItemRepository,
  private val notifications: NotificationClient
) {

  suspend operator fun invoke(name: String, description: String?): Item {
    val item = items.save(name, description)
    notifications.notify("Item created: ${item.name}")
    return item
  }
}

fun CreateItemUseCase(registry: Registry) = CreateItemUseCase(
  items = ItemRepository(registry),
  notifications = registry.notificationClient
)
