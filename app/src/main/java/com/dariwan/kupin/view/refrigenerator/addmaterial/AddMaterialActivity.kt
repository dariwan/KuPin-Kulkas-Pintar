package com.dariwan.kupin.view.refrigenerator.addmaterial

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.DatePicker
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import com.dariwan.kupin.R
import com.dariwan.kupin.core.data.local.database.Material
import com.dariwan.kupin.core.utils.ViewModelFactory
import java.text.SimpleDateFormat
import com.dariwan.kupin.databinding.ActivityAddMaterialBinding
import com.dariwan.kupin.view.main.MainActivity
import java.util.Calendar
@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("SimpleDateFormat")
class AddMaterialActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddMaterialBinding
    private val calendar = Calendar.getInstance()
    private lateinit var addMaterialViewModel: AddMaterialViewModel
    private var materialDate: String? = null
    private var material: Material? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddMaterialBinding.inflate(layoutInflater)
        setContentView(binding.root)

        addMaterialViewModel = obtainViewModel(this@AddMaterialActivity)
        material = Material()

        setupButton()
        setupCalendar()
    }

    private fun obtainViewModel(activity: AppCompatActivity): AddMaterialViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(AddMaterialViewModel::class.java)
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
        binding.ivBack.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        binding.btnSimpan.setOnClickListener {
            createMaterial()
        }
    }

    private fun createMaterial() {
        val materialName = binding.etMaterialName.text.toString()
        val materialQuantity = binding.etMaterialQuantity.text.toString()
        val dateMaterial = materialDate

        if (materialName.isEmpty()) {
            binding.etMaterialNameLayout.error = getString(R.string.error_field)
            return
        } else if (materialQuantity.isEmpty()) {
            binding.etMaterialQuantityLayout.error = getString(R.string.error_field)
        } else if (dateMaterial.isNullOrEmpty()) {
            Toast.makeText(this, "Tanggal harus dipilih", Toast.LENGTH_SHORT).show()
        } else {
            material.let { material ->
                material?.name = materialName
                material?.quantity = materialQuantity.toInt()
                material?.date = dateMaterial
                Log.e(
                    "data_material",
                    "data: ${material?.name}, ${material?.quantity}, ${material?.date}"
                )
            }
            addMaterialViewModel.insert(material as Material)
            Log.e("data_material", "data: $material")
            Toast.makeText(this, "Data berhasil dibuat", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }




}