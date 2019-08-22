package ru.tomindapps.spidertest.models

data class ImageModel(
    val galleryId: String,
    val title: String?,
    val description: String?,
    val datetime: Long,
    val link: String
)