package com.nabilacarrissa.transactionapp

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.nabilacarrissa.transactionapp.data.dao.ProductDao
import com.nabilacarrissa.transactionapp.data.db.AppDatabase
import com.nabilacarrissa.transactionapp.data.model.Product
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.*
import org.junit.Assert.*
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ProductDaoTest {

    private lateinit var database: AppDatabase
    private lateinit var productDao: ProductDao  // ✅ tambahkan ini

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        productDao = database.productDao()
    }


    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun insertProduct_savesDataCorrectly() = runBlocking {
        val dao = database.productDao()
        val product = Product(
            name = "Produk Uji",
            category = "Test",
            price = 15000.0,
            stock = 3,
            imageRes = 0 // bisa 0 karena tidak perlu gambar asli di unit test
        )

        dao.insert(product)
        val result = dao.getAllProducts().first()

        assertEquals(1, result.size)
        assertEquals("Produk Uji", result[0].name)
    }

    @Test
    fun deleteProduct_removesData() = runBlocking {
        val product = Product(
            name = "Produk Hapus",
            category = "Test",
            price = 10000.0,
            stock = 2,
            imageRes = 0
        )

        productDao.insert(product)

        // Ambil product yang barusan disimpan (misalnya by name atau ambil semua lalu filter)
        val insertedProduct = productDao.getAllProducts().first().firstOrNull {
            it.name == "Produk Hapus"
        }

        assertNotNull(insertedProduct) // pastikan product berhasil disimpan

        productDao.delete(insertedProduct!!)
        val result = productDao.getAllProducts().first()

        assertTrue(result.isEmpty())
    }

}
