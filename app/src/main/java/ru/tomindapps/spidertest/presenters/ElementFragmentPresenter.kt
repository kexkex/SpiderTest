package ru.tomindapps.spidertest.presenters

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import ru.tomindapps.spidertest.models.CommentModel
import ru.tomindapps.spidertest.models.CommentRepo
import ru.tomindapps.spidertest.views.ElementFragmentView

@InjectViewState
class ElementFragmentPresenter : MvpPresenter<ElementFragmentView>(){
    private var compositeSubscription = CompositeDisposable()

    override fun onDestroy() {
        super.onDestroy()
        compositeSubscription.clear()
    }

    fun loadData(galleryId: String){
        val single = CommentRepo.getComments(galleryId)
        val subscription = single
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { comments -> onLoadingSuccess(comments) }
        compositeSubscription.add(subscription)
    }

    private fun onLoadingSuccess(comments: List<CommentModel>?) {
        viewState.loadComments(comments!!)
    }

    fun loadImage(imageLink: String, imageTitle: String, imageDescription: String, imageCreated: Long){
        viewState.loadImage(imageLink, imageTitle, imageDescription, imageCreated)
    }
}