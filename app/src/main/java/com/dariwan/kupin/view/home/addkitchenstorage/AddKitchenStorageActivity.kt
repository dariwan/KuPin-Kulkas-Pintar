package com.dariwan.kupin.view.home.addkitchenstorage

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import com.dariwan.kupin.R
import com.dariwan.kupin.core.data.local.database.kitchencabinet.KitchenCabinet
import com.dariwan.kupin.core.data.local.database.kulkasku.Material
import com.dariwan.kupin.core.utils.ViewModelFactory
import com.dariwan.kupin.databinding.ActivityAddKitchenStorageBinding
import com.dariwan.kupin.view.home.addmaterial.AddMaterialActivity
import com.dariwan.kupin.view.home.addmaterial.AddMaterialViewModel
import com.dariwan.kupin.view.home.material.MaterialActivity
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Calendar

@RequiresApi(Build.VERSION_CODES.O)
class AddKitchenStorageActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddKitchenStorageBinding
    private val calendar = Calendar.getInstance()
    private lateinit var addKitchenStorageViewModel: AddKitchenStorageViewModel
    private var materialDate: String? = null
    private var kitchenCabinet: KitchenCabinet? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddKitchenStorageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        addKitchenStorageViewModel = obtainViewModel(this@AddKitchenStorageActivity)
        kitchenCabinet = KitchenCabinet()

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

    private fun viewDate() {
        val dateFormat = "dd - MM - yyyy"
        val simpleDateFormat = SimpleDateFormat(dateFormat)
        materialDate = simpleDateFormat.format(calendar.timeInMillis)
        Log.e("datematerial", "date : $materialDate")
        binding.tvDateCalendar.text = simpleDateFormat.format(calendar.timeInMillis)
    }

    private fun setupButton() {
        binding.btnSimpan.setOnClickListener {
            createMaterial()
        }
    }

    private fun createMaterial() {
        val materialName = binding.etMaterialName.text.toString()
        val materialQuantity = binding.etMaterialQuantity.text.toString()
        val satuan = binding.spSatuan.selectedItem.toString()
        val location_storage = binding.spLocationStorage.selectedItem.toString()
        val category = binding.spCategory.selectedItem.toString()
        val dateMaterial = materialDate
        val date = LocalDate.now()
        val formatter = DateTimeFormatter.ofPattern("dd - MM - yyyy")
        val dateNow = date.format(formatter)

        if (materialName.isEmpty()) {
            binding.etMaterialNameLayout.error = getString(R.string.error_field)
            return
        } else if (materialQuantity.isEmpty()) {
            binding.etMaterialQuantityLayout.error = getString(R.string.error_field)
        } else if (dateMaterial.isNullOrEmpty()) {
            Toast.makeText(this, "Tanggal harus dipilih", Toast.LENGTH_SHORT).show()
        } else {
            kitchenCabinet.let { kitchenCabinet ->
                kitchenCabinet?.name = materialName
                kitchenCabinet?.quantity = materialQuantity.toInt()
                kitchenCabinet?.date = dateMaterial
                kitchenCabinet?.date_input = dateNow
                kitchenCabinet?.satuan = satuan
                kitchenCabinet?.category = category
                kitchenCabinet?.lokasi_penyimpanan = location_storage
                Log.e(
                    "data_material",
                    "data: ${kitchenCabinet?.name}, ${kitchenCabinet?.quantity}, ${kitchenCabinet?.date}, ${kitchenCabinet?.date_input}, " +
                            "${kitchenCabinet?.satuan}, ${kitchenCabinet?.category}" +
                            "${kitchenCabinet?.lokasi_penyimpanan}"
                )
            }
            addKitchenStorageViewModel.insertKitchenStorage(kitchenCabinet as KitchenCabinet)
            Log.e("data_material", "data: $kitchenCabinet")
            Toast.makeText(this, "Data berhasil dibuat", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, MaterialActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()
        }

    }

    private fun obtainViewModel(activity: AppCompatActivity): AddKitchenStorageViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(AddKitchenStorageViewModel::class.java)
    }
}