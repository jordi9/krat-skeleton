package com.jordi9.skeleton.feature.item.inbound

import com.jordi9.krat.pack.core.Handler
import com.jordi9.skeleton.Registry
import com.jordi9.skeleton.feature.item.application.ListItemsUseCase
import io.ktor.server.application.ApplicationCall
import io.ktor.server.response.respond

class ListItemsHandler(
  private val listItems: ListItemsUseCase
) : Handler {

  override suspend fun handle(call: ApplicationCall) {
    val items = listItems()
    call.respond(items.map { it.toResponse() })
  }
}

fun ListItemsHandler(registry: Registry) = ListItemsHandler(
  listItems = ListItemsUseCase(registry)
)
