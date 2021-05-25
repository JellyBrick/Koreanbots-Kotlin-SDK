package be.zvz.koreanbots.dto

import java.util.EnumSet

/**
 * 봇 정보를 나타냅니다.
 * @property id 봇의 아이디입니다.
 * @property name 봇의 이름입니다.
 * @property tag 봇의 태그입니다.
 * @property avatar 봇의 아바타입니다. 없는 경우 null입니다. API v2에서만 지원합니다.
 * @property owners 봇의 소유자의 목록입니다. API v2에서만 지원합니다.
 * @property flags 봇의 플래그입니다. API v2에서만 지원합니다.
 * @property lib 봇이 사용한 라이브러리입니다. API v2에서만 지원합니다.
 * @property prefix 봇의 접두사입니다. API v2에서만 지원합니다.
 * @property votes 봇의 투표입니다.
 * @property servers 봇이 참가한 서버 수입니다.
 * @property intro 봇의 짧은 설명입니다.
 * @property desc 봇의 긴 설명입니다. API v2에서만 지원합니다.
 * @property web 봇의 웹사이트입니다. 없는 경우 null입니다. API v2에서만 지원합니다.
 * @property git 봇의 깃허브 주소입니다. 없는 경우 null입니다. API v2에서만 지원합니다.
 * @property url 봇 초대 링크입니다. 없는 경우 null입니다.
 * @property discord 봇의 지원 디스코드 참가 링크입니다. 없는 경우 null입니다. API v2에서만 지원합니다.
 * @property category 봇의 카테고리 목록입니다.
 * @property vanity 봇의 VANITY URL입니다. 없는 경우 null입니다.
 * @property bg 봇의 배경 이미지 URL입니다. 없는 경우 null입니다.
 * @property banner 봇의 배너 이미지 URL입니다. 없는 경우 null입니다.
 * @property status 봇의 유저 상태입니다. 등록되지 않은 경우 null입니다. API v2에서만 지원합니다.
 * @property state 한국 디스코드봇 리스트에서의 봇의 상태입니다.
 * @property verified 봇이 디스코드에서 인증되었으면 true입니다. API v1에서만 지원합니다.
 * @property trusted 봇이 한국 디스코드 리스트에서 인증되었으면 true입니다. API v1에서만 지원합니다.
 * @property boosted 소유자가 한디리 디스코드 서버 부스터인 경우 true입니다. API v1에서만 지원합니다.
 */
interface Bot {
    // Common Element
    val id: String
    val name: String
    val tag: String
    val votes: Int
    val servers: Int
    val intro: String
    val url: String?
    val category: List<Category>
    val vanity: String?
    val bg: String?
    val banner: String?
    val state: State

    // API v1 Only
    val verified: Boolean get() = throw UnsupportedOperationException("Only Supported on API v1")
    val trusted: Boolean get() = throw UnsupportedOperationException("Only Supported on API v1")
    val boosted: Boolean get() = throw UnsupportedOperationException("Only Supported on API v1")

    // API v2 Only
    val avatar: String? get() = throw UnsupportedOperationException("Only Supported on API v2")
    val owners: List<BotOwner> get() = throw UnsupportedOperationException("Only Supported on API v2")
    val flags: EnumSet<Flag> get() = throw UnsupportedOperationException("Only Supported on API v2")
    val lib: String get() = throw UnsupportedOperationException("Only Supported on API v2")
    val prefix: String get() = throw UnsupportedOperationException("Only Supported on API v2")
    val desc: String get() = throw UnsupportedOperationException("Only Supported on API v2")
    val web: String? get() = throw UnsupportedOperationException("Only Supported on API v2")
    val git: String? get() = throw UnsupportedOperationException("Only Supported on API v2")
    val discord: String? get() = throw UnsupportedOperationException("Only Supported on API v2")
    val status: Status? get() = throw UnsupportedOperationException("Only Supported on API v2")

    /**
     * 봇의 카테고리를 나타냅니다. API v1에서는 일부가 지원되지 않습니다.
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
     * 봇의 유저 상태를 나타냅니다. API v2에서만 지원합니다.
     */
    enum class Status(private val apiName: String) {
        ONLINE("online"),
        IDLE("idle"),
        DO_NOT_DISTURB("dnd"),
        STREAMING("streaming"),
        OFFLINE("offline");

        override fun toString() = apiName
    }

    /**
     * 한국 디스코드봇 리스트에서의 봇의 상태를 나타냅니다.
     */
    enum class State(private val apiName: String) {
        /** 봇이 정상 상태임을 나타냅니다. */
        OK("ok"),
        /** 봇이 일시적으로 정지 상태임을 나타냅니다. API v2에서만 지원합니다. */
        REPORTED("reported"),
        /** 봇이 강제 삭제 처리되었음을 나타냅니다. API v2에서만 지원합니다. */
        BLOCKED("blocked"),
        /** 봇이 특수 목적 봇임을 나타냅니다. */
        PRIVATE("private"),
        /** 봇이 잠겼거나 지원이 종료된 상태임을 나타냅니다. */
        ARCHIVED("archived");

        override fun toString() = apiName
    }

    /**
     * 봇 플래그를 나타냅니다. API v2에서만 지원합니다.
     * @property value 플래그 값입니다.
     */
    enum class Flag(val value: Int) {
        /** 공식 봇임을 나타냅니다. */
        OFFICAL(1 shl 0),
        /** 한국 디스코드봇 리스트에서 인증된 봇임을 나타냅니다. */
        TRUSTED(1 shl 2),
        /** 파트너임을 나타냅니다. */
        PARTNER(1 shl 3),
        /** 디스코드에서 인증된 봇임을 나타냅니다. */
        VERIFIED(1 shl 4),
        /** 프리미엄임을 나타냅니다. */
        PREMIUM(1 shl 5),
        /** 제1회 한국 디스코드봇 리스트 해커톤 우승자 봇임을 나타냅니다. */
        FIRST_HACKATON_WINNER(1 shl 6);
    }

    /**
     * 봇 소유자를 나타냅니다.
     * [bots] 프로퍼티가 문자열 리스트라는 것을 제외하면 [User]와 같습니다.
     * API v2에서만 사용합니다.
     * @see User
     */
    data class BotOwner(
        val id: String,
        val username: String,
        val tag: String,
        val github: String?,
        val flags: EnumSet<User.Flag>,
        val bots: List<String>
    )
}
