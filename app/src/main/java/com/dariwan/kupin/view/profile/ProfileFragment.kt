package com.dariwan.kupin.view.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dariwan.kupin.R
import com.dariwan.kupin.core.utils.Constant.KEY_NAME
import com.dariwan.kupin.core.utils.Constant.KEY_USER_EMAIL
import com.dariwan.kupin.core.utils.SessionManager
import com.dariwan.kupin.databinding.FragmentProfileBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore

class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    private var db = Firebase.firestore
    private lateinit var sharedPref: SessionManager
    private var email: String? = null
    private var username: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentProfileBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedPref = SessionManager(requireContext())

        getData()
        setData()
    }

    private fun setData() {
        email = sharedPref.getEmail
        username = sharedPref.getUsername

        binding.tvUsername.text = username
        binding.tvEmail.text = email
    }

    private fun getData() {
        val userId = FirebaseAuth.getInstance().currentUser?.uid


        if (userId != null){
            db.collection("user").document(userId).get()
                .addOnSuccessListener { document ->
                    val username = document.getString("username")
                    val email = document.getString("email")
                    binding.tvUsername.text = username
                    binding.tvEmail.text = email

                    sharedPref.apply {
                        setStringPref(KEY_USER_EMAIL, email!!)
                        setStringPref(KEY_NAME, username!!)
                    }

                }
        }
    }
}