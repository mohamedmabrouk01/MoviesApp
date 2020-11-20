package com.example.moviesapp.utils

import android.graphics.drawable.Drawable
import android.util.Log
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.request.RequestOptions
import com.squareup.picasso.Picasso
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class DataBindAdapterUtils {
    companion object {
        @JvmStatic
        @BindingAdapter("app:loadImage", "app:placeHolder",requireAll = false)
        fun loadImages(view: ImageView, url: String?,placeholder: Drawable) {
            url?.apply {
//                Glide.with(view)
//                    .load(url)
//                       // .apply(RequestOptions.placeholderOf(placeholder))
//                    .into(view)
                Picasso.get()
                        .load(this)
                        .into(view)



            }
        }


        @JvmStatic
        @BindingAdapter("app:loadImageResource")
        fun loadImage(view: ImageView, resource: Int) {
            view.setImageResource(resource)
        }
    }
}
