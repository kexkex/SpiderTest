package ru.tomindapps.spidertest.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.tomindapps.spidertest.R
import ru.tomindapps.spidertest.models.CommentModel
import java.util.ArrayList

class CommentsAdapter : RecyclerView.Adapter<CommentsAdapter.MyViewHolder>(){

    private var comments = ArrayList<CommentModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.rv_comment_item, parent, false)
        return MyViewHolder(v)
    }

    override fun getItemCount(): Int {
        return comments.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(comments[position])
    }

    fun setupComments(comments: List<CommentModel>){
        this.comments = comments as ArrayList<CommentModel>
        notifyDataSetChanged()
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        fun bind(comment: CommentModel) {
            val tvAuthor = itemView.findViewById<TextView>(R.id.tv_author)
            val tvComment = itemView.findViewById<TextView>(R.id.tv_comment)
            tvAuthor.text = comment.author
            tvComment.text = comment.comment
        }
    }
}