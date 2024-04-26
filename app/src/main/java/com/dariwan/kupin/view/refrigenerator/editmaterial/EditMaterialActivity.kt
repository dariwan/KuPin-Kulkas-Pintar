package com.dariwan.kupin.view.refrigenerator.editmaterial

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
import com.dariwan.kupin.core.data.local.database.Material
import com.dariwan.kupin.core.utils.ViewModelFactory
import com.dariwan.kupin.databinding.ActivityEditMaterialBinding
import com.dariwan.kupin.view.main.MainActivity
import com.dariwan.kupin.view.refrigenerator.detail.DetailMaterialActivity
import java.text.SimpleDateFormat
import java.util.Calendar

@RequiresApi(Build.VERSION_CODES.O)

class EditMaterialActivity : AppCompatActivity() {
    private lateinit var binding : ActivityEditMaterialBinding
    private lateinit var editMaterialViewModel: EditMaterialViewModel
    private val calendar = Calendar.getInstance()
    private var materialDate: String? = null
    private var material: Material? = null

    private var id: Int? = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditMaterialBinding.inflate(layoutInflater)
        setContentView(binding.root)

        editMaterialViewModel = obtainViewModel(this@EditMaterialActivity)
        material = Material()

        setupButton()
        setupCalendar()
    }

    private fun setupButton() {
        binding.btnSimpan.setOnClickListener {
            updateData()
        }

        binding.ivBack.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun updateData() {
        id = intent.getIntExtra(DetailMaterialActivity.ID_MATERIAL, id!!)

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
            val quantity = materialQuantity.toInt()
            Log.e("update", "${id}, ${materialName}, ${quantity}, ${dateMaterial}")
            editMaterialViewModel.update(id!!, materialName, quantity, dateMaterial)
            Toast.makeText(this, "Data berhasil diubah", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
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

    private fun obtainViewModel(activity: AppCompatActivity): EditMaterialViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(EditMaterialViewModel::class.java)
    }

    companion object {
        const val NAME_MATERIAL = "name_material"
        const val QUANTITY_MATERIAL = "quantity_material"
        const val DATE_MATERIAL = "date_material"
        const val ID_MATERIAL = "id_material"
    }
}