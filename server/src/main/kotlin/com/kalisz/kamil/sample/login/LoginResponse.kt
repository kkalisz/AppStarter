package com.kalisz.kamil.sample.login

import com.kalisz.kamil.sample.common.model.CommonResponse
import com.papsign.ktor.openapigen.annotations.parameters.QueryParam
import com.papsign.ktor.openapigen.content.type.multipart.FormDataRequest


class LoginResponse(data: LoginResponseData? = null, message: String? = null) :
    CommonResponse<LoginResponseData>(data, message) {
}

@FormDataRequest
data class LoginParams(@QueryParam("username") val username: String,
                       @QueryParam("password") val password: String)