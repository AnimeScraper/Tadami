package com.sf.tadami.animesources.extractors

import android.util.Base64
import com.sf.tadami.animesources.extractors.utils.PlaylistUtils
import com.sf.tadami.network.api.model.StreamSource
import com.sf.tadami.network.requests.okhttp.GET
import com.sf.tadami.network.requests.utils.asJsoup
import okhttp3.Headers
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.OkHttpClient

class FusevideoExtractor(private val client: OkHttpClient, private val headers: Headers) {

    fun videosFromUrl(url: String, prefix: String = ""): List<StreamSource> {
        return runCatching {
            val newHeaders = headers.newBuilder()
                .set("Accept", "*/*")
                .set("Host", url.toHttpUrl().host)
                .set("Accept-Language", "en-US,en;q=0.5")
                .build()
            val document = client.newCall(GET(url, newHeaders)).execute().use { it.asJsoup() }
            val dataUrl = document.selectFirst("script[src~=f/u/u/u/u]")?.attr("src")!!
            val dataDoc = client.newCall(GET(dataUrl, newHeaders)).execute().use { it.body.string() }
            val encoded = Regex("atob\\(\"(.*?)\"\\)").find(dataDoc)?.groupValues?.get(1)!!
            val data = Base64.decode(encoded, Base64.DEFAULT).toString(Charsets.UTF_8)
            val jsonData = data.split("|||")[1].replace("\\", "")
            val videoUrl = Regex("\"(https://.*?/m/.*)\"").find(jsonData)?.groupValues?.get(1)!!
            PlaylistUtils(client, newHeaders).extractFromHls(videoUrl, videoNameGen = { "${prefix}Fusevideo - $it" })
        }.getOrDefault(emptyList())
    }

}