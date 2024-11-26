package com.example.a26341_mynewsapp.ui.theme.models

import org.json.JSONObject

data class Cat(
    val id: String,
    val name: String = "Nome não disponível",
    val imageUrl: String = "URL da imagem não disponível",
    val url: String = "URL não disponível",
    val origin: String = "Origem desconhecida",
    val temperament: String = "Temperamento não disponível",
    val wikipediaUrl: String? = null
) {
    companion object {
        fun fromJSON(json: JSONObject): Cat {
            val breedsArray = json.optJSONArray("breeds")
            val breedObject = breedsArray?.optJSONObject(0)

            return Cat(
                id = json.getString("id"),
                name = breedObject?.optString("name", "Nome não disponível") ?: "Nome não disponível",
                imageUrl = json.optString("url", "URL da imagem não disponível"),
                url = breedObject?.optString("wikipedia_url", "URL não disponível") ?: "URL não disponível",
                origin = breedObject?.optString("origin", "Origem desconhecida") ?: "Origem desconhecida",
                temperament = breedObject?.optString("temperament", "Temperamento não disponível") ?: "Temperamento não disponível",
                wikipediaUrl = breedObject?.optString("wikipedia_url")
            )
        }
    }
}



