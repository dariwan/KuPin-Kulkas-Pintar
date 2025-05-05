package com.dariwan.kupin.view.home.editmaterial

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import com.dariwan.kupin.R
import com.dariwan.kupin.core.data.local.database.kitchencabinet.KitchenCabinet
import com.dariwan.kupin.core.data.local.database.kulkasku.Material
import com.dariwan.kupin.core.utils.ViewModelFactory
import com.dariwan.kupin.databinding.ActivityEditMaterialBinding
import com.dariwan.kupin.databinding.ActivityEditStorageBinding
import com.dariwan.kupin.view.home.detail.DetailMaterialActivity
import com.dariwan.kupin.view.home.kitchenstorage.KitchenStorageActivity
import com.dariwan.kupin.view.home.material.MaterialActivity
import com.dariwan.kupin.view.main.MainActivity
import java.text.SimpleDateFormat
import java.util.Calendar

@RequiresApi(Build.VERSION_CODES.O)
class EditStorageActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditStorageBinding
    private lateinit var editMaterialViewModel: EditMaterialViewModel
    private val calendar = Calendar.getInstance()
    private var materialDate: String? = null
    private var storage: KitchenCabinet? = null
    private var id: Int? = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditStorageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        editMaterialViewModel = obtainViewModel(this@EditStorageActivity)
        storage = KitchenCabinet()

        setupButton()
        setupCalendar()

    }

    private fun setupCalendar() {
        val dataSetListener =
            DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                calendar.set(Calendar.YEAR, year)
                calendar.set(Calendar.MONTH, monthOfYear)
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                viewDate()
            }

        binding.ivCalendar.setOnClickListener {
            DatePickerDialog(
                this,
                dataSetListener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH),
            ).show()
        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun viewDate() {
        val dateFormat = "dd - MM - yyyy"
        val simpleDateFormat = SimpleDateFormat(dateFormat)
        materialDate = simpleDateFormat.format(calendar.timeInMillis)
        Log.e("datematerial", "date : $materialDate")
        binding.tvDateCalendar.text = simpleDateFormat.format(calendar.timeInMillis)
    }

    private fun setupButton() {
        binding.btnSimpan.setOnClickListener {
            updateData()
        }
    }

    private fun updateData() {
        id = intent.getIntExtra(DetailMaterialActivity.ID_MATERIAL, id!!)

        val materialName = binding.etMaterialName.text.toString()
        val materialQuantity = binding.etMaterialQuantity.text.toString()
        val quantity = materialQuantity.toInt()
        val satuan = binding.spSatuan.selectedItem.toString()
        val category = binding.spCategory.selectedItem.toString()
        val location_storage = binding.spLocationStorage.selectedItem.toString()

        val dateMaterial = materialDate

        if (materialName.isEmpty()) {
            binding.etMaterialNameLayout.error = getString(R.string.error_field)
            return
        } else if (materialQuantity.isEmpty()) {
            binding.etMaterialQuantityLayout.error = getString(R.string.error_field)
        } else if (dateMaterial.isNullOrEmpty()) {
            Toast.makeText(this, "Tanggal harus dipilih", Toast.LENGTH_SHORT).show()
        } else {
            Log.e("update", "${id}, ${materialName}, ${quantity}, ${dateMaterial}")
            editMaterialViewModel.updateStorage(id!!, materialName, quantity, dateMaterial, satuan, category, location_storage)
            Toast.makeText(this, "Data berhasil diubah", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, KitchenStorageActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()
        }
    }

    private fun obtainViewModel(activity: AppCompatActivity): EditMaterialViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(EditMaterialViewModel::class.java)
    }

    companion object {
        const val ID_MATERIAL = "id_material"
    }
}