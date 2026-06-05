package com.jordi9.skeleton.scenario

import com.jordi9.kogiven.ScenarioStringSpec

class ItemShould : ScenarioStringSpec<GivenItem, WhenItem, ThenItem, ItemContext>({

  "return empty list when no items exist" {
    Given.`no items exist`()
    When.`listing all items`()
    Then.`the response is successful`()
      .and().`no items are returned`()
  }

  "create an item" {
    Given.`no items exist`()
    When.`creating an item`("Buy milk", "From the store", priority = "high")
    Then.`the item was created`()
      .and().`the item id is public`()
      .and().`the item has name`("Buy milk")
      .and().`the item has description`("From the store")
      .and().`the item has priority`("high")
      .and().`timestamps are current`()
      .and().`a notification was sent`("Item created: Buy milk")
  }

  "create an item without description" {
    Given.`no items exist`()
    When.`creating an item`("Buy milk")
    Then.`the item was created`()
      .and().`the item has name`("Buy milk")
      .and().`the item has no description`()
      .and().`the item has priority`("normal")
  }

  "reject invalid item priority" {
    Given.`no items exist`()
    When.`creating an item with invalid priority`()
    Then.`the response is bad request`()
  }

  "return items after creating them" {
    Given.`an item exists`("First item")
      .and().`an item exists`("Second item")
    When.`listing all items`()
    Then.`the response is successful`()
      .and().`items are returned`(2)
  }

  "return an item by id" {
    Given.`an item exists`("Test item", "Some description")
    When.`getting item by id`()
    Then.`the response is successful`()
      .and().`the item has name`("Test item")
      .and().`the item has description`("Some description")
  }

  "return 404 when item does not exist" {
    Given.`no items exist`()
    When.`getting item by unknown id`()
    Then.`the response is not found`()
  }
})
