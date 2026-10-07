package com.example.services

import com.example.models.Users
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import org.mindrot.jbcrypt.BCrypt
class UserService {
    fun register(email: String, password:String): Int? = transaction{
        val exists = Users.selectAll().where { Users.email eq email}.count() > 0
        if (exists) return@transaction null

        val hash =BCrypt.hashpw(password, BCrypt.gensalt())
        Users.insert{
            it[Users.email] = email
            it[Users.passwordHash] = hash
        }get Users.id

    }

    fun findByEmail(email: String): Pair<Int, String>? = transaction {
        Users.selectAll().where {Users.email eq email}
            .map { it[Users.id] to it [Users.passwordHash]}
            .singleOrNull()
    }
    fun verifyPassword(plain: String, hash: String): Boolean{
        return BCrypt.checkpw(plain, hash)
    }
}