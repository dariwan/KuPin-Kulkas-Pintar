package com.dariwan.kupin.core.utils

import androidx.recyclerview.widget.DiffUtil
import com.dariwan.kupin.core.data.local.database.kulkasku.Material

class MaterialDiffCallback(private val oldMaterialList: List<Material>, private val newMaterialList: List<Material>): DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldMaterialList.size
    }

    override fun getNewListSize(): Int {
        return newMaterialList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldMaterialList[oldItemPosition].id == newMaterialList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldMaterial = oldMaterialList[oldItemPosition]
        val newMaterial = newMaterialList[newItemPosition]

        return oldMaterial.name == newMaterial.name && oldMaterial.quantity == newMaterial.quantity
    }

}