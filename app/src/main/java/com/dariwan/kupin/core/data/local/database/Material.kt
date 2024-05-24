package com.dariwan.kupin.core.data.local.database

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@Entity
@Parcelize
data class Material(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0,

    @ColumnInfo(name = "name")
    var name: String? = null,

    @ColumnInfo(name = "quantity")
    var quantity: Int = 0,

    @ColumnInfo(name = "date")
    var date: String? = null,

    @ColumnInfo(name ="date_input")
    var date_input: String? = null,

    @ColumnInfo(name ="category")
    var category: String? = null,

    @ColumnInfo(name ="satuan")
    var satuan: String? = null,

    @ColumnInfo(name = "notificationSent")
    var notificationSent: Int = 0
) : Parcelable
