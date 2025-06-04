package com.nabilacarrissa.transactionapp.ui.main

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.nabilacarrissa.transactionapp.data.db.AppDatabase
import com.nabilacarrissa.transactionapp.data.model.Product
import com.nabilacarrissa.transactionapp.databinding.ActivityMainBinding
import com.nabilacarrissa.transactionapp.ui.adapter.ProductAdapter
import com.nabilacarrissa.transactionapp.ui.add.AddProductActivity
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val productDao by lazy { AppDatabase.getDatabase(this).productDao() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvProducts.layoutManager = LinearLayoutManager(this)

        lifecycleScope.launch {
            productDao.getAllProducts().collect { products ->
                val adapter = ProductAdapter(
                    products,
                    onItemClick = { product ->
                        val intent = Intent(this@MainActivity, AddProductActivity::class.java)
                        intent.putExtra("product", product)
                        startActivity(intent)
                    },
                    onItemLongClick = { product ->
                        showDeleteDialog(product)
                    }
                )
                binding.rvProducts.adapter = adapter
            }
        }

        binding.fabAdd.setOnClickListener {
            startActivity(Intent(this, AddProductActivity::class.java))
        }

        lifecycleScope.launch {
            launch {
                productDao.getTotalProductCount().collect { total ->
                    binding.tvTotalProducts.text = "Total Produk: $total"
                }
            }
            launch {
                productDao.getTotalStockCount().collect { totalStock ->
                    binding.tvTotalStock.text = "Total Stok: ${totalStock ?: 0}"
                }
            }
        }
    }

    private fun showDeleteDialog(product: Product) {
        AlertDialog.Builder(this).apply {
            setTitle("Hapus Produk")
            setMessage("Apakah kamu yakin ingin menghapus \"${product.name}\"?")
            setPositiveButton("Hapus") { _, _ ->
                lifecycleScope.launch {
                    productDao.delete(product)
                }
            }
            setNegativeButton("Batal", null)
            show()
        }
    }

}
