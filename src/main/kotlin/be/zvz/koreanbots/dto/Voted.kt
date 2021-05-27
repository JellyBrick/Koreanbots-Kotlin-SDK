package be.zvz.koreanbots.dto

/**
 * 유저가 봇을 투표했는지 나타냅니다.
 * @property voted 유저가 투표하면 true, 투표하지 않았으면 false입니다.
 * @property lastVote 가장 최근 투표한 시간을 나타냅니다. 한번도 투표하지 않은 경우는 0입니다. API v2에서만 지원합니다.
 */
interface Voted {
    val voted: Boolean
    val lastVote: Long get() = throw UnsupportedOperationException("Only Supported on API v2")
}
