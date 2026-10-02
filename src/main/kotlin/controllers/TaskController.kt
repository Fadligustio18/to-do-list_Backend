package com.example.controllers

import com.example.models.TaskDto
import com.example.services.TaskService
import io.ktor.http.HttpStatusCode
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.delete
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.put
import io.ktor.server.routing.route

fun Route.taskController(){
    val service = TaskService()

    route("/tasks"){
        get {
            call.respond(service.getAll())
        }
        get("/id"){
            val id = call.parameters["id"]?.toIntOrNull()
                ?: return@get call.respond(HttpStatusCode.BadRequest, "ID tidak valid ")
            val task = service.getById(id)
            if (task == null) call.respond(HttpStatusCode.NotFound)
            else call.respond(task)
        }
        post {
            val dto = call.receive<TaskDto>()
            val newId = service.create(dto)
            call.respond(HttpStatusCode.Created, mapOf("id" to newId))
        }

        put("/{id}"){
            val id = call.parameters["id"]?.toIntOrNull()
                ?:return@put call.respond(HttpStatusCode.BadRequest, "ID tidak valid")
            val dto = call.receive<TaskDto>()
            val success = service.update(id, dto)
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