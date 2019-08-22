package ru.tomindapps.spidertest.ui


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.squareup.picasso.Picasso

import ru.tomindapps.spidertest.R
import ru.tomindapps.spidertest.models.CommentModel
import ru.tomindapps.spidertest.presenters.ElementFragmentPresenter
import ru.tomindapps.spidertest.views.ElementFragmentView

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class ElementFragment : MvpAppCompatFragment(), ElementFragmentView {

    private lateinit var iv: ImageView
    private lateinit var rv: RecyclerView
    private lateinit var ca: CommentsAdapter
    private lateinit var tvImgTitle: TextView
    private lateinit var tvImgDescription: TextView
    private lateinit var tvImgCreated: TextView

    @InjectPresenter
    lateinit var mElementFragmentPresenter: ElementFragmentPresenter

    private var imageLink: String? = null
    private var galleryId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            imageLink = it.getString(ARG_PARAM1)
            galleryId = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_element, container, false)
        val lm = LinearLayoutManager(activity)
        ca = CommentsAdapter()
        tvImgTitle = v.findViewById(R.id.tv_img_title)
        tvImgDescription = v.findViewById(R.id.tv_img_description)
        tvImgCreated = v.findViewById(R.id.tv_img_created)
        iv = v.findViewById(R.id.iv_element)
        rv = v.findViewById(R.id.rv_comments)
        with(rv){
            layoutManager = lm
            itemAnimator = DefaultItemAnimator()
            adapter = ca
        }
        mElementFragmentPresenter.loadImage(imageLink!!)
        mElementFragmentPresenter.loadData(galleryId!!)

        return v
    }

    override fun loadImage(imageLink: String, imageTitle: String?, imageDescription: String?, imageCreated: String) {

        Picasso.get().load(imageLink)
            .placeholder(R.drawable.ic_place_holder_black_24dp)
            .into(iv)
    }

    override fun loadComments(comments: List<CommentModel>) {
        ca.setupComments(comments)
    }


    companion object {
        @JvmStatic
        fun newInstance(imageLink: String, galleryId: String) =
            ElementFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, imageLink)
                    putString(ARG_PARAM2, galleryId)
                }
            }
    }
}
