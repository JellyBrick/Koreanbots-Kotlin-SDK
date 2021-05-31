package be.zvz.koreanbots.dto

/**
 * 봇 정보를 나타냅니다.
 * @property id 봇의 아이디
 * @property name 봇의 이름
 * @property tag 봇의 태그
 * @property avatar 봇의 아바타(없는 경우 null)
 * @property owners 봇의 소유자 리스트
 * @property flags 봇의 플래그
 * @property lib 봇이 사용한 라이브러리
 * @property prefix 봇의 접두사
 * @property votes 봇의 투표수
 * @property servers 봇이 참가한 서버 수
 * @property intro 봇의 짧은 설명
 * @property desc 봇의 자세한 설명
 * @property web 봇의 웹사이트(없는 경우 빈 문자열)
 * @property git 봇의 깃허브 닉네임(없는 경우 빈 문자열)
 * @property url 봇 초대 링크(없는 경우 빈 문자열)
 * @property discord 봇의 지원 디스코드 참가 링크(없는 경우 빈 문자열)
 * @property category 봇의 카테고리
 * @property vanity 봇의 VANITY URL(없는 경우 빈 문자열)
 * @property bg 봇의 배경 이미지 URL(없는 경우 빈 문자열)
 * @property banner 봇의 배너 이미지 URL(없는 경우 빈 문자열)
 * @property status 봇의 유저 상태(없는 경우 null)
 * @property state 한국 디스코드봇 리스트에서의 봇의 상태
 */
data class Bot(
    val id: String,
    val name: String,
    val tag: String,
    val avatar: String?,
    val owners: List<BotOwner>,
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
    val category: List<Category>,
    val vanity: String,
    val bg: String,
    val banner: String,
    val status: Status?,
    val state: State
) {
    /**
     * 봇의 카테고리를 나타냅니다.
     */
    enum class Category(private val apiName: String) {
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

    /**
     * 봇의 유저 상태를 나타냅니다.
     */
    enum class Status(private val apiName: String) {
        /** 온라인 */
        ONLINE("online"),
        /** 자리 비움 */
        IDLE("idle"),
        /** 다른 용무 중 */
        DO_NOT_DISTURB("dnd"),
        /** 방송중 */
        STREAMING("streaming"),
        /** 오프라인 */
        OFFLINE("offline");

        override fun toString() = apiName
    }

    /**
     * 한국 디스코드봇 리스트에서의 봇의 상태를 나타냅니다.
     * @property OK 정상
     */
    enum class State(private val apiName: String) {
        /** 정상 */
        OK("ok"),
        /** 일시 정지 */
        REPORTED("reported"),
        /** 강제 삭제 */
        BLOCKED("blocked"),
        /** 특수 목적 봇 */
        PRIVATE("private"),
        /** 잠금 처리 (지원 종료) */
        ARCHIVED("archived");

        override fun toString() = apiName
    }

    /**
     * 봇의 플래그를 나타냅니다.
     */
    enum class Flag(private val value: Int) {
        /** 공식 봇 */
        OFFICAL(1 shl 0),
        /** 	한국 디스코드봇 리스트 인증된 봇 */
        KOREANBOTS_VERIFIED(1 shl 2),
        /** 파트너 */
        PARTNER(1 shl 3),
        /** 디스코드 인증된 봇 */
        DISCORD_VERIFIED(1 shl 4),
        /** 프리미엄 */
        PREMIUM(1 shl 5),
        /** 제1회 한국 디스코드봇 리스트 해커톤 우승자 봇 */
        FIRST_HACKATON_WINNER(1 shl 6);
    }

    /**
     * 봇의 소유자를 나타냅니다.
     * [bots] 프로퍼티가 문자열 리스트인것을 제외하면 [User] 클래스와 동일합니다.
     */
    data class BotOwner(
        val id: String,
        val username: String,
        val tag: String,
        val github: String?,
        val flags: Int,
        val bots: List<String>
    )
}
