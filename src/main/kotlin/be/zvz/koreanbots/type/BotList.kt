package be.zvz.koreanbots.type

data class BotList(
        val code: Int,
        val data: List<Bot>?,
        val totalPage: Int?,
        val currentPage: Int?,
        val message: String?
)