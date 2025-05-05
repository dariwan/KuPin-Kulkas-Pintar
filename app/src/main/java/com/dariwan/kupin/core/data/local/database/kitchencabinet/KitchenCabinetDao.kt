package com.dariwan.kupin.core.data.local.database.kitchencabinet

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dariwan.kupin.core.data.local.database.kulkasku.Material

@Dao
interface KitchenCabinetDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(kitchenCabinet: KitchenCabinet)

    @Query("SELECT * FROM kitchencabinet")
    fun getAllMaterials(): LiveData<List<KitchenCabinet>>

    @Query("UPDATE kitchencabinet SET quantity = quantity + 1 WHERE id = :id")
    fun incrementQuantity(id: Int)

    @Query("UPDATE kitchencabinet SET quantity = quantity - 1 WHERE id = :id AND quantity > 0")
    fun decrementQuantity(id: Int)

    @Query("SELECT * FROM kitchencabinet WHERE category = :category")
    fun getCategory(category: String): LiveData<List<KitchenCabinet>>

    @Query("DELETE FROM kitchencabinet WHERE id = :id")
    fun delete(id: Int)

    @Query("UPDATE kitchencabinet SET name = :nameValue, quantity = :quantityValue, date = :dateValue, satuan = :satuan, category = :category, lokasi_penyimpanan = :lokasi_penyimpanan WHERE id = :id")
    fun update(id: Int, nameValue: String, quantityValue: Int, dateValue: String, satuan: String, category: String, lokasi_penyimpanan: String)

    @Query("UPDATE kitchencabinet SET notificationSent = :notificationSent WHERE id = :id")
    fun updateNotificationSent(id: Int, notificationSent: Int)
}