package com.jordi9.skeleton.feature.item.outbound

import com.jordi9.krat.jdbi.handle
import com.jordi9.skeleton.Registry
import com.jordi9.skeleton.feature.item.domain.Item
import com.jordi9.skeleton.feature.item.domain.ItemId
import org.jdbi.v3.core.Jdbi
import org.jdbi.v3.core.kotlin.mapTo
import org.jdbi.v3.core.mapper.RowMapper
import org.jdbi.v3.core.statement.StatementContext
import java.sql.ResultSet
import java.time.Instant

class ItemRepository(
  private val jdbi: Jdbi
) {

  suspend fun findAll(): List<Item> = jdbi.handle {
    createQuery("SELECT * FROM items ORDER BY created_at DESC")
      .mapTo<Item>()
      .list()
  }

  suspend fun findById(id: ItemId): Item? = jdbi.handle {
    createQuery("SELECT * FROM items WHERE id = :id")
      .bind("id", id.value)
      .mapTo<Item>()
      .findOne()
      .orElse(null)
  }

  suspend fun save(item: Item): Item = jdbi.handle {
    createQuery(
      """
      INSERT INTO items (id, name, description, priority, created_at, updated_at)
      VALUES (:id, :name, :description, :priority, :createdAt, :updatedAt)
      RETURNING *
      """.trimIndent()
    )
      .bind("id", item.id.value)
      .bind("name", item.name)
      .bind("description", item.description)
      .bind("priority", ItemPriorityMapper.toDatabase(item.priority))
      .bind("createdAt", item.createdAt.toEpochMilli())
      .bind("updatedAt", item.updatedAt.toEpochMilli())
      .mapTo<Item>()
      .one()
  }

  suspend fun deleteAll() = jdbi.handle {
    execute("DELETE FROM items")
  }
}

class ItemRowMapper : RowMapper<Item> {

  override fun map(rs: ResultSet, ctx: StatementContext): Item = Item(
    id = ItemId(rs.getString("id")),
    name = rs.getString("name"),
    description = rs.getString("description"),
    priority = ItemPriorityMapper.toDomain(rs.getString("priority")),
    createdAt = Instant.ofEpochMilli(rs.getLong("created_at")),
    updatedAt = Instant.ofEpochMilli(rs.getLong("updated_at"))
  )
}

fun ItemRepository(registry: Registry) = ItemRepository(
  jdbi = registry.jdbi
)

internal fun registerItemMappers(jdbi: Jdbi) {
  jdbi.registerRowMapper(Item::class.java, ItemRowMapper())
}
