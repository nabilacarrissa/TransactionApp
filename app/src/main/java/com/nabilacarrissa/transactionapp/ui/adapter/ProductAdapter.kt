package com.nabilacarrissa.transactionapp.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.nabilacarrissa.transactionapp.R
import com.nabilacarrissa.transactionapp.data.model.Product
import com.nabilacarrissa.transactionapp.databinding.ItemProductBinding

class ProductAdapter(
    private val products: List<Product>,
    private val onItemClick: (Product) -> Unit,
    private val onItemLongClick: (Product) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val VIEW_TYPE_PRODUCT = 1
        private const val VIEW_TYPE_EMPTY = 2
    }

    inner class ProductViewHolder(val binding: ItemProductBinding) :
        RecyclerView.ViewHolder(binding.root)

    inner class EmptyStockViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView)

    override fun getItemViewType(position: Int): Int {
        return if (products[position].stock == 0) VIEW_TYPE_EMPTY else VIEW_TYPE_PRODUCT
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_EMPTY -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_product_empty, parent, false)
                EmptyStockViewHolder(view)
            }
            else -> {
                val binding = ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                ProductViewHolder(binding)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val product = products[position]

        when (holder) {
            is ProductViewHolder -> {
                with(holder.binding) {
                    tvName.text = product.name
                    tvCategory.text = "Kategori: ${product.category}"
                    tvPriceStock.text = "Harga: Rp${product.price ?: 0} | Stok: ${product.stock ?: 0}"

                    root.setOnClickListener { onItemClick(product) }
                    root.setOnLongClickListener {
                        onItemLongClick(product)
                        true
                    }
                }
            }
            is EmptyStockViewHolder -> {
                val tvNameEmpty = holder.itemView.findViewById<TextView>(R.id.tvNameEmpty)
                val tvWarning = holder.itemView.findViewById<TextView>(R.id.tvEmptyWarning)
                tvNameEmpty.text = product.name
                tvWarning.text = "⚠ Stok Habis!"

                holder.itemView.setOnClickListener { onItemClick(product) }
                holder.itemView.setOnLongClickListener {
                    onItemLongClick(product)
                    true
                }
            }
        }
    }

    override fun getItemCount(): Int = products.size
}
