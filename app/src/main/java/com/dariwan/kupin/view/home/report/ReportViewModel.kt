package com.dariwan.kupin.view.home.report

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.dariwan.kupin.core.data.local.database.Material
import com.dariwan.kupin.core.data.local.repository.MaterialRepository
@RequiresApi(Build.VERSION_CODES.O)

class ReportViewModel(application: Application): ViewModel() {
    private val mMaterialRepository: MaterialRepository = MaterialRepository(application)

    fun getMaterialsByDate(startDate: String, endDate: String): LiveData<List<Material>>{
        return mMaterialRepository.getMaterialsByDate(startDate, endDate)
    }
}