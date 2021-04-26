package com.kalisz.kamil.sample

import com.papsign.ktor.openapigen.OpenAPIGen
import com.papsign.ktor.openapigen.schema.namer.DefaultSchemaNamer
import com.papsign.ktor.openapigen.schema.namer.SchemaNamer
import io.ktor.application.*
import io.ktor.routing.*
import kotlin.reflect.KType

fun Application.installOpenApi(){
    val port = environment.config.property("server.port").getString().toInt()
    val host = environment.config.property("server.host").getString()
    install(OpenAPIGen) {
        // basic info
        info {
            version = "0.0.1"
            title = "Test API"
            description = "The Test API"
            contact {
                name = "Support"
                email = "support@test.com"
            }
        }
        //addModules(authProvider)
        // describe the server, add as many as you want
        server("http://localhost:$port/") {
            description = "localhost"
        }
        server("https://$host:$port/") {
            description = "Release"
        }
        //optional custom schema object namer
        replaceModule(DefaultSchemaNamer, object: SchemaNamer {
            val regex = Regex("[A-Za-z0-9_.]+")
            override fun get(type: KType): String {
                return type.toString().replace(regex) { it.value.split(".").last() }.replace(Regex(">|<|, "), "_")
            }
        })
    }

    routing {

    }
}