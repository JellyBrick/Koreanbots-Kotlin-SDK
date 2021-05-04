package be.zvz.koreanbots.dto

data class User(
    val id: String,
    val username: String,
    val tag: String,
    val github: String?,
    val flags: Int,
    val bots: List<OwnedBot>
) {
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
        val web: String?,
        val git: String?,
        val url: String?,
        val discord: String?,
        val category: List<Bot.Category>,
        val vanity: String?,
        val bg: String?,
        val banner: String?,
        val status: Bot.Status,
        val state: Bot.State
    )

    enum class Flag(val value: Int, val desc: String) {
        NONE(0 shl 0, "없음"),
        ADMIN(1 shl 0, "관리자"),
        BUG_HUNTER(1 shl 1, "버그 헌터"),
        BOT_REVIEWER(1 shl 2, "봇 리뷰어"),
        PREMIUM(1 shl 3, "프리미엄"),
    }
}
