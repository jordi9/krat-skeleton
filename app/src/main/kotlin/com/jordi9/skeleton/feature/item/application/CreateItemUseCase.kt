package com.jordi9.skeleton.feature.item.application

import com.jordi9.krat.time.TimeClock
import com.jordi9.skeleton.Registry
import com.jordi9.skeleton.feature.item.domain.Item
import com.jordi9.skeleton.feature.item.domain.ItemId
import com.jordi9.skeleton.feature.item.domain.ItemPriority
import com.jordi9.skeleton.feature.item.domain.NotificationClient
import com.jordi9.skeleton.feature.item.outbound.ItemRepository
import com.jordi9.skeleton.shared.domain.NanoId

class CreateItemUseCase(
  private val items: ItemRepository,
  private val notifications: NotificationClient,
  private val ids: NanoId,
  private val clock: TimeClock
) {

  suspend operator fun invoke(command: CreateItemCommand): Item {
    val now = clock.now()
    val item = items.save(
      Item(
        id = ItemId(ids.get(ItemId.PREFIX)),
        name = command.name,
        description = command.description,
        priority = command.priority,
        createdAt = now,
        updatedAt = now
      )
    )
    notifications.notify("Item created: ${item.name}")
    return item
  }
}

data class CreateItemCommand(
  val name: String,
  val description: String?,
  val priority: ItemPriority
)

fun CreateItemUseCase(registry: Registry) = CreateItemUseCase(
  items = ItemRepository(registry),
  notifications = registry.notificationClient,
  ids = registry.nanoId,
  clock = registry.timeClock
)
