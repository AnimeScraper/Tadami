package com.sf.tadami.domain.episode

data class UpdateEpisode(
    val id: Long,
    val animeId: Long? = null,
    val url: String? = null,
    val name: String? = null,
    val episodeNumber: Float? =  null,
    val timeSeen: Long? = null,
    val totalTime: Long? = null,
    val dateFetch: Long? = null,
    val dateUpload: Long? = null,
    val seen: Boolean? = null,
    val sourceOrder : Long? = null,
    val languages : String? = null
)

fun Episode.toUpdateEpisode() : UpdateEpisode{
    return UpdateEpisode(
        id = id,
        animeId = animeId,
        url = url,
        name = name,
        episodeNumber = episodeNumber,
        timeSeen = timeSeen,
        totalTime = totalTime,
        dateFetch = dateFetch,
        dateUpload = dateUpload,
        seen = seen,
        sourceOrder = sourceOrder,
        languages = languages
    )
}