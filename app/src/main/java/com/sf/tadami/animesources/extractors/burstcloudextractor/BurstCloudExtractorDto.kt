package com.sf.tadami.animesources.extractors.burstcloudextractor

import kotlinx.serialization.Serializable

@Serializable
data class BurstCloudDto(
    val purchase: Purchase,
)

@Serializable
data class Purchase(
    val cdnUrl: String,
)