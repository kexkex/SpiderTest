package ru.tomindapps.spidertest.views

import com.arellomobile.mvp.MvpView
import ru.tomindapps.spidertest.models.CommentModel

interface ElementFragmentView : MvpView{
    fun loadImage(imageLink: String, imageTitle: String?, imageDescription: String?, imageCreated: String)
    fun loadComments(comments: List<CommentModel>)
}