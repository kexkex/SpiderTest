package ru.tomindapps.spidertest.views

import com.arellomobile.mvp.MvpView
import ru.tomindapps.spidertest.models.ImageModel

interface ListFragmentView : MvpView{
    fun loadImages(images: List<ImageModel>)
    fun openElementFragment(image: ImageModel)
}