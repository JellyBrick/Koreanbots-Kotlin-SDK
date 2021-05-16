package be.zvz.koreanbots.dto

data class ResponseWrapper<T> (
    val code: Int,
    val version: Int,
    val data: T?
)
