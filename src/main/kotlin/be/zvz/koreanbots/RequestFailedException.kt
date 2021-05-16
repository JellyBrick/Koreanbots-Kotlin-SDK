package be.zvz.koreanbots

data class RequestFailedException(
    override val message: String,
    val errors: List<String>?,
    val code: Int,
    val version: Int
) : RuntimeException()
