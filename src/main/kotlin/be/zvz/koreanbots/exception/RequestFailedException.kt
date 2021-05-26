package be.zvz.koreanbots.exception

/**
 * 한국 디스코드봇 리스트 API로 요청을 보냈으나, 실패한 경우 발생합니다.
 * @property message API에서 돌아온 에러 메세지입니다.
 * @property errors 어떤 부분에서 오류가 발생했는지를 알려줍니다. API v2에서만 지원하며, null일 수 있습니다.
 * @property code 요청에 대한 응답의 HTTP 상태코드입니다.
 * @property version 요청을 보낸 API 버전입니다.
 */
class RequestFailedException(
    message: String,
    val errors: List<String>?,
    val code: Int,
    val version: Int
) : RuntimeException(message)
