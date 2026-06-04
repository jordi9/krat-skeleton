package com.jordi9.skeleton

import com.jordi9.krat.otel.OpenTelemetryConfig
import com.jordi9.krat.otel.testlib.OpenTelemetryTestProvider
import com.jordi9.krat.time.TimeClock
import com.jordi9.skeleton.stub.NotificationClientStub
import io.kotest.core.listeners.AfterEachListener
import io.kotest.core.test.TestCase
import io.kotest.engine.test.TestResult

class TestStubs(
  val clock: TimeClock = sharedClock(),
  val notification: NotificationClientStub = NotificationClientStub(),
  val openTelemetry: OpenTelemetryTestProvider = OpenTelemetryTestProvider(
    OpenTelemetryConfig(serviceName = "skeleton-test")
  )
) {

  fun resetAll() {
    notification.reset()
  }
}

val Stubs = TestStubs()
val NotificationStub = Stubs.notification

object ResetStubsExtension : AfterEachListener {
  override suspend fun afterEach(testCase: TestCase, result: TestResult) {
    Stubs.resetAll()
  }
}
