package ru.tomindapps.spidertest.models

import io.reactivex.Single
import ru.tomindapps.spidertest.network.ApiLoader

object CommentRepo {
    fun getComments(galleryId: String):Single<List<CommentModel>>{
        return Single.fromCallable{ loadComments(galleryId)}
    }

    private fun loadComments(galleryId: String): List<CommentModel> {
        return ApiLoader.parseCommentsJson(ApiLoader.makeCommentsSearch(galleryId)!!)
    }
}