package com.dariwan.kupin.view.home.addkitchenstorage

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import com.dariwan.kupin.core.data.local.database.kitchencabinet.KitchenCabinet
import com.dariwan.kupin.core.data.local.repository.MaterialRepository

@RequiresApi(Build.VERSION_CODES.O)

class AddKitchenStorageViewModel(application: Application): ViewModel() {
    private val mMaterialRepository: MaterialRepository = MaterialRepository(application)

    fun insertKitchenStorage(kitchenCabinet: KitchenCabinet){
        mMaterialRepository.insertStorageKitchen(kitchenCabinet)
    }

}