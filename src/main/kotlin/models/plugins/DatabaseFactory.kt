package com.example.models.plugins

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.ktor.server.application.*
import org.jetbrains.exposed.sql.Database
import javax.sql.DataSource

fun Application.configureDatabases(){
    val dataSource = hikari()
    Database.connect(dataSource)
}
private fun hikari(): DataSource {
    val config = HikariConfig().apply {
        driverClassName = "org.mariadb.jdbc.Driver"
        jdbcUrl = System.getenv("DB_URL")?: "jdbc:mariadb://localhost:3306/belajar"
        username = System.getenv("USER")?: "root"
        password = System.getenv("PASSWORD")?: "120807"
        maximumPoolSize = 5
        isAutoCommit = false
        transactionIsolation = "TRANSACTION_REPEATABLE_READ"
        validate()
    }
    return HikariDataSource(config)
}