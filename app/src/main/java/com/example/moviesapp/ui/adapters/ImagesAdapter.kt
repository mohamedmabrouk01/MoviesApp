package com.example.moviesapp.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.moviesapp.R
import com.example.moviesapp.data.models.PersonImages
import com.example.moviesapp.databinding.ImageItemViewBinding

class ImagesAdapter(val listener: ImagesListener) : RecyclerView.Adapter<ImagesAdapter.Holder>() {
    var data:ArrayList<PersonImages> = ArrayList()
    set(value) {
        field=value
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val viewBinding = DataBindingUtil.inflate<ImageItemViewBinding>(LayoutInflater.from(parent.context), R.layout.image_item_view,parent,false)
        return Holder(viewBinding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int = data.size

    inner class Holder(private val viewBinding: ImageItemViewBinding) : RecyclerView.ViewHolder(viewBinding.root) {

        fun bind(item:PersonImages){
            viewBinding.image=item
            viewBinding.executePendingBindings()
            viewBinding.root.setOnClickListener { listener.OnImageClick(item) }
        }
    }

    interface ImagesListener{
        fun OnImageClick(item:PersonImages)
    }
}