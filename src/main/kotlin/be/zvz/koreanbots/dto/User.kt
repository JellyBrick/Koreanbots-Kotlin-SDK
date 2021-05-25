package be.zvz.koreanbots.dto

import java.util.EnumSet

/**
 * 유저 정보를 나타냅니다.
 * @property id 유저의 디스코드 아이디입니다.
 * @property username 유저의 디스코드 닉네임입니다.
 * @property tag 유저의 디스코드 태그입니다.
 * @property github 유저의 깃허브 닉네임입니다. 등록하지 않은 경우 null입니다.
 * @property flags 유저 플래그입니다.
 * @property bots 유저가 소유한 봇의 목록입니다.
 */
interface User {
    val id: String
    val username: String
    val tag: String
    val github: String?
    val flags: EnumSet<Flag>
    val bots: List<OwnedBot>

    /**
     * 유저 플래그를 나타냅니다.
     * @property value 플래그 값입니다.
     */
    enum class Flag(val value: Int) {
        /** 관리자임을 나타냅니다. */
        ADMIN(1 shl 0),
        /** 버그 헌터임을 나타냅니다. */
        BUG_HUNTER(1 shl 1),
        /** 봇 리뷰어임을 나타냅니다. */
        BOT_REVIEWER(1 shl 2),
        /** 프리미엄 유저임을 나타냅니다. */
        PREMIUM(1 shl 3),
    }

    /**
     * 유저가 소유한 봇을 나타냅니다.
     * [owners] 프로퍼티가 문자열 리스트라는 것을 제외하면 [Bot]와 같습니다.
     * API v2에서만 사용합니다.
     * @see User
     */
    data class OwnedBot(
        val id: String,
        val name: String,
        val tag: String,
        val votes: Int,
        val servers: Int,
        val intro: String,
        val url: String?,
        val category: List<Bot.Category>,
        val vanity: String?,
        val bg: String?,
        val banner: String?,
        val state: Bot.State,
        val avatar: String?,
        val owners: List<String>,
        val flags: EnumSet<Bot.Flag>,
        val lib: String,
        val prefix: String,
        val desc: String,
        val web: String?,
        val git: String?,
        val discord: String?,
        val status: Bot.Status?,
    )
}
