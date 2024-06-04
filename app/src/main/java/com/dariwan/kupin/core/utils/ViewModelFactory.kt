package com.dariwan.kupin.core.utils

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dariwan.kupin.view.main.MainViewModel
import com.dariwan.kupin.view.recomendation.RecomendationViewModel
import com.dariwan.kupin.view.home.RefrigeneratorViewModel
import com.dariwan.kupin.view.home.addkitchenstorage.AddKitchenStorageViewModel
import com.dariwan.kupin.view.home.addmaterial.AddMaterialViewModel
import com.dariwan.kupin.view.home.detail.DetailViewModel
import com.dariwan.kupin.view.home.editmaterial.EditMaterialViewModel
import com.dariwan.kupin.view.home.kitchenstorage.KitchenStorageViewModel
import com.dariwan.kupin.view.home.report.ReportViewModel
import com.dariwan.kupin.view.recipe.GetAllDataViewModel
import java.lang.IllegalArgumentException

@RequiresApi(Build.VERSION_CODES.O)
class ViewModelFactory private constructor(private val mApplication: Application):
    ViewModelProvider.NewInstanceFactory(){

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RefrigeneratorViewModel::class.java)){
            return RefrigeneratorViewModel(mApplication) as T
        } else if (modelClass.isAssignableFrom(AddMaterialViewModel::class.java)){
            return AddMaterialViewModel(mApplication) as T
        } else if (modelClass.isAssignableFrom(DetailViewModel::class.java)){
            return DetailViewModel(mApplication) as T
        } else if (modelClass.isAssignableFrom(EditMaterialViewModel::class.java)){
            return EditMaterialViewModel(mApplication) as T
        } else if (modelClass.isAssignableFrom(MainViewModel::class.java)){
            return MainViewModel(mApplication) as T
        } else if (modelClass.isAssignableFrom(RecomendationViewModel::class.java)){
            return RecomendationViewModel(mApplication) as T
        } else if (modelClass.isAssignableFrom(ReportViewModel::class.java)){
            return ReportViewModel(mApplication) as T
        } else if (modelClass.isAssignableFrom(GetAllDataViewModel::class.java)){
            return GetAllDataViewModel(mApplication) as T
        }else if (modelClass.isAssignableFrom(AddKitchenStorageViewModel::class.java)){
            return AddKitchenStorageViewModel(mApplication) as T
        }else if (modelClass.isAssignableFrom(KitchenStorageViewModel::class.java)){
            return KitchenStorageViewModel(mApplication) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class : ${modelClass.name}")
    }


        companion object{
            @Volatile
            private var INSTANCE: ViewModelFactory? = null

            @JvmStatic
            fun getInstance(application: Application): ViewModelFactory{
                if (INSTANCE == null){
                    synchronized(ViewModelFactory::class.java){
                        INSTANCE = ViewModelFactory(application)
                    }
                }
                return INSTANCE as ViewModelFactory
            }
        }
}