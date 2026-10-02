package com.example.controllers

import com.example.models.CategoryDto
import com.example.services.CategoryService
import io.ktor.http.HttpStatusCode
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.delete
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.put
import io.ktor.server.routing.route

fun Route.categoryController(){
    val service = CategoryService()
    route("/categories"){
        get {
            call.respond(service.getAll())
        }
        get ("/{id}"){
            val id = call.parameters["id"]?.toIntOrNull()
                ?: return@get call.respond(HttpStatusCode.BadRequest, "ID tidak valid")
            val category = service.getById(id)
            if (category == null) call.respond(HttpStatusCode.NotFound)
            else call.respond(category)
        }
        post {
            val dto =call.receive<CategoryDto>()
            val newId = service.create(dto)
            call.respond(HttpStatusCode.Created, mapOf("id" to newId))
        }
        put("/{id}"){
            val id = call.parameters["id"]?.toIntOrNull()
                ?: return@put call.respond(HttpStatusCode.BadRequest, "ID tidak valid")
            val dto = call.receive<CategoryDto>()
            val success = service.update(id,dto)
            if (!success) call.respond(HttpStatusCode.NotFound)
            else call.respond(HttpStatusCode.OK)

        }
        delete("/{id}"){
            val id = call.parameters["id"]?.toIntOrNull()
                ?: return@delete call.respond(HttpStatusCode.BadRequest, "ID tidak valid")
            val success = service.delete(id)
            if (!success) call.respond(HttpStatusCode.NotFound)
            else call.respond(HttpStatusCode.NoContent)
        }
    }
}