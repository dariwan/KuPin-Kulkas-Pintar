package com.dariwan.kupin.view.refrigenerator.addmaterial

import android.app.Application
import androidx.lifecycle.ViewModel
import com.dariwan.kupin.core.data.local.database.Material
import com.dariwan.kupin.core.data.local.repository.MaterialRepository

class AddMaterialViewModel(application: Application): ViewModel() {
    private val mMaterialRepository: MaterialRepository = MaterialRepository(application)

    fun insert (material: Material){
        mMaterialRepository.insert(material)
    }
}