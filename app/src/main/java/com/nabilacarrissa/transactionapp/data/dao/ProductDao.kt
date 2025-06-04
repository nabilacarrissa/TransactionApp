package com.nabilacarrissa.transactionapp.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.nabilacarrissa.transactionapp.data.model.Product

@Dao
interface ProductDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(product: Product)

    @Update
    suspend fun update(product: Product)

    @Delete
    suspend fun delete(product: Product)

    @Query("SELECT * FROM products ORDER BY id DESC")
    fun getAllProducts(): LiveData<List<Product>>
}
