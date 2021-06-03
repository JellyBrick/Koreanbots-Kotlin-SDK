package be.zvz.koreanbots.dto

internal data class ResponseWrapper<T> (
    val code: Int,
    val version: Int,
    val data: T?,
    val message: String?
)
