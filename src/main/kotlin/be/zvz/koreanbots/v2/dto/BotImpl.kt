package be.zvz.koreanbots.v2.dto

import be.zvz.koreanbots.dto.Bot
import java.util.EnumSet

internal data class BotImpl(
    override val id: String,
    override val name: String,
    override val owners: List<Bot.BotOwner>,
    override val lib: String,
    override val prefix: String,
    override val votes: Int,
    override val servers: Int,
    override val intro: String,
    override val desc: String,
    override val web: String?,
    override val git: String?,
    override val url: String?,
    override val category: List<Bot.Category>,
    override val status: Bot.Status,
    override val tag: String,
    override val avatar: String?,
    override val state: Bot.State,
    override val vanity: String?,
    override val bg: String?,
    override val banner: String?,
    override val discord: String?,

    override val flags: EnumSet<Bot.Flag>
) : Bot
