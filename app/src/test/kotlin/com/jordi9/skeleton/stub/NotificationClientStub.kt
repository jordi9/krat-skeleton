package com.jordi9.skeleton.stub

import com.jordi9.skeleton.feature.item.domain.NotificationClient

class NotificationClientStub : NotificationClient {
  val notifications = mutableListOf<String>()

  override fun notify(message: String) {
    notifications.add(message)
  }

  fun reset() {
    notifications.clear()
  }
}
