package be.zvz.koreanbots.type

data class RequestException(val code: Int, val errorMessage: String) : RuntimeException(errorMessage)