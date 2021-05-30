package be.zvz.koreanbots.dto

data class SearchResult(
    val type: String,
    val data: List<Bot>,
    val currentPage: Int,
    val totalPage: Int
)
