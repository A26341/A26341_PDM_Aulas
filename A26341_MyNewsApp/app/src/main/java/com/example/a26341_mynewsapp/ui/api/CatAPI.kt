package com.example.a26341_mynewsapp.ui.api

import com.example.a26341_mynewsapp.models.Cat
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONArray
import java.io.IOException

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CatAPI {

    suspend fun fetchCatImages(): List<Cat> = withContext(Dispatchers.IO) {
        val client = OkHttpClient()
        val request = Request.Builder()
            .url("https://api.thecatapi.com/v1/images/search?has_breeds=1&limit=500&api_key=live_lMN5Wyf05fGwUphyAnAF1y2kdGGqkOQQre8QIZ4bfnHlOLsevdiB7fB9f87rN6lj")
            .build()

        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) throw IOException("Erro na requisição: $response")

            println("Resposta: $response")

            val jsonArray = JSONArray(response.body?.string())
            println("Resposta JSON: $jsonArray")
            val cats = mutableListOf<Cat>()

            for (i in 0 until jsonArray.length()) {
                val catObject = jsonArray.getJSONObject(i)
                val cat = Cat.fromJSON(catObject)
                cats.add(cat)
            }
            cats
        }
    }
}
