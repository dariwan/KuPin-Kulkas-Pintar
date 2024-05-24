package com.dariwan.kupin.view.home.report

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dariwan.kupin.R
import com.dariwan.kupin.core.adapter.MaterialAdapter
import com.dariwan.kupin.core.adapter.ReportAdapter
import com.dariwan.kupin.core.utils.ViewModelFactory
import com.dariwan.kupin.databinding.ActivityReportBinding
import com.dariwan.kupin.view.home.RefrigeneratorViewModel
import java.text.SimpleDateFormat
import java.util.Calendar

@RequiresApi(Build.VERSION_CODES.O)
class ReportActivity : AppCompatActivity() {
    private lateinit var binding: ActivityReportBinding
    private val calendar = Calendar.getInstance()
    private lateinit var adapter: ReportAdapter
    private var dateNow: String? = null
    private var startDate: String = ""
    private var endDate: String = ""
    private lateinit var reportViewModel: ReportViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReportBinding.inflate(layoutInflater)
        setContentView(binding.root)

        reportViewModel = obtainViewModel(this as AppCompatActivity)
        setupCalendarStartDate()
        setupCalendarEndDate()
        setupRv()

    }

    private fun setupRv() {
        binding.btnCari.setOnClickListener {
            reportViewModel.getMaterialsByDate(startDate, endDate).observe(this) { material ->
                Log.e("date", "date view model start $startDate, end: $endDate")
                if (material != null) {
                    adapter.setListMaterial(material)
                    binding.tvNoData.visibility = View.GONE
                } else {
                    binding.tvNoData.visibility = View.VISIBLE
                }
            }
        }
        adapter = ReportAdapter()
        binding.rvReport.adapter = adapter
        binding.rvReport.layoutManager = LinearLayoutManager(this)
        binding.rvReport.setHasFixedSize(true)
        Log.e("date", "start $startDate, end: $endDate")
    }

    private fun setupCalendarEndDate() {
        val dataSetListener =
            DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                calendar.set(Calendar.YEAR, year)
                calendar.set(Calendar.MONTH, monthOfYear)
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                viewDateEndDate()
                val dateFormat = "dd - MM - yyyy"
                val simpleDateFormat = SimpleDateFormat(dateFormat)
                endDate = simpleDateFormat.format(calendar.time)
            }

        binding.ivCalendarEndDate.setOnClickListener {
            DatePickerDialog(
                this,
                dataSetListener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH),
            ).show()
        }
    }

    private fun setupCalendarStartDate() {
        val dataSetListener =
            DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                calendar.set(Calendar.YEAR, year)
                calendar.set(Calendar.MONTH, monthOfYear)
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                viewDateStartDate()
                val dateFormat = "dd - MM - yyyy"
                val simpleDateFormat = SimpleDateFormat(dateFormat)
                startDate = simpleDateFormat.format(calendar.time)
            }

        binding.ivCalendarStartDate.setOnClickListener {
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
    private fun viewDateStartDate() {
        val dateFormat = "dd - MM - yyyy"
        val simpleDateFormat = SimpleDateFormat(dateFormat)
        dateNow = simpleDateFormat.format(calendar.timeInMillis)
        Log.e("dateNow", "date : $dateNow")
        binding.tvDateCalendarStartDate.text = simpleDateFormat.format(calendar.timeInMillis)
    }

    @SuppressLint("SimpleDateFormat")
    private fun viewDateEndDate() {
        val dateFormat = "dd - MM - yyyy"
        val simpleDateFormat = SimpleDateFormat(dateFormat)
        dateNow = simpleDateFormat.format(calendar.timeInMillis)
        Log.e("dateNow", "date : $dateNow")
        binding.tvDateCalendarEndDate.text = simpleDateFormat.format(calendar.timeInMillis)
    }

    private fun obtainViewModel(activity: AppCompatActivity): ReportViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[ReportViewModel::class.java]
    }
}