package com.example.moviesapp.ui.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moviesapp.R
import com.example.moviesapp.callBack.PersonDetailsCallBack
import com.example.moviesapp.data.models.People
import com.example.moviesapp.data.models.PersonImages
import com.example.moviesapp.databinding.PeopleDetailsLayoutBinding
import com.example.moviesapp.ui.PreviewImageFragment
import com.example.moviesapp.ui.adapters.ImagesAdapter
import com.example.moviesapp.utils.PERSON_ID
import com.example.moviesapp.viewModels.PersonViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PersonDetailsActivity : AppCompatActivity() , PersonDetailsCallBack, ImagesAdapter.ImagesListener {
    lateinit var layoutBinding: PeopleDetailsLayoutBinding
    val viewModel : PersonViewModel<PersonDetailsCallBack> by  viewModels()
    val imagesAdapter: ImagesAdapter by lazy {
        ImagesAdapter(this)
    }

    companion object{
        fun start(id:Long, context: Context){
            val intent=Intent(context,PersonDetailsActivity::class.java)
            intent.putExtra(PERSON_ID,id)
            context.startActivity(intent)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        layoutBinding=DataBindingUtil.setContentView(this, R.layout.people_details_layout)
        layoutBinding.imagesRcv.layoutManager=GridLayoutManager(this,2,LinearLayoutManager.VERTICAL,false)
        layoutBinding.imagesRcv.adapter=imagesAdapter
        viewModel.attachView(this)
        viewModel.loadPersonDetails(intent.getLongExtra(PERSON_ID,0))
        viewModel.loadImages()
        layoutBinding.vm=viewModel
        layoutBinding.backImg.setOnClickListener { finish() }
    }

    override fun loadPerson(data: People) {
       layoutBinding.person=data
    }

    override fun loadImages(images: ArrayList<PersonImages>) {
       imagesAdapter.data=images
    }

    override fun OnImageClick(item: PersonImages) {
       PreviewImageFragment.start(item).show(supportFragmentManager,"preview")
    }
}