package com.dariwan.kupin.view.refrigenerator.editmaterial

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import com.dariwan.kupin.core.data.local.database.Material
import com.dariwan.kupin.core.data.local.repository.MaterialRepository
@RequiresApi(Build.VERSION_CODES.O)
class EditMaterialViewModel(application: Application): ViewModel() {
    private val mMaterialRepository: MaterialRepository = MaterialRepository(application)

    fun update(id: Int, nameValue: String, quantityValue: Int, dateValue: String){
        mMaterialRepository.update(id, nameValue, quantityValue, dateValue)
    }
}