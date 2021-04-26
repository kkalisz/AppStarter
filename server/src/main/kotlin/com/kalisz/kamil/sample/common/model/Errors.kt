package com.kalisz.kamil.sample.common.model


data class Errors(val generalError: Error? = null, val errors: List<FieldError> = emptyList()){
    companion object{
        val sample = Errors(com.kalisz.kamil.sample.common.model.Error("Code","general error message"), listOf(FieldError.sample))
    }
}