package com.dariwan.kupin.view.refrigenerator

import android.Manifest
import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dariwan.kupin.core.adapter.MaterialAdapter
import com.dariwan.kupin.core.data.local.database.MaterialRoomDatabase
import com.dariwan.kupin.core.utils.SessionManager
import com.dariwan.kupin.core.utils.ViewModelFactory
import com.dariwan.kupin.databinding.FragmentRefrigeneratorBinding

class RefrigeneratorFragment : Fragment() {
   private lateinit var binding: FragmentRefrigeneratorBinding
   private lateinit var adapter: MaterialAdapter
   private lateinit var refrigeneratorViewModel: RefrigeneratorViewModel
   private lateinit var sharedPref: SessionManager
   private var username: String? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentRefrigeneratorBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        refrigeneratorViewModel = obtainViewModel(requireActivity() as AppCompatActivity)
        sharedPref = SessionManager(requireContext())

        setupRv()
        setData()
        //setupPermission()
        checkData()
    }



    private fun checkData() {

        val materialList = MaterialRoomDatabase.getDatabase(requireContext()).materialDao()

        materialList.getAllMaterials().observeForever{materials ->
            materials.forEach {material ->
                Log.e("cek_data", "$material")
            }
        }

    }


    @SuppressLint("SetTextI18n")
    private fun setData() {
        username = sharedPref.getUsername

        binding.tvUsername.text = "Hi $username"
    }

    private fun setupRv() {
        refrigeneratorViewModel.getAllMaterial().observe(requireActivity()){materialList ->
            if (materialList != null){
                adapter.setListMaterial(materialList)
            } else{
                binding.tvNoData.visibility = View.VISIBLE
            }
        }

        adapter = MaterialAdapter(refrigeneratorViewModel)
        binding.rvMaterial.layoutManager = LinearLayoutManager(requireContext())
        binding.rvMaterial.setHasFixedSize(true)
        binding.rvMaterial.adapter = adapter
    }

    private fun obtainViewModel(activity: AppCompatActivity): RefrigeneratorViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(RefrigeneratorViewModel::class.java)
    }

}