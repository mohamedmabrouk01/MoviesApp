package com.example.moviesapp.ui

import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.example.moviesapp.R
import com.example.moviesapp.callBack.PersonDetailsCallBack
import com.example.moviesapp.data.models.PersonImages
import com.example.moviesapp.databinding.ImageViewBinding
import com.example.moviesapp.utils.PERSON_IMAGE
import com.example.moviesapp.utils.REQUESTCODE
import com.example.moviesapp.viewModels.PersonViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PreviewImageFragment : BottomSheetDialogFragment() , PersonDetailsCallBack{
    lateinit var layoutBinding:ImageViewBinding
    val viewModel:PersonViewModel<PersonDetailsCallBack> by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        layoutBinding=DataBindingUtil.inflate(inflater, R.layout.image_view,container,false)
        return layoutBinding.root
    }

      override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
          val parcelable = arguments?.getParcelable<PersonImages>(PERSON_IMAGE)!!
          viewModel.attachView(this)
          viewModel.image=parcelable
          layoutBinding.image=parcelable
          layoutBinding.previewImg.ratio=parcelable.aspect_ratio
          layoutBinding.downloadImg.setOnClickListener {
              if (checkPermission()){
                  viewModel.downloadImage()
              }else{
                  requestPermission()
              }
              }
      }

    override fun onStart() {
        super.onStart()
        val sheetContainer = requireView().parent as? ViewGroup ?: return
        sheetContainer.layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT
    }

    companion object{
        fun start(image:PersonImages) : PreviewImageFragment{
            val bundle = Bundle()
            bundle.putParcelable(PERSON_IMAGE,image)
            val fragment=PreviewImageFragment()
            fragment.arguments=bundle
            return fragment
        }
    }


    private fun requestPermission(){
        activity?.let { ActivityCompat.requestPermissions(it, arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE,android.Manifest.permission.READ_EXTERNAL_STORAGE),REQUESTCODE) }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when(requestCode){
            REQUESTCODE -> {
                if (grantResults.isNotEmpty() && grantResults[0]== PackageManager.PERMISSION_GRANTED){
                    viewModel.downloadImage()
                } else{
                    Snackbar.make(layoutBinding.root,"Permission Denied, Please allow to proceed !", Snackbar.LENGTH_LONG).show();
                }
            }
        }
    }


    fun checkPermission():Boolean = activity?.applicationContext?.let {
        ContextCompat.checkSelfPermission(
            it,android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
    } == PackageManager.PERMISSION_GRANTED



}