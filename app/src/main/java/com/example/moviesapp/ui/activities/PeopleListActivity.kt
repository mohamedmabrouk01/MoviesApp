package com.example.moviesapp.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moviesapp.R
import com.example.moviesapp.callBack.PeopleCallBack
import com.example.moviesapp.data.models.People
import com.example.moviesapp.databinding.ActivityPeopleListBinding
import com.example.moviesapp.ui.adapters.PeopleListAdapter
import com.example.moviesapp.viewModels.PeopleViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PeopleListActivity : AppCompatActivity() , PeopleCallBack, PeopleListAdapter.PeopleListener {
    lateinit var layoutBinding: ActivityPeopleListBinding
    private  val viewModel:PeopleViewModel<PeopleCallBack> by viewModels()
     private val adapter: PeopleListAdapter by lazy {
         PeopleListAdapter(this)
     }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        layoutBinding=DataBindingUtil.setContentView(this,R.layout.activity_people_list)
        layoutBinding.peopleRcv.layoutManager = GridLayoutManager(this,2,LinearLayoutManager.VERTICAL,false)
        layoutBinding.peopleRcv.adapter=adapter
        viewModel.attachView(this)
        layoutBinding.peopleVm=viewModel
       viewModel.loadData()
    }

    override fun loadPeople(data: PagingData<People>) {
        lifecycleScope.launch{
            adapter.submitData(data)
        }
    }

    override fun onPeopleClick(people: People) {
        PersonDetailsActivity.start(people.id,this)
    }



}