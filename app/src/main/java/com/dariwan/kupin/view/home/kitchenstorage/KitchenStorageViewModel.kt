package com.dariwan.kupin.view.home.kitchenstorage

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.dariwan.kupin.core.data.local.database.kitchencabinet.KitchenCabinet
import com.dariwan.kupin.core.data.local.database.kulkasku.Material
import com.dariwan.kupin.core.data.local.repository.MaterialRepository

@RequiresApi(Build.VERSION_CODES.O)
class KitchenStorageViewModel(application: Application): ViewModel() {
    private val mMaterialRepository: MaterialRepository = MaterialRepository(application)

    fun getAllMaterialStorage(): LiveData<List<KitchenCabinet>> = mMaterialRepository.getAllKitchenStorage()

    fun incrementQuantityStorage(id: Int){
        mMaterialRepository.incrementQuantityStorage(id)
    }

    fun decrementQuantityStorage(id: Int){
        mMaterialRepository.decrementQuantityStorage(id)
    }

    fun getCategoryStorage(category: String): LiveData<List<KitchenCabinet>>{
        return mMaterialRepository.getCategoryStorage(category)
    }
}