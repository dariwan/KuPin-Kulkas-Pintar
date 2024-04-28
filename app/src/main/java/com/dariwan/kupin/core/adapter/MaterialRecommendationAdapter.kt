package com.dariwan.kupin.core.adapter

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.dariwan.kupin.core.data.local.database.Material
import com.dariwan.kupin.core.utils.MaterialDiffCallback
import com.dariwan.kupin.databinding.MaterialListBinding
import com.dariwan.kupin.databinding.RecomMaterialListBinding

@RequiresApi(Build.VERSION_CODES.O)
class MaterialRecommendationAdapter: RecyclerView.Adapter<MaterialRecommendationAdapter.MaterialRecommendationViewHolder>(){
    private val listMaterial = ArrayList<Material>()

    class MaterialRecommendationViewHolder(val binding: RecomMaterialListBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(material: Material){
            with(binding){
                tvMaterial.text = material.name
                tvDate.text = material.date
            }
        }
    }

    fun setListMaterial(listMaterial: List<Material>){
        val diffCallback = MaterialDiffCallback(this.listMaterial, listMaterial)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.listMaterial.clear()
        this.listMaterial.addAll(listMaterial)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): MaterialRecommendationAdapter.MaterialRecommendationViewHolder {
        val binding = RecomMaterialListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MaterialRecommendationViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: MaterialRecommendationAdapter.MaterialRecommendationViewHolder,
        position: Int,
    ) {
        val material = listMaterial[position]
        holder.bind(material)
    }

    override fun getItemCount(): Int {
        return listMaterial.size
    }
}