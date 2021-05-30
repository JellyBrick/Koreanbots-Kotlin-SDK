package be.zvz.koreanbots.dto

/**
 * 유저가 투표했는지를 나타냅니다.
 * @property voted 최근 12시간 이내 투표 여부
 * @property lastVote 타임스탬프(한 번도 하지 않은 경우 0)
 */
data class Voted(
    val voted: Boolean,
    val lastVote: Long
)
