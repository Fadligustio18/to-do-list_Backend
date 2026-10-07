package com.example.services

import com.example.models.Categories
import com.example.models.Tasks
import com.example.models.TaskDto
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

class TaskService {

    fun getAll(categoryId: Int? = null, completed: Boolean? = null): List<TaskDto> = transaction{
        var query = (Tasks innerJoin Categories).selectAll()
        if (categoryId != null){
            query = query.andWhere { Tasks.categoryId eq categoryId}
        }
        if (completed != null){
            query = query.andWhere { Tasks.isCompleted eq completed}
        }
        query.map { toDto(it) }
    }

    fun getById(id: Int): TaskDto? = transaction {
        (Tasks innerJoin  Categories)
            .selectAll().where {Tasks.id eq id}
            .map { toDto(it) }
            .singleOrNull()
    }
    fun create(dto: TaskDto): Int = transaction {
        Tasks.insert {
            it[title] = dto.title
            it[description] = dto.description
            it[isCompleted] = dto.isCompleted
            it[categoryId] = dto.categoryId
        }get Tasks.id
    }
    fun update(id: Int, dto: TaskDto): Boolean = transaction {
        Tasks.update({Tasks.id eq id}) {
            it[title] = dto.title
            it[description] = dto.description
            it[isCompleted] = dto.isCompleted
            it[categoryId] = dto.categoryId
        } > 0
    }

    fun delete(id: Int): Boolean = transaction {
        Tasks.deleteWhere {SqlExpressionBuilder.run { Tasks.id eq id}} > 0
    }
    private fun toDto(row: ResultRow): TaskDto = TaskDto(
        id = row[Tasks.id],
        title = row[Tasks.title],
        description = row[Tasks.description],
        isCompleted = row[Tasks.isCompleted],
        categoryId = row[Tasks.categoryId],
        categoryName = row[Categories.name]
    )
}