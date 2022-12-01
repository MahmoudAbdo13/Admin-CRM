package com.brokerageInsurance.admincrm.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.text.DateFormat
import java.time.format.DateTimeFormatter

@Entity("customer_table")
@Parcelize
data class Customer(
    val name:String,
    val address:String,
    val email:String,
    val phone: String,
    val followUp:Boolean,
    val time: Long = System.currentTimeMillis(),
    @PrimaryKey(autoGenerate = true) val id: Int = 0
) : Parcelable {
    val createdDateFormatted: String
        get() = DateFormat.getDateTimeInstance().format(time)
}

