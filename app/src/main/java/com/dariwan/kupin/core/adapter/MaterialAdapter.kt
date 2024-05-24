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
import com.dariwan.kupin.core.data.local.database.Material
import com.dariwan.kupin.core.utils.MaterialDiffCallback
import com.dariwan.kupin.databinding.MaterialListBinding
import com.dariwan.kupin.view.home.RefrigeneratorViewModel
import com.dariwan.kupin.view.home.detail.DetailMaterialActivity
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

@RequiresApi(Build.VERSION_CODES.O)
class MaterialAdapter(private val refrigeneratorViewModel: RefrigeneratorViewModel): RecyclerView.Adapter<MaterialAdapter.MaterialViewHolder>() {
    private val listMaterial = ArrayList<Material>()

    fun setListMaterial(listMaterial: List<Material>){
        val diffCallback = MaterialDiffCallback(this.listMaterial, listMaterial)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.listMaterial.clear()
        this.listMaterial.addAll(listMaterial)
        diffResult.dispatchUpdatesTo(this)
    }

    inner class MaterialViewHolder (val binding: MaterialListBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(material: Material){
            with(binding){
                tvMaterial.text = material.name
                tvQuantity.text = material.quantity.toString()
                tvDate.text = material.date
                tvCategory.text = material.category

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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MaterialViewHolder {
        val binding = MaterialListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MaterialViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MaterialViewHolder, position: Int) {
        val material = listMaterial[position]
        holder.bind(material)

        holder.binding.ivAdd.setOnClickListener {
            refrigeneratorViewModel.incrementQuantity(material.id)
        }

        holder.binding.ivMinus.setOnClickListener {
            refrigeneratorViewModel.decrementQuantity(material.id)
        }

        holder.binding.root.setOnClickListener {
            val intent = Intent(holder.itemView.context, DetailMaterialActivity::class.java)
            intent.putExtra(DetailMaterialActivity.NAME_MATERIAL, material.name)
            intent.putExtra(DetailMaterialActivity.QUANTITY_MATERIAL, material.quantity)
            intent.putExtra(DetailMaterialActivity.DATE_MATERIAL, material.date)
            intent.putExtra(DetailMaterialActivity.DATE_MATERIAL, material.date)
            intent.putExtra(DetailMaterialActivity.SATUAN_MATERIAL, material.satuan)
            intent.putExtra(DetailMaterialActivity.CATEGORY_MATERIAL, material.category)
            intent.putExtra(DetailMaterialActivity.ID_MATERIAL, material.id)

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

    override fun getItemCount(): Int {
        return listMaterial.size
    }
}