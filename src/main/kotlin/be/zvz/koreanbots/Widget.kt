package be.zvz.koreanbots

fun KoreanBots.getWidgetUrl(
    id: Int,
    type: WidgetType,
    style: WidgetStyle = WidgetStyle.FLAT,
    scale: Double = 1.0,
    icon: Boolean = true
) = "${KoreanBotsInfo.API_BASE_URL}/widget/bots/$type/$id.svg?style=${style.apiName}&scale=$scale&icon=$icon"

enum class WidgetType(val apiName: String) {
    VOTES("votes"),
    SERVERS("servers"),
    STATUS("status");

    override fun toString() = apiName
}

enum class WidgetStyle(val apiName: String) {
    CLASSIC("classic"),
    FLAT("flat");

    override fun toString() = apiName
}
