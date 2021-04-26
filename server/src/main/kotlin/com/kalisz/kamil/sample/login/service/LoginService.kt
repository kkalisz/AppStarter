package com.kalisz.kamil.sample.login.service

import com.inversoft.error.Errors
import com.inversoft.rest.ClientResponse
import com.kalisz.kamil.sample.common.model.toDomainErrors
import io.fusionauth.client.FusionAuthClient
import io.fusionauth.domain.User
import io.fusionauth.domain.api.UserRequest
import io.fusionauth.domain.api.UserResponse
import io.ktor.http.*
import java.util.*


class LoginService {
    private val client: FusionAuthClient
    private val applicationUuid = UUID.fromString("af600a62-cf3d-4445-a78f-8f453b36cf84")

    fun getUserByEmail(email: String?): User? {
        val response: ClientResponse<UserResponse, Errors?> = client.retrieveUserByEmail(email)
        if (response.wasSuccessful()) {
            return response.successResponse.user
        } else if (response.errorResponse != null) {
            // Error Handling
            val errors: Errors? = response.errorResponse
        } else if (response.exception != null) {
            // Exception Handling
            val exception = response.exception
        }
        return null
    }

    fun changeUserPassword(userName: String, password: String): User? {
        val user = getUserByEmail(userName)!!
        user.password = password
        val userId = user.id
        val userRequest = UserRequest(false, true, user)
        val response = client.updateUser(userId, userRequest)
        if (response.wasSuccessful()) {
            return response.successResponse.user
        } else if (response.errorResponse != null) {
            // Error Handling
            val errors: Errors? = response.errorResponse
        } else if (response.exception != null) {
            // Exception Handling
            val exception = response.exception
        }
        return null
    }

//    fun loginUserByPassword(loginParams: LoginParams) : LoginResponse{
//
//        val response: ClientResponse<UserResponse, Errors?> = client.retrieveUserByEmail(loginParams.username)
//        if(!response.wasSuccessful()){
//
//        }
//    }

//    fun userRouting(routing: Routing) {
//        routing.apply {
//            apiRouting {
//                route("user") {
//                    route("login") {
//                        throws(HttpStatusCode.Unauthorized, GenericErrorResponse.sample, Throwable::class) {
//                            post<String, LoginResponse, LoginParams> { path, loginParams ->
//                                val response: ClientResponse<UserResponse, Errors?> = client.retrieveUserByEmail(loginParams.username)
//                                if (!response.wasSuccessful()) {
//                                    responder.respond(
//                                        HttpStatusCode(response.status, description = ""),
//                                        GenericErrorResponse(errors = response.errorResponse.toDomainErrors()),
//                                        pipeline
//                                    )
//                                    return@post
//                                }
//                                val loginResponse = client.login(LoginRequest(applicationUuid, loginParams.username, loginParams.password))
//                                if (!loginResponse.wasSuccessful()) {
//                                    responder.respond(
//                                        HttpStatusCode(loginResponse.status, description = ""),
//                                        GenericErrorResponse(errors = loginResponse.errorResponse.toDomainErrors()),
//                                        pipeline
//                                    )
//                                    return@post
//                                }
//                                val loginDataResponse = loginResponse.successResponse
//                                val responseData = LoginResponseData(loginDataResponse.token, loginDataResponse.refreshToken)
//                                respond(LoginResponse(responseData))
//                            }
//                        }
//                    }
//                }
//            }
//        }
//    }


    init {
        client = FusionAuthClient("ZXlUcBimpyX1PvuwvMAh-N4nzjSgZX7j0POuub7jNBeQukVkRbVwLOKm", "http://192.168.1.111:9011")
    }


}

fun <T> ClientResponse<T, Errors>.toErrors(): com.kalisz.kamil.sample.common.model.Errors? {
    if (wasSuccessful()) {
        return null
    }
    if (errorResponse != null) {
        return errorResponse.toDomainErrors()
    }
    if (exception != null) {
        return com.kalisz.kamil.sample.common.model.Errors(
            com.kalisz.kamil.sample.common.model.Error(
                HttpStatusCode.InternalServerError.toString(),
                ""
            )
        )
    }
    return null
}