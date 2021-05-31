package be.zvz.koreanbots.dto

/**
 * 유저 정보를 나타냅니다.
 * @property id 유저의 디스코드 아이디
 * @property username 유저의 디스코드 닉네임
 * @property tag 유저의 디스코드 태그
 * @property github 유저의 깃허브 닉네임(없는 경우 null)
 * @property flags 유저 플래그
 * @property bots 유저가 소유한 봇 리스트
 */
data class User(
    val id: String,
    val username: String,
    val tag: String,
    val github: String?,
    val flags: Int,
    val bots: List<OwnedBot>
) {
    /**
     * 유저가 소유한 봇를 나타냅니다.
     * [owners] 프로퍼티가 문자열 리스트인것을 제외하면 [Bot] 클래스와 동일합니다.
     */
    data class OwnedBot(
        val id: String,
        val name: String,
        val tag: String,
        val avatar: String?,
        val owners: List<String>,
        val flags: Int,
        val lib: String,
        val prefix: String,
        val votes: Int,
        val servers: Int,
        val intro: String,
        val desc: String,
        val web: String,
        val git: String,
        val url: String,
        val discord: String,
        val category: List<Bot.Category>,
        val vanity: String,
        val bg: String,
        val banner: String,
        val status: Bot.Status?,
        val state: Bot.State
    )

    /**
     * 유저의 플래그를 나타냅니다.
     */
    enum class Flag(val value: Int) {
        /** 관리자 */
        ADMIN(1 shl 0),
        /** 버그 헌터 */
        BUG_HUNTER(1 shl 1),
        /** 봇 리뷰어 */
        BOT_REVIEWER(1 shl 2),
        /** 프리미엄 */
        PREMIUM(1 shl 3),
    }
}
