package com.dariwan.kupin.core.data.local.repository

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.dariwan.kupin.core.data.local.database.Material
import com.dariwan.kupin.core.data.local.database.MaterialDao
import com.dariwan.kupin.core.data.local.database.MaterialRoomDatabase
import com.dariwan.kupin.core.utils.NotificationWorker
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
@RequiresApi(Build.VERSION_CODES.O)
class MaterialRepository(application: Application) {
    private val mMaterialDao: MaterialDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = MaterialRoomDatabase.getDatabase(application)
        mMaterialDao = db.materialDao()
    }

    fun getAllMaterial(): LiveData<List<Material>> = mMaterialDao.getAllMterial()
    fun getAllMaterials(): LiveData<List<Material>> = mMaterialDao.getAllMaterials()

    fun insert(material: Material){
        executorService.execute{
            mMaterialDao.insert(material)
        }
    }

    fun delete(id: Int){
        executorService.execute{
            mMaterialDao.delete(id)
        }
    }

    fun update(id: Int, nameValue: String, quantityValue: Int, dateValue: String){
        executorService.execute{
            mMaterialDao.update(id, nameValue, quantityValue, dateValue)
        }

    }

    fun updateMaterialNotification(id: Int, notificationSent: Int){
        executorService.execute {
            mMaterialDao.updateNotificationSent(id, notificationSent)
        }
    }

    fun incrementQuantity(id: Int){
        executorService.execute {
            mMaterialDao.incrementQuantity(id)
        }
    }

    fun decrementQuantity(id: Int){
        executorService.execute { mMaterialDao.decrementQuantity(id) }
    }
}