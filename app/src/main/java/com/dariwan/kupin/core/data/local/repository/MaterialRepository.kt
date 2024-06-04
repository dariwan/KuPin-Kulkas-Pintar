package com.dariwan.kupin.core.data.local.repository

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import com.dariwan.kupin.core.data.local.database.kulkasku.Material
import com.dariwan.kupin.core.data.local.database.kulkasku.MaterialDao
import com.dariwan.kupin.core.data.local.database.MaterialRoomDatabase
import com.dariwan.kupin.core.data.local.database.kitchencabinet.KitchenCabinet
import com.dariwan.kupin.core.data.local.database.kitchencabinet.KitchenCabinetDao
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
@RequiresApi(Build.VERSION_CODES.O)
class MaterialRepository(application: Application) {
    private val mMaterialDao: MaterialDao
    private val mKitchenCabinet: KitchenCabinetDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = MaterialRoomDatabase.getDatabase(application)
        mMaterialDao = db.materialDao()
        mKitchenCabinet = db.kitchenCabinetDao()
    }

    fun getAllMaterial(): LiveData<List<Material>> = mMaterialDao.getAllMterial()
    fun getRecommendationMaterial(): LiveData<List<Material>> = mMaterialDao.getRecommendationMaterial()
    fun getAllMaterials(): LiveData<List<Material>> = mMaterialDao.getAllMaterials()

    fun getCategory(category: String): LiveData<List<Material>>{
        return mMaterialDao.getCategory(category)
    }

    fun getMaterialsByDate(startDate: String, endDate: String): LiveData<List<Material>>{
        return mMaterialDao.getAllMaterialsByDate(startDate, endDate)
    }
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

    fun update(id: Int, nameValue: String, quantityValue: Int, dateValue: String, satuan: String, category: String, lokasi_penyimpanan: String){
        executorService.execute{
            mMaterialDao.update(id, nameValue, quantityValue, dateValue, satuan, category, lokasi_penyimpanan)
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



    //storage kitchen
    fun insertStorageKitchen(kitchenCabinet: KitchenCabinet){
        executorService.execute {
            mKitchenCabinet.insert(kitchenCabinet)
        }
    }

    fun getAllKitchenStorage(): LiveData<List<KitchenCabinet>> = mKitchenCabinet.getAllMaterials()

    fun incrementQuantityStorage(id: Int){
        executorService.execute {
            mKitchenCabinet.incrementQuantity(id)
        }
    }

    fun decrementQuantityStorage(id: Int){
        executorService.execute { mKitchenCabinet.decrementQuantity(id) }
    }

    fun getCategoryStorage(category: String): LiveData<List<KitchenCabinet>>{
        return mKitchenCabinet.getCategory(category)
    }

    fun deleteStorage(id: Int){
        executorService.execute{
            mKitchenCabinet.delete(id)
        }
    }

    fun updateStorage(id: Int, nameValue: String, quantityValue: Int, dateValue: String, satuan: String, category: String, lokasi_penyimpanan: String){
        executorService.execute{
            mMaterialDao.update(id, nameValue, quantityValue, dateValue, satuan, category, lokasi_penyimpanan)
        }
    }

    fun updateMaterialNotificationStorage(id: Int, notificationSent: Int){
        executorService.execute {
            mKitchenCabinet.updateNotificationSent(id, notificationSent)
        }
    }
}