package com.jordi9.skeleton.fixture

import com.jordi9.krat.jdbi.handleSync
import com.jordi9.skeleton.feature.item.domain.ItemId
import com.jordi9.skeleton.feature.item.outbound.ItemPriorityMapper
import com.jordi9.skeleton.jdbi

data class ItemRow(val id: ItemId)

object ItemTable {

  fun insert(example: ItemExample): ItemRow {
    jdbi().handleSync {
      createUpdate(
        """
        INSERT INTO items (id, name, description, priority, created_at, updated_at)
        VALUES (:id, :name, :description, :priority, :createdAt, :updatedAt)
        """.trimIndent()
      )
        .bind("id", example.id.value)
        .bind("name", example.name)
        .bind("description", example.description)
        .bind("priority", ItemPriorityMapper.toDatabase(example.priority))
        .bind("createdAt", example.createdAt.toEpochMilli())
        .bind("updatedAt", example.updatedAt.toEpochMilli())
        .execute()
    }
    return ItemRow(id = example.id)
  }

  fun deleteAll() {
    jdbi().handleSync {
      execute("DELETE FROM items")
    }
  }
}
