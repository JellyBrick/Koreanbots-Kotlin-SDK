package be.zvz.koreanbots.exception

/**
 * 요청이 실패하였음을 나타냅니다.
 * @property message 간단한 에러 메세지
 * @property errors 오류에 대한 자세한 설명(없는 경우 null)
 * @property code 반환된 HTTP 상태코드
 * @property version 요청을 보낸 API 버전
 */
data class RequestFailedException(
    override val message: String,
    val errors: List<String>?,
    val code: Int,
    val version: Int
) : RuntimeException("Request failed with message: $message")
