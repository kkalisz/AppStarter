package com.kalisz.kamil.sample

import foo.Elo
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
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
    install(DefaultHeaders)
    install(CallLogging)
    install(Routing) {
        get("/") {
            call.respondText(
                "My Example Blog2 ${
                Elo("xxx").id}",
                ContentType.Text.Html
            )
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
