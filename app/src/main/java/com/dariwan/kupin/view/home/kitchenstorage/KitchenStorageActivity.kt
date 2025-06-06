package com.dariwan.kupin.view.home.kitchenstorage

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dariwan.kupin.R
import com.dariwan.kupin.core.adapter.KitchenStorageAdapter
import com.dariwan.kupin.core.adapter.MaterialAdapter
import com.dariwan.kupin.core.utils.ViewModelFactory
import com.dariwan.kupin.databinding.ActivityKitchenStorageBinding
import com.dariwan.kupin.view.home.RefrigeneratorViewModel
import com.dariwan.kupin.view.home.addkitchenstorage.AddKitchenStorageActivity
import com.dariwan.kupin.view.home.addmaterial.AddMaterialActivity
import com.google.android.material.chip.Chip

@RequiresApi(Build.VERSION_CODES.O)
class KitchenStorageActivity : AppCompatActivity() {
    private lateinit var binding: ActivityKitchenStorageBinding
    private lateinit var adapter: KitchenStorageAdapter
    private lateinit var kitchenStorageViewModel: KitchenStorageViewModel
    private val rotateOpen: Animation by lazy { AnimationUtils.loadAnimation(this, R.anim.rotate_open_anim) }
    private val rotateClose: Animation by lazy { AnimationUtils.loadAnimation(this, R.anim.rotate_close_anim) }
    private val fromBottom: Animation by lazy { AnimationUtils.loadAnimation(this, R.anim.from_bottom_anim) }
    private val toBottom: Animation by lazy { AnimationUtils.loadAnimation(this, R.anim.to_bottom_anim) }
    private var clicked = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityKitchenStorageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        kitchenStorageViewModel = obtainViewModel(this as AppCompatActivity)
        setupRv()
        setupButton()
        setupChip()
    }

    private fun setupChip() {
        binding.cgCategory.setOnCheckedChangeListener { group, checkedIds ->
            val chip: Chip? = group.findViewById(checkedIds)
            if (checkedIds != View.NO_ID) {
                chip?.let {
                    kitchenStorageViewModel.getCategoryStorage(chip?.text.toString())
                        .observe(this) { materialList ->
                            if (materialList != null) {
                                adapter.setListMaterial(materialList)
                            }
                        }
                }
            } else {
                kitchenStorageViewModel.getAllMaterialStorage().observe(this) { materialList ->
                    if (materialList != null) {
                        adapter.setListMaterial(materialList)
                    }
                }
            }
        }
    }

    private fun setupButton() {
        binding.fabAdd.setOnClickListener {
            addButtonClicked()
        }

        binding.ivBack.setOnClickListener {
            finish()
        }

        binding.fabStorage.setOnClickListener {
            val intent = Intent(this@KitchenStorageActivity, AddKitchenStorageActivity::class.java)
            startActivity(intent)

        }
        binding.fabKulkasku.setOnClickListener {
            val intent = Intent(this@KitchenStorageActivity, AddMaterialActivity::class.java)
            startActivity(intent)
        }
    }

    private fun addButtonClicked() {
        setVisibility(clicked)
        setAnimation(clicked)
        clicked = !clicked
    }

    private fun setAnimation(clicked: Boolean) {
        if (!clicked){
            binding.fabKulkasku.startAnimation(fromBottom)
            binding.fabStorage.startAnimation(fromBottom)
            binding.fabAdd.startAnimation(rotateOpen)
        } else{
            binding.fabKulkasku.startAnimation(toBottom)
            binding.fabStorage.startAnimation(toBottom)
            binding.fabAdd.startAnimation(rotateClose)
        }
    }

    private fun setVisibility(clicked: Boolean) {
        if (!clicked){
            binding.fabKulkasku.visibility = View.VISIBLE
            binding.fabStorage.visibility = View.VISIBLE
        } else{
            binding.fabKulkasku.visibility = View.INVISIBLE
            binding.fabStorage.visibility = View.INVISIBLE
        }
    }

    private fun setupRv() {
        kitchenStorageViewModel.getAllMaterialStorage().observe(this){storageList ->
            if (storageList != null) {
                adapter.setListMaterial(storageList)
            } else {
                binding.tvNoData.visibility = View.VISIBLE
                binding.rvMaterial.visibility = View.GONE
            }
        }

        adapter = KitchenStorageAdapter(kitchenStorageViewModel)
        binding.rvMaterial.layoutManager = LinearLayoutManager(this)
        binding.rvMaterial.setHasFixedSize(true)
        binding.rvMaterial.adapter = adapter
    }

    private fun obtainViewModel(activity: AppCompatActivity): KitchenStorageViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(KitchenStorageViewModel::class.java)
    }
}