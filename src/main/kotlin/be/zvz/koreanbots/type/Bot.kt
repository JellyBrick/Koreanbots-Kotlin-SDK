package be.zvz.koreanbots.type

data class Bot(
        val id: String,
        val name: String,
        val servers: Int,
        val votes: Int,
        val intro: String,
        val avatar: String,
        val url: String?,
        val category: List<String>, // TODO: Category enum 사용하도록 변경
        val tag: String,
        val state: State,
        val verified: Boolean,
        val boosted: Boolean,
        val vanity: String?,
        val banner: String?,
        val bg: String
)