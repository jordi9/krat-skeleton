package com.jordi9.skeleton.feature.item.domain

import com.jordi9.skeleton.shared.domain.NanoId

@JvmInline
value class ItemId(
  val value: String
) {
  init {
    require(NanoId.isValid(PREFIX, value)) { "Invalid item ID format" }
  }

  companion object {
    const val PREFIX = "item_"
  }
}
