package com.dariwan.kupin.view.home.detail

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import com.dariwan.kupin.core.data.local.repository.MaterialRepository

@RequiresApi(Build.VERSION_CODES.O)
class DetailViewModel(application: Application): ViewModel() {
    private val mMaterialRepository: MaterialRepository = MaterialRepository(application)

    fun delete (id: Int){
        mMaterialRepository.delete(id)
    }

    fun deleteStorage(id: Int){
        mMaterialRepository.deleteStorage(id)
    }
}