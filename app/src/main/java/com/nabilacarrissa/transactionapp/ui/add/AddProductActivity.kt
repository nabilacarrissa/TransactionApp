package com.nabilacarrissa.transactionapp.ui.add

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.nabilacarrissa.transactionapp.data.db.AppDatabase
import com.nabilacarrissa.transactionapp.data.model.Product
import com.nabilacarrissa.transactionapp.databinding.ActivityAddProductBinding
import kotlinx.coroutines.launch

class AddProductActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddProductBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val productDao = AppDatabase.getDatabase(this).productDao()

        binding.btnSave.setOnClickListener {
            val name = binding.etName.text.toString()
            val category = binding.etCategory.text.toString()
            val price = binding.etPrice.text.toString().toDoubleOrNull()
            val stock = binding.etStock.text.toString().toIntOrNull()

            if (name.isNotEmpty() && category.isNotEmpty() && price != null && stock != null) {
                val product = Product(name = name, category = category, price = price, stock = stock)
                lifecycleScope.launch {
                    productDao.insert(product)
                    finish() // kembali ke MainActivity
                }
            } else {
                Toast.makeText(this, "Isi semua data dengan benar", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
