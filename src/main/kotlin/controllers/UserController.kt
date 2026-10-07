package com.example.controllers

import com.example.models.UserDto
import com.example.services.UserService
import io.ktor.http.HttpStatusCode
import io.ktor.server.request.receive
import io.ktor.server.response.*
import io.ktor.server.routing.Route
import io.ktor.server.routing.post
import io.ktor.server.routing.route

fun Route.userController() {
    val service = UserService()
    route("/auth"){
        post("/register"){
            val dto = call.receive<UserDto>()
            val error = dto.validate()
            if (error != null) return@post call.respond(HttpStatusCode.BadRequest, error)

            val newId = service.register(dto.email, dto.password)
            if (newId == null){
                call.respond(HttpStatusCode.Conflict, "Email Sudah Terdaftar")
            }else{
                call.respond(HttpStatusCode.Created, mapOf("id" to newId))
            }
        }
    }
}