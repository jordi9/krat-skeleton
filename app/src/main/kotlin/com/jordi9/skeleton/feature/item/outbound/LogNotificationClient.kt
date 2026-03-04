package com.jordi9.skeleton.feature.item.outbound

import com.jordi9.krat.otel.withSpan
import com.jordi9.skeleton.feature.item.domain.NotificationClient
import io.opentelemetry.api.OpenTelemetry

class LogNotificationClient(openTelemetry: OpenTelemetry) : NotificationClient {

  private val tracer = openTelemetry.getTracer("skeleton.notifications")

  override fun notify(message: String) {
    tracer.withSpan("notification.send", { setAttribute("notification.message", message) }) {}
  }
}
