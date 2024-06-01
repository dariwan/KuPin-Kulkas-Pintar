package com.dariwan.kupin.view.home.material

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.dariwan.kupin.R
import com.dariwan.kupin.core.adapter.MaterialAdapter
import com.dariwan.kupin.core.utils.ViewModelFactory
import com.dariwan.kupin.databinding.ActivityMaterialBinding
import com.dariwan.kupin.view.home.RefrigeneratorFragment
import com.dariwan.kupin.view.home.RefrigeneratorViewModel
import com.dariwan.kupin.view.home.addmaterial.AddMaterialActivity
import com.google.android.material.chip.Chip

@RequiresApi(Build.VERSION_CODES.O)
class MaterialActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMaterialBinding
    private lateinit var adapter: MaterialAdapter
    private lateinit var refrigeneratorViewModel: RefrigeneratorViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMaterialBinding.inflate(layoutInflater)
        setContentView(binding.root)

        refrigeneratorViewModel = obtainViewModel(this as AppCompatActivity)
        setupRv()
        setupButton()
        setupChip()
    }

    private fun setupChip() {
        binding.cgCategory.setOnCheckedChangeListener { group, checkedIds ->
            val chip: Chip? = group.findViewById(checkedIds)
            if (checkedIds != View.NO_ID) {
                chip?.let {
                    refrigeneratorViewModel.getCategory(chip?.text.toString())
                        .observe(this) { materialList ->
                            if (materialList != null) {
                                adapter.setListMaterial(materialList)
                            }
                        }
                }
            } else {
                refrigeneratorViewModel.getAllMaterial().observe(this) { materialList ->
                    if (materialList != null) {
                        adapter.setListMaterial(materialList)
                    }
                }
            }
        }
    }

    private fun setupButton() {
        binding.fabAdd.setOnClickListener {
            val intent = Intent(this, AddMaterialActivity::class.java)
            startActivity(intent)
        }

        binding.ivBack.setOnClickListener {
            finish()
        }
    }

    private fun setupRv() {
        refrigeneratorViewModel.getAllMaterial().observe(this) { materialList ->
            if (materialList != null) {
                adapter.setListMaterial(materialList)
            } else {
                binding.tvNoData.visibility = View.VISIBLE
                binding.rvMaterial.visibility = View.GONE
            }
        }

        adapter = MaterialAdapter(refrigeneratorViewModel)
        binding.rvMaterial.layoutManager = LinearLayoutManager(this)
        binding.rvMaterial.setHasFixedSize(true)
        binding.rvMaterial.adapter = adapter
    }

    private fun obtainViewModel(activity: AppCompatActivity): RefrigeneratorViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(RefrigeneratorViewModel::class.java)
    }
}