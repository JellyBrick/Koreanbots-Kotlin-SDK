package be.zvz.koreanbots.dto

data class Bot(
    val id: String,
    val name: String,
    val tag: String,
    val avatar: String?,
    val owners: List<User>,
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
    val category: List<Category>,
    val vanity: String?,
    val bg: String?,
    val banner: String?,
    val status: Status,
    val state: State
) {
    enum class Category(val apiName: String) {
        MODERATION("관리"),
        MUSIC("뮤직"),
        STATS("전적"),
        GAME("게임"),
        GAMBLING("도박"),
        LOGGING("로깅"),
        SLASH_COMMAND("슬래시 명령어"),
        WEB_DASHBOARD("웹 대시보드"),
        MEME("밈"),
        LEVELING("레벨링"),
        UTILITY("유틸리티"),
        TALK("대화"),
        NSFW("NSFW"),
        SEARCH("검색"),
        SCHOOL("학교"),
        COVID("코로나19"),
        TRANSLATE("번역"),
        OVERWATCH("오버워치"),
        LEAGUE_OF_LEGENDS("리그 오브 레전트"),
        BATTLE_GROUND("배틀그라운드"),
        MINECRAFT("마인크래프트");

        override fun toString() = apiName
    }

    enum class Status(val apiName: String) {
        ONLINE("online"),
        IDLE("idle"),
        DO_NOT_DISTURB("dnd"),
        STREAMING("streaming"),
        OFFLINE("offline");

        override fun toString() = apiName
    }

    enum class State(val apiName: String, val desc: String) {
        OK("ok", "정상"),
        REPORTED("reported", "일시 정지"),
        BLOCKED("blocked", "강제 삭제"),
        PRIVATE("private", "특수 목적 봇"),
        ARCHIVED("archived", "잠금 처리 (지원 종료)");

        override fun toString() = apiName
    }

    enum class Flag(val value: Int, val desc: String) {
        NONE(0 shl 0, "없음"),
        OFFICAL(1 shl 0, "공식 봇"),
        KOREANBOTS_VERIFIED(1 shl 2, "한국 디스코드봇 리스트 인증된 봇"),
        PARTNER(1 shl 3, "파트너"),
        DISCORD_VERIFIED(1 shl 4, "디스코드 인증된 봇"),
        PREMIUM(1 shl 5, "프리미엄"),
        FIRST_HACKATON_WINNER(1 shl 6, "제1회 한국 디스코드봇 리스트 해커톤 우승자 봇");
    }
}
