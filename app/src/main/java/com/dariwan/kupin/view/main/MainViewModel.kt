package com.dariwan.kupin.view.main

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.dariwan.kupin.core.data.local.database.Material
import com.dariwan.kupin.core.data.local.repository.MaterialRepository

@RequiresApi(Build.VERSION_CODES.O)
class MainViewModel(application: Application): ViewModel() {
    private val mMaterialRepository: MaterialRepository = MaterialRepository(application)

    fun getAllMaterials(): LiveData<List<Material>> = mMaterialRepository.getAllMaterials()

    fun updateNotificationSent(id: Int, notificationSent: Int){
        mMaterialRepository.updateMaterialNotification(id, notificationSent)
    }
}