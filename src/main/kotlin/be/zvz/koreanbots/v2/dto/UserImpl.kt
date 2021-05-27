package be.zvz.koreanbots.v2.dto

import be.zvz.koreanbots.dto.User
import java.util.EnumSet

data class UserImpl(
    override val id: String,
    override val username: String,
    override val tag: String,
    override val github: String?,
    override val flags: EnumSet<User.Flag>,
    override val bots: List<User.OwnedBot>
) : User
