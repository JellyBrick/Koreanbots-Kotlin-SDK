package be.zvz.koreanbots.v2.dto

import be.zvz.koreanbots.dto.Voted

data class VotedImpl(
    override val voted: Boolean,
    override val lastVote: Long
) : Voted
