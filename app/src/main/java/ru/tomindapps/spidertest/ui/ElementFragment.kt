package ru.tomindapps.spidertest.ui


import android.os.Bundle
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
import java.util.*

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
private const val ARG_PARAM3 = "param3"
private const val ARG_PARAM4 = "param4"
private const val ARG_PARAM5 = "param5"

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
    private var imageTitle: String? = null
    private var imageDescription: String? = null
    private var imageCreated: Long? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            imageLink = it.getString(ARG_PARAM1,"")
            galleryId = it.getString(ARG_PARAM2,"")
            imageTitle = it.getString(ARG_PARAM3,"No Title")
            imageDescription = it.getString(ARG_PARAM4, "No Description")
            imageCreated = it.getLong(ARG_PARAM5,0)
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
        mElementFragmentPresenter.loadImage(imageLink!!, imageTitle!!, imageDescription!!, imageCreated!!)
        mElementFragmentPresenter.loadData(galleryId!!)

        return v
    }

    override fun loadImage(imageLink: String, imageTitle: String, imageDescription: String, imageCreated: Long) {
        tvImgTitle.text = imageTitle
        tvImgDescription.text = imageDescription
        tvImgCreated.text = Date(imageCreated).toString()
        Picasso.get().load(imageLink)
            .placeholder(R.drawable.ic_place_holder_black_24dp)
            .into(iv)
    }

    override fun loadComments(comments: List<CommentModel>) {
        ca.setupComments(comments)
    }


    companion object {
        @JvmStatic
        fun newInstance(imageLink: String, galleryId: String, imageTitle: String?, imageDescription: String?, imageCreated: Long) =
            ElementFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, imageLink)
                    putString(ARG_PARAM2, galleryId)
                    putString(ARG_PARAM3, imageTitle)
                    putString(ARG_PARAM4, imageDescription)
                    putLong(ARG_PARAM5, imageCreated)
                }
            }
    }
}
