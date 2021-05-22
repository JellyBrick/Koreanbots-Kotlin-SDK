package be.zvz.koreanbots.v2.dto

data class VotedImpl(
    val voted: Boolean,
    val lastVote: Long
) : be.zvz.koreanbots.dto.Voted()
