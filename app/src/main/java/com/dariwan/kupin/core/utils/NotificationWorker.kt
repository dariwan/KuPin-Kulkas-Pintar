package com.dariwan.kupin.core.utils

import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.dariwan.kupin.core.data.local.database.MaterialRoomDatabase
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.Calendar
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
class NotificationWorker(context: Context, params: WorkerParameters): Worker(context, params) {
    override fun doWork(): Result {
        val materialList = MaterialRoomDatabase.getDatabase(applicationContext).materialDao()
            .getAllMaterials().value

        materialList?.forEach { material ->
            val formatter = DateTimeFormatter.ofPattern("dd - MM - yyyy")
            val currentDate = LocalDate.now()
            val materialDate = LocalDate.parse(material.date, formatter)

            if (materialDate != null){
                val difference = ChronoUnit.DAYS.between(currentDate, materialDate)
                if (difference <= 5){
                    val daysLeft = difference + 1
                    val notificationMessage = "Persediaan ${material.name} akan habis dalam $daysLeft hari."
                    val notificationId = material.id
                    NotificationUtils.showNotification(applicationContext, "Peringatan Persediaan", notificationMessage, notificationId)
                }
            }
        }
        return Result.success()
    }
}