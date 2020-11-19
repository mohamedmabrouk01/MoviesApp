package com.example.moviesapp.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.moviesapp.R
import com.example.moviesapp.data.models.People
import com.example.moviesapp.databinding.PepoleItemViewBinding

class PeopleListAdapter(val listener: PeopleListener)  : PagingDataAdapter<People,PeopleListAdapter.Holder>(callBack){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val viewBinding = DataBindingUtil.inflate<PepoleItemViewBinding>(LayoutInflater.from(parent.context),
            R.layout.pepole_item_view,parent,false)

        return Holder(viewBinding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    inner class Holder(var itemViewBinding: PepoleItemViewBinding) :  RecyclerView.ViewHolder(itemViewBinding.root){

        fun bind(item:People){
            itemViewBinding.people=item
            itemViewBinding.executePendingBindings()
            itemViewBinding.root.setOnClickListener { listener.onPeopleClick(item) }
        }

    }

    companion object {
        val callBack = object : DiffUtil.ItemCallback<People>(){
            override fun areItemsTheSame(oldItem: People, newItem: People): Boolean = oldItem.id==newItem.id

            override fun areContentsTheSame(oldItem: People, newItem: People): Boolean = oldItem==newItem

        }
    }

    interface PeopleListener{
        fun onPeopleClick(people:People)
    }
}