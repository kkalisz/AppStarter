package com.kalisz.kamil.sample

import com.kalisz.kamil.sample.login.repository.loginRouting
import com.kalisz.kamil.sample.login.service.LoginService
import com.papsign.ktor.openapigen.openAPIGen
import com.papsign.ktor.openapigen.route.apiRouting
import com.papsign.ktor.openapigen.route.path.auth.get
import com.papsign.ktor.openapigen.route.path.normal.get
import com.papsign.ktor.openapigen.route.response.respond
import com.papsign.ktor.openapigen.route.route
import com.typesafe.config.ConfigFactory
import io.fusionauth.domain.User
import io.ktor.application.*
import io.ktor.config.*
import io.ktor.features.*
import io.ktor.gson.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import org.slf4j.Logger
import org.slf4j.LoggerFactory

val LOG: Logger = LoggerFactory.getLogger("ktor-app")

val loginService = LoginService()

@ExperimentalStdlibApi
fun Application.module() {

    install(CORS) {
        header(HttpHeaders.AccessControlAllowHeaders)
        header(HttpHeaders.AccessControlAllowOrigin)
        header(HttpHeaders.AccessControlExposeHeaders)
        header(HttpHeaders.AccessControlAllowCredentials)
        header(HttpHeaders.AccessControlAllowOrigin)

        anyHost()
        allowSameOrigin = true
        method(HttpMethod.Options)
        method(HttpMethod.Put)
        method(HttpMethod.Delete)
        method(HttpMethod.Patch)
        header(HttpHeaders.Authorization)
    }
    installAuth()
    installOpenApi()
    install(ContentNegotiation) {
        gson()
    }
    install(DefaultHeaders)
    install(CallLogging)
    install(Routing) {
        get("/test") {
            call.respond("Test")
        }
        get("/openapi.json") {
            call.respond(application.openAPIGen.api.serialize())
        }
        get("/user/{login}") {
            //call.respondText("Hello, ${call.parameters["login"]}")
            val user = loginService.getUserByEmail(call.parameters["login"])
            if (user != null) {
                call.respond<User>(user)
            } else {
                call.respond("No user found")
            }
        }
        get("/user/{login}/{password}") {
            //call.respondText("Hello, ${call.parameters["login"]}")
            val login = call.parameters["login"]!!
            val password = call.parameters["password"]!!

            loginService.changeUserPassword(login, password)

            call.respond("Ok")

        }
        get("/") {
            call.respondRedirect("/swagger-ui/index.html?url=/openapi.json", true)
        }

        loginRouting()
    }


    apiRouting {
        route("abc") {
            jwtAuth {
                get<String, String, UserPrincipal> {
                    respond("sdf")
                }
            }
            route("elo") {
                get<String, String> { path ->
                    respond("dss")
                }
            }
        }

    }
}


@ExperimentalStdlibApi
fun main(args: Array<String>) {

    val applicationConfig = HoconApplicationConfig(ConfigFactory.load())
    val port = applicationConfig.property("server.port").getString().toInt()
    val host = applicationConfig.property("server.host").getString()

    embeddedServer(
        Netty,
        environment = applicationEngineEnvironment {
            log = LoggerFactory.getLogger("ktor.application")
            config = applicationConfig

            module {
                module()
            }
            connector {
                this.port = port
                this.host = host
            }
        },
    ).start(true)
}

