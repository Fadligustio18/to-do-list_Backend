package com.example


import com.example.controllers.categoryController
import com.example.controllers.taskController
import com.example.controllers.userController
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        get("/") {
            call.respondText("Hello, World!")
        }
        get("/json/kotlinx-serialization"){
            call.respond(mapOf("hello" to "world"))
        }
        categoryController()
        taskController()
        userController()
    }
}





