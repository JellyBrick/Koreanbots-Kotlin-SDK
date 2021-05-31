package be.zvz.koreanbots.dto

/**
 * 검색결과를 나타냅니다.
 * @property type 검색 종류(예: `SEARCH`, `VOTE`)
 * @property data 검색 결과
 * @property currentPage 현재 페이지
 * @property totalPage 전체 페이지
 */
data class SearchResult(
    val type: String,
    val data: List<Bot>,
    val currentPage: Int,
    val totalPage: Int
)
