package com.dariwan.kupin.view.refrigenerator.detail

import android.app.Application
import androidx.lifecycle.ViewModel
import com.dariwan.kupin.core.data.local.database.Material
import com.dariwan.kupin.core.data.local.repository.MaterialRepository

class DetailViewModel(application: Application): ViewModel() {
    private val mMaterialRepository: MaterialRepository = MaterialRepository(application)

    fun delete (id: Int){
        mMaterialRepository.delete(id)
    }
}