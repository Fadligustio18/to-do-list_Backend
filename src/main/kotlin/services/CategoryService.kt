package com.example.services

import com.example.models.Categories
import com.example.models.CategoryDto
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update

class CategoryService {
    fun getAll(): List<CategoryDto> = transaction{
        Categories.selectAll().map{
            CategoryDto(it[Categories.id],it[Categories.name])
        }
    }
    fun getById(id: Int): CategoryDto? = transaction{
        Categories.selectAll().where {Categories.id eq id}
            .map { CategoryDto(it[Categories.id], it[Categories.name])}
            .singleOrNull()
    }
    fun create(dto: CategoryDto): Int = transaction{
        Categories.insert{
            it[name] = dto.name
        }get Categories.id
    }

    fun update(id: Int, dto: CategoryDto) : Boolean = transaction{
        Categories.update({ Categories.id eq id}){
            it[name] = dto.name
        } > 0
    }
    fun delete(id : Int): Boolean = transaction{
        Categories.deleteWhere { Categories.id eq id} > 0
    }
}