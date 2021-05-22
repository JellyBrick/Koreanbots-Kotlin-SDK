package be.zvz.koreanbots.v2.dto

data class ResponseWrapper<T> (
    val code: Int,
    val version: Int,
    val data: T?
)
