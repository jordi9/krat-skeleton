package com.jordi9.skeleton.feature.item.inbound

import com.jordi9.krat.pack.core.Handler
import com.jordi9.skeleton.Registry
import com.jordi9.skeleton.feature.item.application.CreateItemCommand
import com.jordi9.skeleton.feature.item.application.CreateItemUseCase
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.ApplicationCall
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import kotlinx.serialization.Serializable

class CreateItemHandler(
  private val createItem: CreateItemUseCase
) : Handler {
  override suspend fun handle(call: ApplicationCall) {
    val request = call.receive<Request>()
    val item = createItem(request.toCommand())
    call.respond(HttpStatusCode.Created, item.toResponse())
  }

  @Serializable
  data class Request(
    val name: String,
    val description: String? = null,
    val priority: String = "normal"
  )
}

private fun CreateItemHandler.Request.toCommand() = CreateItemCommand(
  name = name,
  description = description,
  priority = toItemPriority(priority)
)

fun CreateItemHandler(registry: Registry) = CreateItemHandler(
  createItem = CreateItemUseCase(registry)
)
