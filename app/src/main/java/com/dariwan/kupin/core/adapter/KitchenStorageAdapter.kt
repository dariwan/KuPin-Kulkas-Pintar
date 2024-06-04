package com.dariwan.kupin.core.adapter

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.dariwan.kupin.core.data.local.database.kitchencabinet.KitchenCabinet
import com.dariwan.kupin.core.data.local.database.kulkasku.Material
import com.dariwan.kupin.core.utils.MaterialDiffCallback
import com.dariwan.kupin.core.utils.StorageDiffCallback
import com.dariwan.kupin.databinding.MaterialListBinding
import com.dariwan.kupin.databinding.StorageListBinding
import com.dariwan.kupin.view.home.RefrigeneratorViewModel
import com.dariwan.kupin.view.home.detail.DetailMaterialActivity
import com.dariwan.kupin.view.home.detail.DetailStorageActivity
import com.dariwan.kupin.view.home.kitchenstorage.KitchenStorageViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

@RequiresApi(Build.VERSION_CODES.O)
class KitchenStorageAdapter(private val kitchenStorageViewModel: KitchenStorageViewModel): RecyclerView.Adapter<KitchenStorageAdapter.StorageViewHolder>() {
    private val listStorage = ArrayList<KitchenCabinet>()

    fun setListMaterial(listStorage: List<KitchenCabinet>){
        val diffCallback = StorageDiffCallback(this.listStorage, listStorage)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.listStorage.clear()
        this.listStorage.addAll(listStorage)
        diffResult.dispatchUpdatesTo(this)
    }

    class StorageViewHolder(val binding: StorageListBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(material: KitchenCabinet){
            with(binding){
                tvMaterial.text = material.name
                tvQuantity.text = material.quantity.toString()
                tvDate.text = material.date
                tvCategory.text = material.category
                tvLocationStorage.text = material.lokasi_penyimpanan

                val formatter = DateTimeFormatter.ofPattern("dd - MM - yyyy")
                val currentDate = LocalDate.now()
                val materialDate = LocalDate.parse(material.date, formatter)
                val difference = ChronoUnit.DAYS.between(currentDate, materialDate)

                if (difference <= 5){
                    ivAlert.visibility = View.VISIBLE
                } else{
                    ivAlert.visibility = View.GONE
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StorageViewHolder {
        val binding = StorageListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StorageViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return listStorage.size
    }

    override fun onBindViewHolder(holder: StorageViewHolder, position: Int) {
        val material = listStorage[position]
        holder.bind(material)

        holder.binding.ivAdd.setOnClickListener {
            kitchenStorageViewModel.incrementQuantityStorage(material.id)
        }

        holder.binding.ivMinus.setOnClickListener {
            kitchenStorageViewModel.decrementQuantityStorage(material.id)
        }

        holder.binding.root.setOnClickListener {
            val intent = Intent(holder.itemView.context, DetailStorageActivity::class.java)
            intent.putExtra(DetailStorageActivity.NAME_MATERIAL, material.name)
            intent.putExtra(DetailStorageActivity.QUANTITY_MATERIAL, material.quantity)
            intent.putExtra(DetailStorageActivity.DATE_MATERIAL, material.date)
            intent.putExtra(DetailStorageActivity.DATE_MATERIAL, material.date)
            intent.putExtra(DetailStorageActivity.SATUAN_MATERIAL, material.satuan)
            intent.putExtra(DetailStorageActivity.CATEGORY_MATERIAL, material.category)
            intent.putExtra(DetailStorageActivity.ID_MATERIAL, material.id)
            intent.putExtra(DetailStorageActivity.LOCATION_STORAGE, material.lokasi_penyimpanan)

            val optionsCOmpact: ActivityOptionsCompat =
                ActivityOptionsCompat.makeSceneTransitionAnimation(
                    holder.itemView.context as Activity,
                    Pair(holder.binding.tvMaterial, "material"),
                    Pair(holder.binding.tvQuantity, "quantity"),
                    Pair(holder.binding.tvDate, "date"),
                )
            holder.itemView.context.startActivity(intent, optionsCOmpact.toBundle())
        }
    }
}