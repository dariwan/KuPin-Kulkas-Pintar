package com.dariwan.kupin.view.home

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.dariwan.kupin.core.data.local.database.kulkasku.Material
import com.dariwan.kupin.core.data.local.repository.MaterialRepository
@RequiresApi(Build.VERSION_CODES.O)
class RefrigeneratorViewModel(application: Application): ViewModel() {
    private val mMaterialRepository: MaterialRepository = MaterialRepository(application)
    fun getAllMaterial(): LiveData<List<Material>> = mMaterialRepository.getAllMaterial()

    fun incrementQuantity(id: Int){
        mMaterialRepository.incrementQuantity(id)
    }

    fun decrementQuantity(id: Int){
        mMaterialRepository.decrementQuantity(id)
    }

    fun getCategory(category: String): LiveData<List<Material>>{
        return mMaterialRepository.getCategory(category)
    }

}