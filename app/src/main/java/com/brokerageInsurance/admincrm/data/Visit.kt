package com.brokerageInsurance.admincrm.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.text.DateFormat

@Entity("visit_table")
@Parcelize
data class Visit(
    val visitLength:String,
    val Address: String,
    val result: String,
    val uId: Int,
    val time: Long = System.currentTimeMillis(),
    @PrimaryKey(autoGenerate = true) val id: Int = 0
) : Parcelable {
    val createdDateFormatted: String
        get() = DateFormat.getDateTimeInstance().format(time)
}
