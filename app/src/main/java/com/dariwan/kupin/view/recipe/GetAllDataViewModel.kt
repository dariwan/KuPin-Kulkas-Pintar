package com.dariwan.kupin.view.recipe

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dariwan.kupin.core.data.local.repository.MaterialRepository
import java.lang.StringBuilder

@RequiresApi(Build.VERSION_CODES.O)
class GetAllDataViewModel(application: Application): ViewModel()  {
    private val materialName: MutableLiveData<String> = MutableLiveData()
    private val mMaterialRepository: MaterialRepository = MaterialRepository(application)

    init {
        mMaterialRepository.getAllMaterial().observeForever{ materials ->
            val names = StringBuilder()
            for (material in materials){
                names.append(material.name).append(",")
            }
            materialName.value = names.toString()
        }
    }

    fun getMaterialNames(): LiveData<String> {
        return materialName
    }
}