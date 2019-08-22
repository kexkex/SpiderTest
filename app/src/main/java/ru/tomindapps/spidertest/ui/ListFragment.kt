package ru.tomindapps.spidertest.ui


import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter

import ru.tomindapps.spidertest.R
import ru.tomindapps.spidertest.models.ImageModel
import ru.tomindapps.spidertest.presenters.ListFragmentPresenter
import ru.tomindapps.spidertest.views.ListFragmentView
import java.util.ArrayList


class ListFragment : MvpAppCompatFragment(), ListFragmentView, ImagesAdapter.MyAdapterListener {

    private lateinit var rv: RecyclerView
    private lateinit var ia: ImagesAdapter

    var pageCount: Int = 1

    @InjectPresenter
    lateinit var mListFragmentPresenter: ListFragmentPresenter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val v = inflater.inflate(R.layout.fragment_list, container, false)

        val lm = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)

        ia = ImagesAdapter(this)
        rv = v.findViewById(R.id.rv)
        with(rv){
            layoutManager = lm
            itemAnimator = DefaultItemAnimator()
            adapter = ia
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)

                    val lastVisiblePosition = lm.findLastVisibleItemPositions(null)
                    if (lastVisiblePosition[0] >= ia.itemCount-3){
                        pageCount++
                        mListFragmentPresenter.loadNextImages(pageCount)
                    }
                }
            })
        }

        return v
    }

    override fun loadImages(images: List<ImageModel>) {
        ia.setupImages(images as ArrayList<ImageModel>)
    }


    override fun openElementFragment(image: ImageModel) {
        val fragment = ElementFragment.newInstance(image.link,image.galleryId)
        activity!!.supportFragmentManager.beginTransaction()
            .replace(R.id.container, fragment)
            .addToBackStack(null)
            .commit()
    }

    override fun onRowClicked(image: ImageModel) {
        mListFragmentPresenter.elementClick(image)
    }

}
