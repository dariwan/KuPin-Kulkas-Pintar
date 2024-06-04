package com.dariwan.kupin.view.home.editmaterial

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import com.dariwan.kupin.core.data.local.repository.MaterialRepository
@RequiresApi(Build.VERSION_CODES.O)
class EditMaterialViewModel(application: Application): ViewModel() {
    private val mMaterialRepository: MaterialRepository = MaterialRepository(application)

    fun update(id: Int, nameValue: String, quantityValue: Int, dateValue: String, satuan: String, category: String, lokasi_penyimpanan: String){
        mMaterialRepository.update(id, nameValue, quantityValue, dateValue, satuan, category, lokasi_penyimpanan)
    }

    fun updateStorage(id: Int, nameValue: String, quantityValue: Int, dateValue: String, satuan: String, category: String, lokasi_penyimpanan: String){
        mMaterialRepository.updateStorage(id, nameValue, quantityValue, dateValue, satuan, category, lokasi_penyimpanan)
    }
}