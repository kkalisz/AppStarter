package com.kalisz.kamil.sample.common.model

data class FieldError(val name: String, val errors: List<Error>){
    companion object{
        val sample = FieldError("field", listOf(com.kalisz.kamil.sample.common.model.Error("ErrorCode","field error message")))
    }
}