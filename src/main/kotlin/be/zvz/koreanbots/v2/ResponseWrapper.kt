package be.zvz.koreanbots.v2

data class ResponseWrapper<T> (
    val code: Int,
    val version: Int,
    val data: T?
)
