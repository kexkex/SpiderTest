package ru.tomindapps.spidertest.presenters

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import ru.tomindapps.spidertest.R
import ru.tomindapps.spidertest.models.ImageModel
import ru.tomindapps.spidertest.models.ImageRepo
import ru.tomindapps.spidertest.views.ListFragmentView

@InjectViewState
class ListFragmentPresenter : MvpPresenter<ListFragmentView>(){

    private var compositeSubscription = CompositeDisposable()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        loadData(1)
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeSubscription.clear()
    }

    fun loadNextImages(page:Int){
        loadData(page)
    }

    private fun loadData(page: Int){
        val single = ImageRepo.getImages(page)
        val subscription = single
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { images -> onLoadingSuccess(images) }
        compositeSubscription.add(subscription)
    }

    private fun onLoadingSuccess(images: List<ImageModel>?) {
        viewState.loadImages(images!!)
    }

    fun elementClick(image: ImageModel){
        viewState.openElementFragment(image)
    }
}