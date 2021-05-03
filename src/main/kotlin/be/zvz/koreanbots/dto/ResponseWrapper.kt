package be.zvz.koreanbots.dto

data class ResponseWrapper<T> (
    val code: Int,
    val data: T?,
    val version: Int,
    val message: String
)
