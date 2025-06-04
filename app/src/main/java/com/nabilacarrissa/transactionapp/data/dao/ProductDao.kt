package com.nabilacarrissa.transactionapp.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.nabilacarrissa.transactionapp.data.model.Product
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(product: Product)

    @Update
    suspend fun update(product: Product)

    @Delete
    suspend fun delete(product: Product)

    @Query("SELECT * FROM products ORDER BY id DESC")
    fun getAllProducts(): Flow<List<Product>>

    @Query("SELECT * FROM products WHERE name LIKE '%' || :query || '%' ORDER BY id DESC")
    fun searchProducts(query: String): Flow<List<Product>>

    @Query("SELECT * FROM products WHERE category = :category ORDER BY id DESC")
    fun filterByCategory(category: String): Flow<List<Product>>

    // Statistik: jumlah produk
    @Query("SELECT COUNT(*) FROM products")
    fun getTotalProductCount(): Flow<Int?>

    // Statistik: total stok
    //@Query("SELECT SUM(stock) FROM products")
    //fun getTotalStockCount(): Flow<Int?> // Gunakan Int? (nullable)

    @Query("SELECT IFNULL(SUM(stock), 0) FROM products")
    fun getTotalStockCount(): Flow<Int>

}
