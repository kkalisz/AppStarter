package com.kalisz.kamil.sample

import com.papsign.ktor.openapigen.OpenAPIGen
import com.papsign.ktor.openapigen.openAPIGen
import com.papsign.ktor.openapigen.route.apiRouting
import com.papsign.ktor.openapigen.route.path.normal.get
import com.papsign.ktor.openapigen.route.response.respond
import com.papsign.ktor.openapigen.route.route
import foo.Elo
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.gson.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import org.slf4j.Logger
import org.slf4j.LoggerFactory

val LOG: Logger = LoggerFactory.getLogger("ktor-app")

const val portArgName = "--server.port"
const val defaultPort = 8080

fun Application.module() {

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
        // describe the server, add as many as you want
        server("http://localhost:8080/") {
            description = "Test server"
        }
        //optional custom schema object namer
//        replaceModule(DefaultSchemaNamer, object : SchemaNamer {
//            val regex = Regex("[A-Za-z0-9_.]+")
//            override fun get(type: KType): String {
//                return type.toString().replace(regex) { it.value.split(".").last() }.replace(Regex(">|<|, "), "_")
//            }
//        })
    }
    install(ContentNegotiation){
        gson()
    }
    install(DefaultHeaders)
    install(CallLogging)
    install(Routing) {
        get("/test") {
            call.respond(Elo("Test"))
        }
        get("/openapi.json") {
            call.respond(application.openAPIGen.api.serialize())
        }
        get("/") {
            call.respondRedirect("/swagger-ui/index.html?url=/openapi.json", true)
        }
    }
    apiRouting {
        route("abc"){
            get<String,Elo>{ path ->
                respond(Elo("dss"))
            }
        }
    }
}

fun main(args: Array<String>) {
    val portConfigured = args.isNotEmpty() && args[0].startsWith(portArgName)

    val port = if (portConfigured) {
        LOG.debug("Custom port configured: ${args[0]}")
        args[0].split("=").last().trim().toInt()
    } else defaultPort
    embeddedServer(Netty, port, module = Application::module).start(wait = true)
}
