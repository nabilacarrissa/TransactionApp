package com.nabilacarrissa.transactionapp.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.nabilacarrissa.transactionapp.R
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "products")
data class Product(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val category: String,
    val price: Double,
    val stock: Int = 0,
    val imageRes: Int = R.drawable.sample_product // 👈 default
) : Parcelable
