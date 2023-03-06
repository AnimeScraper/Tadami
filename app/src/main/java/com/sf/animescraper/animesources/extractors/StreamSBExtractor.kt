package com.sf.animescraper.animesources.extractors

import com.sf.animescraper.network.requests.okhttp.GET
import com.sf.animescraper.network.scraping.dto.crypto.StreamSource
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonObject
import okhttp3.Headers
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.OkHttpClient

class StreamSBExtractor(private val client: OkHttpClient) {

    protected fun bytesToHex(bytes: ByteArray): String {
        val hexArray = "0123456789ABCDEF".toCharArray()
        val hexChars = CharArray(bytes.size * 2)
        for (j in bytes.indices) {
            val v = bytes[j].toInt() and 0xFF

            hexChars[j * 2] = hexArray[v ushr 4]
            hexChars[j * 2 + 1] = hexArray[v and 0x0F]
        }
        return String(hexChars)
    }

    // animension, asianload and dramacool uses "common = false"
    private fun fixUrl(url: String, common: Boolean): String {
        val sbUrl = "https://${url.toHttpUrl().host}"
        val id = url.substringAfter(url.toHttpUrl().host)
            .substringAfter("/e/")
            .substringAfter("/embed-")
            .substringBefore("?")
            .substringBefore(".html")
            .substringAfter("/")
        return if (common) {
            val hexBytes = bytesToHex(id.toByteArray())
            "$sbUrl/sources51/625a364258615242766475327c7c${hexBytes}7c7c4761574550654f7461566d347c7c73747265616d7362"
        } else {
            "$sbUrl/sources51/${bytesToHex("||$id||||streamsb".toByteArray())}/"
        }
    }

    fun videosFromUrl(url: String, headers: Headers, prefix: String = "", suffix: String = "", common: Boolean = true): List<StreamSource> {
        val newHeaders = headers.newBuilder()
            .set("referer", url)
            .set("watchsb", "sbstream")
            .set("authority", "embedsb.com")
            .build()
        return try {
            val master = fixUrl(url, common)
            val json = Json.decodeFromString<JsonObject>(
                client.newCall(GET(master, newHeaders))
                    .execute().body!!.string()
            )
            val masterUrl = json["stream_data"]!!.jsonObject["file"].toString().trim('"')
            val masterPlaylist = client.newCall(GET(masterUrl, newHeaders))
                .execute()
                .body!!.string()
            val separator = "#EXT-X-STREAM-INF"
            masterPlaylist.substringAfter(separator).split(separator).map {
                val resolution = it.substringAfter("RESOLUTION=")
                    .substringBefore("\n")
                    .substringAfter("x")
                    .substringBefore(",") + "p"
                val quality = ("StreamSB:" + resolution).let {
                    if(prefix.isNotBlank()) "$prefix $it"
                    else it
                }.let {
                    if(suffix.isNotBlank()) "$it $suffix"
                    else it
                }
                val videoUrl = it.substringAfter("\n").substringBefore("\n")
                StreamSource(videoUrl, quality, headers = newHeaders)
            }.reversed()
        } catch (e: Exception) {
            emptyList()
        }
    }

    fun videosFromDecryptedUrl(realUrl: String, headers: Headers, prefix: String = "", suffix: String = ""): List<StreamSource> {
        return try {
            val json = Json.decodeFromString<JsonObject>(client.newCall(GET(realUrl, headers)).execute().body!!.string())
            val masterUrl = json["stream_data"]!!.jsonObject["file"].toString().trim('"')
            val masterPlaylist = client.newCall(GET(masterUrl, headers)).execute().body!!.string()
            val separator = "#EXT-X-STREAM-INF"
            masterPlaylist.substringAfter(separator).split(separator).map {
                val resolution = it.substringAfter("RESOLUTION=")
                    .substringBefore("\n")
                    .substringAfter("x")
                    .substringBefore(",") + "p"
                val quality = ("StreamSB:$resolution").let {
                    if(prefix.isNotBlank()) "$prefix $it"
                    else it
                }.let {
                    if(suffix.isNotBlank()) "$it $suffix"
                    else it
                }
                val videoUrl = it.substringAfter("\n").substringBefore("\n")
                StreamSource(videoUrl, quality, headers = headers)
            }
        } catch (e: Exception) {
            emptyList()
        }
    }
}