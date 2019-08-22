package ru.tomindapps.spidertest.models

import io.reactivex.Single
import ru.tomindapps.spidertest.network.ApiLoader

object ImageRepo {

    fun getImages(page: Int): Single<List<ImageModel>>{

        return Single.fromCallable { loadImages(page) }


    }

    private fun loadImages(page: Int):List<ImageModel>{
        return ApiLoader.parseGaleryJson(ApiLoader.makeGalerySearch(page)!!)
    }
}