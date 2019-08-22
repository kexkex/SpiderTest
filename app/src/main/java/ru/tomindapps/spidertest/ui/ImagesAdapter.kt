package ru.tomindapps.spidertest.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import ru.tomindapps.spidertest.R
import ru.tomindapps.spidertest.models.ImageModel
import java.util.ArrayList

class ImagesAdapter(listener: MyAdapterListener) : RecyclerView.Adapter<ImagesAdapter.MyViewHolder>() {

    private var listener = listener
    private var images = ArrayList<ImageModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.rv_item, parent, false)
        return MyViewHolder(v)
    }

    override fun getItemCount(): Int {
        return images.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(images[position])
        holder.itemView.setOnClickListener { listener.onRowClicked(images[position]) }
    }

    fun setupImages(images: ArrayList<ImageModel>){
        this.images.addAll(images)
        notifyDataSetChanged()
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        fun bind(image: ImageModel) {
            val iv = itemView.findViewById<ImageView>(R.id.iv_image)
            Picasso.get().load(image.link)
                .placeholder(R.drawable.ic_place_holder_black_24dp)
                .into(iv)
        }
    }

    interface MyAdapterListener {
        fun onRowClicked(image: ImageModel)
    }
}