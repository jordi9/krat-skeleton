package com.jordi9.skeleton.feature.greeting.inbound

import com.jordi9.krat.pack.core.Handler
import com.jordi9.skeleton.feature.greeting.application.GetGreetingUseCase
import io.ktor.server.application.ApplicationCall
import io.ktor.server.response.respondText

class HelloHandler(private val getGreeting: GetGreetingUseCase) : Handler {
  override suspend fun handle(call: ApplicationCall) {
    val name = call.request.queryParameters["name"]
    call.respondText(getGreeting(name))
  }
}

fun HelloHandler(greetingConfig: GreetingConfig) = HelloHandler(
  getGreeting = GetGreetingUseCase(greetingConfig)
)
