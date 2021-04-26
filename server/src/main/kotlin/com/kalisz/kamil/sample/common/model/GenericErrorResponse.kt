package com.kalisz.kamil.sample.common.model

open class GenericErrorResponse(val message: String? = "", val errors: Errors? = null){
    companion object{
        val sample = GenericErrorResponse("error message", errors = Errors.sample)
    }
}