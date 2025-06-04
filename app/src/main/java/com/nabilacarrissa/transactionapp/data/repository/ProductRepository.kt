package com.nabilacarrissa.transactionapp.data.repository

import androidx.lifecycle.LiveData
import com.nabilacarrissa.transactionapp.data.dao.ProductDao
import com.nabilacarrissa.transactionapp.data.model.Product
import kotlinx.coroutines.flow.Flow

class ProductRepository(private val dao: ProductDao) {
    suspend fun insert(product: Product) = dao.insert(product)
    fun getAllProducts(): Flow<List<Product>> = dao.getAllProducts()
}
