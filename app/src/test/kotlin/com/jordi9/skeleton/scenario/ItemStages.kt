package com.jordi9.skeleton.scenario

import com.jordi9.kogiven.StageContext
import com.jordi9.kogiven.required
import com.jordi9.krat.pack.test.JsonResponse
import com.jordi9.krat.pack.test.toJsonResponse
import com.jordi9.skeleton.NotificationStub
import com.jordi9.skeleton.feature.item.domain.ItemId
import com.jordi9.skeleton.fixture.ItemExample
import com.jordi9.skeleton.fixture.ItemTable
import com.jordi9.skeleton.httpClient
import com.jordi9.skeleton.sharedClock
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldStartWith
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType

class ItemContext {
  var status: HttpStatusCode by required()
  var response: JsonResponse by required()
  val insertedItemIds: MutableList<ItemId> = mutableListOf()
}

class GivenItem : StageContext<GivenItem, ItemContext>() {

  fun `no items exist`() = apply {
    // Database is empty by default
  }

  fun `an item exists`(name: String, description: String? = null) = apply {
    val item = ItemExample(name = name, description = description)
    val row = ItemTable.insert(item)
    ctx.insertedItemIds.add(row.id)
  }
}

class WhenItem : StageContext<WhenItem, ItemContext>() {

  suspend fun `listing all items`() = apply {
    val response = httpClient().get("/api/v1/items")
    ctx.status = response.status
    if (response.status == HttpStatusCode.OK) {
      ctx.response = response.toJsonResponse()
    }
  }

  suspend fun `creating an item`(name: String, description: String? = null, priority: String? = null) = apply {
    val fields = buildList {
      add("\"name\": \"$name\"")
      description?.let { add("\"description\": \"$it\"") }
      priority?.let { add("\"priority\": \"$it\"") }
    }
    val response = httpClient().post("/api/v1/items") {
      contentType(ContentType.Application.Json)
      setBody("{${fields.joinToString(", ")}}")
    }
    ctx.status = response.status
    if (response.status == HttpStatusCode.Created) {
      ctx.response = response.toJsonResponse()
    }
  }

  suspend fun `creating an item with invalid priority`() = apply {
    val response = httpClient().post("/api/v1/items") {
      contentType(ContentType.Application.Json)
      setBody("""{"name": "Buy milk", "priority": "urgent"}""")
    }
    ctx.status = response.status
  }

  suspend fun `getting item by id`() = apply {
    val response = httpClient().get("/api/v1/items/${ctx.insertedItemIds.last().value}")
    ctx.status = response.status
    if (response.status == HttpStatusCode.OK) {
      ctx.response = response.toJsonResponse()
    }
  }

  suspend fun `getting item by unknown id`() = apply {
    val response = httpClient().get("/api/v1/items/item_000000000000000000000")
    ctx.status = response.status
  }
}

class ThenItem : StageContext<ThenItem, ItemContext>() {

  fun `the response is successful`() = apply {
    ctx.status shouldBe HttpStatusCode.OK
  }

  fun `the item was created`() = apply {
    ctx.status shouldBe HttpStatusCode.Created
  }

  fun `the response is not found`() = apply {
    ctx.status shouldBe HttpStatusCode.NotFound
  }

  fun `the response is bad request`() = apply {
    ctx.status shouldBe HttpStatusCode.BadRequest
  }

  fun `no items are returned`() = apply {
    ctx.response.isEmpty() shouldBe true
  }

  fun `items are returned`(count: Int) = apply {
    ctx.response.items().size shouldBe count
  }

  fun `the item id is public`() = apply {
    ctx.response.string("id") shouldStartWith "item_"
  }

  fun `the item has name`(expected: String) = apply {
    ctx.response.string("name") shouldBe expected
  }

  fun `the item has description`(expected: String) = apply {
    ctx.response.string("description") shouldBe expected
  }

  fun `the item has no description`() = apply {
    ctx.response.stringOrNull("description") shouldBe null
  }

  fun `the item has priority`(expected: String) = apply {
    ctx.response.string("priority") shouldBe expected
  }

  fun `timestamps are current`() = apply {
    ctx.response.string("createdAt") shouldBe sharedClock().now().toString()
    ctx.response.string("updatedAt") shouldBe sharedClock().now().toString()
  }

  fun `a notification was sent`(expected: String) = apply {
    NotificationStub.notifications shouldContain expected
  }
}
