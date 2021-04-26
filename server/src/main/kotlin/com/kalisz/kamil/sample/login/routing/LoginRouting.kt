package com.kalisz.kamil.sample.login.repository

import com.inversoft.error.Errors
import com.inversoft.rest.ClientResponse
import com.kalisz.kamil.sample.common.model.GenericErrorResponse
import com.kalisz.kamil.sample.common.model.toDomainErrors
import com.kalisz.kamil.sample.login.LoginParams
import com.kalisz.kamil.sample.login.LoginResponse
import com.kalisz.kamil.sample.login.LoginResponseData
import com.papsign.ktor.openapigen.route.apiRouting
import com.papsign.ktor.openapigen.route.path.normal.post
import com.papsign.ktor.openapigen.route.response.respond
import com.papsign.ktor.openapigen.route.route
import com.papsign.ktor.openapigen.route.throws
import io.fusionauth.client.FusionAuthClient
import io.fusionauth.domain.api.LoginRequest
import io.fusionauth.domain.api.UserResponse
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.routing.*
import java.util.*

fun Application.loginRouting(){

    val applicationUuid = UUID.fromString("af600a62-cf3d-4445-a78f-8f453b36cf84")
    val client = FusionAuthClient("ZXlUcBimpyX1PvuwvMAh-N4nzjSgZX7j0POuub7jNBeQukVkRbVwLOKm", "http://192.168.1.111:9011")

    routing {
        apiRouting {
            route("user") {
                route("login") {
                    throws(HttpStatusCode.Unauthorized, GenericErrorResponse.sample, Throwable::class) {
                        post<String, LoginResponse, LoginParams> { path, loginParams ->
                            val response: ClientResponse<UserResponse, Errors?> = client.retrieveUserByEmail(loginParams.username)
                            if (!response.wasSuccessful()) {
                                responder.respond(
                                    HttpStatusCode(response.status, description = ""),
                                    GenericErrorResponse(errors = response.errorResponse.toDomainErrors()),
                                    pipeline
                                )
                                return@post
                            }
                            val loginResponse = client.login(LoginRequest(applicationUuid, loginParams.username, loginParams.password))
                            if (!loginResponse.wasSuccessful()) {
                                responder.respond(
                                    HttpStatusCode(loginResponse.status, description = ""),
                                    GenericErrorResponse(errors = loginResponse.errorResponse.toDomainErrors()),
                                    pipeline
                                )
                                return@post
                            }
                            val loginDataResponse = loginResponse.successResponse
                            val responseData = LoginResponseData(loginDataResponse.token, loginDataResponse.refreshToken)
                            respond(LoginResponse(responseData))
                        }
                    }
                }
            }
        }
    }
}