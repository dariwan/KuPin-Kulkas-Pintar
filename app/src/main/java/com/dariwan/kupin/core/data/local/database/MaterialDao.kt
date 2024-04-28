package com.dariwan.kupin.core.data.local.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.dariwan.kupin.core.data.local.database.Material


@Dao
interface MaterialDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(material: Material)

    @Query("UPDATE material SET name = :nameValue, quantity = :quantityValue, date = :dateValue WHERE id = :id")
    fun update(id: Int, nameValue: String, quantityValue: Int, dateValue: String)

    @Query("DELETE FROM material WHERE id = :id")
    fun delete(id: Int)

    @Query("SELECT * FROM material ORDER BY date")
    fun getAllMterial(): LiveData<List<Material>>

    @Query("UPDATE material SET quantity = quantity + 1 WHERE id = :id")
    fun incrementQuantity(id: Int)

    @Query("UPDATE material SET quantity = quantity - 1 WHERE id = :id AND quantity > 0")
    fun decrementQuantity(id: Int)

    @Query("SELECT * FROM material")
    fun getAllMaterials(): LiveData<List<Material>>

    @Query("UPDATE material SET notificationSent = :notificationSent WHERE id = :id")
    fun updateNotificationSent(id: Int, notificationSent: Int)

    @Query("SELECT * FROM material WHERE quantity <= 5")
    fun getRecommendationMaterial(): LiveData<List<Material>>
}