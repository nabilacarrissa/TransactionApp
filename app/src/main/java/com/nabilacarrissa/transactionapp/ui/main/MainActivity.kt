package com.nabilacarrissa.transactionapp.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.nabilacarrissa.transactionapp.data.db.AppDatabase
import com.nabilacarrissa.transactionapp.databinding.ActivityMainBinding
import com.nabilacarrissa.transactionapp.ui.adapter.ProductAdapter
import com.nabilacarrissa.transactionapp.ui.add.AddProductActivity
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.collect


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val productDao by lazy { AppDatabase.getDatabase(this).productDao() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = ProductAdapter(listOf())
        binding.rvProducts.layoutManager = LinearLayoutManager(this)
        binding.rvProducts.adapter = adapter

        lifecycleScope.launch {
            productDao.getAllProducts().collect { products ->
                binding.rvProducts.adapter = ProductAdapter(products)
            }
        }

        binding.fabAdd.setOnClickListener {
            startActivity(Intent(this, AddProductActivity::class.java))
        }
    }
}
