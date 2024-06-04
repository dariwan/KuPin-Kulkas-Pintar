package com.dariwan.kupin.view.main

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.dariwan.kupin.core.data.local.database.kitchencabinet.KitchenCabinet
import com.dariwan.kupin.core.data.local.database.kulkasku.Material
import com.dariwan.kupin.core.data.local.repository.MaterialRepository

@RequiresApi(Build.VERSION_CODES.O)
class MainViewModel(application: Application): ViewModel() {
    private val mMaterialRepository: MaterialRepository = MaterialRepository(application)

    fun getAllMaterials(): LiveData<List<Material>> = mMaterialRepository.getAllMaterials()

    fun updateNotificationSent(id: Int, notificationSent: Int){
        mMaterialRepository.updateMaterialNotification(id, notificationSent)
    }

    fun getAllStorage(): LiveData<List<KitchenCabinet>> = mMaterialRepository.getAllKitchenStorage()
    fun updateNotificationStorage(id: Int, notificationSent: Int){
        mMaterialRepository.updateMaterialNotificationStorage(id, notificationSent)
    }
}