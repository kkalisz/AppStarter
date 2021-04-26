package com.kalisz.kamil.sample.common.model

import com.inversoft.error.Error
import com.inversoft.error.Errors


fun Errors?.toDomainErrors(): com.kalisz.kamil.sample.common.model.Errors?{
    if(this == null){
        return null
    }
    val mainError = this.generalErrors.firstOrNull().toDomainError()
    val fieldErrors = this.fieldErrors.map { FieldError(it.key,it.value.mapNotNull { error -> error.toDomainError() } ) }
    return com.kalisz.kamil.sample.common.model.Errors(mainError,fieldErrors)

}

fun Error?.toDomainError(): com.kalisz.kamil.sample.common.model.Error?{
    if(this == null){
        return null
    }
    return com.kalisz.kamil.sample.common.model.Error(this.code,this.message)
}