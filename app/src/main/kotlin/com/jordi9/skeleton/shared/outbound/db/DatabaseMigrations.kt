package com.jordi9.skeleton.shared.outbound.db

import org.flywaydb.core.Flyway

fun runDatabaseMigrations(dbUrl: String) {
  val flyway = Flyway.configure()
    .dataSource(dbUrl, null, null)
    .locations("db/migration")
    .load()
  flyway.migrate()
}
