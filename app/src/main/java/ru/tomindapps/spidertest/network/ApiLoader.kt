package ru.tomindapps.spidertest.network

import com.google.gson.JsonObject
import com.google.gson.JsonParser
import io.github.rybalkinsd.kohttp.dsl.httpGet
import io.github.rybalkinsd.kohttp.ext.asString
import io.github.rybalkinsd.kohttp.ext.url
import okhttp3.Response
import ru.tomindapps.spidertest.Settings
import ru.tomindapps.spidertest.models.CommentModel
import ru.tomindapps.spidertest.models.ImageModel

object ApiLoader{

    private const val MAIN_URL = "https://api.imgur.com/3/gallery/"
    private const val GALLERY_POPULAR_SEARCH = "hot/top/"
    private const val COMMENTS_TOP_LOAD = "/comments/"
    private const val AUTHORIZATION = "Authorization"

    fun makeCommentsSearch(galleryId: String): String?{
        val response: Response = httpGet {
            url(MAIN_URL + galleryId + COMMENTS_TOP_LOAD)
            header {
                AUTHORIZATION to Settings.CLIENT_ID
            }
        }
        return response.asString()
    }

    fun parseCommentsJson(s: String): ArrayList<CommentModel>{
        val data = JsonParser()
            .parse(s)
            .asJsonObject
            .getAsJsonArray("data")
        val comments = arrayListOf<CommentModel>()
        for (i in 0 until data.size()){
            val item = data[i].asJsonObject
            val comment = bindComment(item)
            comments.add(comment)
        }
        return comments
    }

    private fun bindComment(item: JsonObject?): CommentModel {
        return CommentModel(
            item!!["comment"].asString,
            item["author"].asString
        )
    }

    fun makeGalerySearch(page: Int): String? {
        val response: Response = httpGet {
            url(MAIN_URL + GALLERY_POPULAR_SEARCH + page.toString())
            header {
                AUTHORIZATION to Settings.CLIENT_ID
                }
            }
        return response.asString()
    }

    fun parseGaleryJson(s: String):ArrayList<ImageModel>{
        val data = JsonParser()
            .parse(s)
            .asJsonObject
            .getAsJsonArray("data")
        val images = arrayListOf<ImageModel>()

        for (i in 0 until data.size()){
            val items = data[i]
                .asJsonObject
            if (items.has("images")){
                val galleryId = items["id"].asString
                val imagesO = items
                    .getAsJsonArray("images")[0]
                    .asJsonObject
                val imageType = imagesO["type"].asString
                if (imageType == "image/png" || imageType == "image/jpg") {
                    val image = bindImageToModel(imagesO, galleryId)
                    images.add(image)
                }
            }
        }
        return images
    }

    private fun bindImageToModel(image: JsonObject, galleryId: String): ImageModel {
        val title = if (image["title"].isJsonNull) null else image["title"].asString
        val description = if (image["description"].isJsonNull) null else image["description"].asString

        return ImageModel(
            galleryId,
            title,
            description,
            image["datetime"].asLong,
            image["link"].asString
        )
    }

}