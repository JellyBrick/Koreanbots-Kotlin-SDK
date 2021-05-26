package be.zvz.koreanbots.exception

/**
 * 요청이 성공하였으나, 응답에 바디가 없거나 그 내용이 잘못되었음을 의미합니다.
 * API의 오류이거나, 응답 객체의 구조가 바뀌었을 수 있습니다.
 */
class InvalidDataReceivedException(message: String) : Exception(message)
