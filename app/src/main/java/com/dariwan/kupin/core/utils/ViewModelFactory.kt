package com.dariwan.kupin.core.utils

import android.app.Application
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dariwan.kupin.view.main.MainViewModel
import com.dariwan.kupin.view.refrigenerator.RefrigeneratorViewModel
import com.dariwan.kupin.view.refrigenerator.addmaterial.AddMaterialViewModel
import com.dariwan.kupin.view.refrigenerator.detail.DetailViewModel
import com.dariwan.kupin.view.refrigenerator.editmaterial.EditMaterialViewModel
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