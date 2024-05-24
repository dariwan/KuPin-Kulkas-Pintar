package com.dariwan.kupin.core.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.dariwan.kupin.core.data.local.database.Material
import com.dariwan.kupin.core.utils.MaterialDiffCallback
import com.dariwan.kupin.databinding.ReportListBinding

class ReportAdapter: RecyclerView.Adapter<ReportAdapter.ReportViewHolder>() {

    private val listReport = ArrayList<Material>()

    fun setListMaterial(listReport: List<Material>){
        val diffCallback = MaterialDiffCallback(this.listReport, listReport)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.listReport.clear()
        this.listReport.addAll(listReport)
        diffResult.dispatchUpdatesTo(this)
    }

    class ReportViewHolder(val binding: ReportListBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(material: Material){
            with(binding){
                tvMaterial.text = material.name
                tvDate.text = material.quantity.toString()
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ReportAdapter.ReportViewHolder {
        val binding = ReportListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ReportViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ReportAdapter.ReportViewHolder, position: Int) {
        val report = listReport[position]
        holder.bind(report)
    }

    override fun getItemCount(): Int {
        return listReport.size
    }

}