package com.dariwan.kupin.view.home.detail

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Window
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import com.dariwan.kupin.R
import com.dariwan.kupin.core.data.local.database.kitchencabinet.KitchenCabinet
import com.dariwan.kupin.core.data.local.database.kulkasku.Material
import com.dariwan.kupin.core.utils.ViewModelFactory
import com.dariwan.kupin.databinding.ActivityDetailMaterialBinding
import com.dariwan.kupin.databinding.ActivityDetailStorageBinding
import com.dariwan.kupin.view.home.editmaterial.EditMaterialActivity
import com.dariwan.kupin.view.home.editmaterial.EditStorageActivity
import com.dariwan.kupin.view.main.MainActivity
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

@RequiresApi(Build.VERSION_CODES.O)
class DetailStorageActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailStorageBinding
    private lateinit var detailViewModel: DetailViewModel
    private var storage: KitchenCabinet? = null
    private var materialName: String? = null
    private var quantity: Int? = 0
    private var date: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailStorageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        detailViewModel = obtainViewModel(this@DetailStorageActivity)
        storage = KitchenCabinet()

        setupButton()
        getData()
    }

    private fun getData() {
        materialName = intent.getStringExtra(DetailMaterialActivity.NAME_MATERIAL)
        quantity = intent.getIntExtra(DetailMaterialActivity.QUANTITY_MATERIAL, 0)
        date = intent.getStringExtra(DetailMaterialActivity.DATE_MATERIAL)
        val satuan = intent.getStringExtra(DetailMaterialActivity.SATUAN_MATERIAL)
        val category = intent.getStringExtra(DetailMaterialActivity.CATEGORY_MATERIAL)
        val location_storage = intent.getStringExtra(DetailMaterialActivity.LOCATION_STORAGE)

        val formatter = DateTimeFormatter.ofPattern("dd - MM - yyyy")
        val materialDate = LocalDate.parse(date, formatter)
        val dateNow = LocalDate.now()
        val dayDifference = ChronoUnit.DAYS.between(dateNow, materialDate)

        binding.tvMaterialName.text = materialName
        binding.tvQuantity.text = "$quantity $satuan"
        binding.tvDate.text = date
        binding.tvCategory.text = category
        Log.e("day_difference", "$dayDifference")
        binding.tvLimitComsumtion.text = "$dayDifference Hari"
        binding.tvLocationStorage.text = location_storage
    }

    private fun setupButton() {
        binding.btnDelete.setOnClickListener {
            val deleteMessage = getString(R.string.delete_message)
            showDialogDelete(deleteMessage)
        }

        binding.btnEdit.setOnClickListener {
            sendData()
        }
    }

    private fun sendData() {
        val id = intent.getIntExtra(ID_MATERIAL, 0)
        val intent = Intent(this, EditStorageActivity::class.java)
        intent.putExtra(EditStorageActivity.ID_MATERIAL, id)
        Log.e("send_data", "$id")
        startActivity(intent)
    }

    private fun deleteData(id: Int?) {
        if (id != null) {
            detailViewModel.delete(id)
        }
        Toast.makeText(this, "Data berhasil dihapus", Toast.LENGTH_SHORT).show()
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    private fun showDialogDelete(deleteMessage: String) {

        val id = intent.getIntExtra(DetailMaterialActivity.ID_MATERIAL, 0)

        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.layout_custom_dialog)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val tvMessage: TextView = dialog.findViewById(R.id.tv_message_dialog)
        val btnYes: Button = dialog.findViewById(R.id.btn_yes)
        val btnNo: Button = dialog.findViewById(R.id.btn_no)

        tvMessage.text = deleteMessage
        btnYes.setOnClickListener {
            deleteData(id)
        }

        btnNo.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()

    }

    private fun obtainViewModel(activity: AppCompatActivity): DetailViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(DetailViewModel::class.java)
    }


    companion object {
        const val NAME_MATERIAL = "name_material"
        const val QUANTITY_MATERIAL = "quantity_material"
        const val DATE_MATERIAL = "date_material"
        const val SATUAN_MATERIAL = "satuan_material"
        const val CATEGORY_MATERIAL = "category_material"
        const val ID_MATERIAL = "id_material"
        const val LOCATION_STORAGE = "location_storage"
    }
}