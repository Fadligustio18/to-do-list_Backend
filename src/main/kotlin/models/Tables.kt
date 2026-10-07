package com.example.models

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.timestamp
import org.jetbrains.exposed.sql.javatime.CurrentTimestamp


object Categories : Table ("categories") {
    val id = integer("id").autoIncrement()
    val name = varchar("name", 100)
    override val primaryKey = PrimaryKey(id)
}
object  Tasks: Table("tasks") {
    val id = integer("id").autoIncrement()
    val title = varchar("title", 100)
    val description = text("description").nullable()
    val isCompleted = bool("is_completed").default(false)
    val categoryId = integer("category_id").references(Categories.id)
    val createdAt = timestamp ("created_at").defaultExpression(CurrentTimestamp)
}
object Users : Table("users"){
    val id = integer("id").autoIncrement()
    val email = varchar("email", 100).uniqueIndex()
    val passwordHash = varchar("password_hash", 255)

    override val primaryKey = PrimaryKey(id)
}