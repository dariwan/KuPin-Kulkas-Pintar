package com.dariwan.kupin.view.profile

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.bumptech.glide.Glide
import com.dariwan.kupin.R
import com.dariwan.kupin.core.utils.Constant.KEY_NAME
import com.dariwan.kupin.core.utils.Constant.KEY_USER_EMAIL
import com.dariwan.kupin.core.utils.SessionManager
import com.dariwan.kupin.databinding.FragmentProfileBinding
import com.dariwan.kupin.view.login.LoginActivity
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore
import com.google.firebase.storage.FirebaseStorage

@RequiresApi(Build.VERSION_CODES.O)
class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    private var db = Firebase.firestore
    private lateinit var sharedPref: SessionManager
    private var email: String? = null
    private var username: String? = null
    private var selectedImageUri: Uri? = null
    private val PICK_IMAGE_REQUEST = 1

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
        setButton()
    }

    private fun setButton() {
        binding.btnLogout.setOnClickListener {
            sharedPref.clearData()
            val intent = Intent(requireContext(), LoginActivity::class.java)
            startActivity(intent)
        }

        binding.ivProfile.setOnClickListener {
            openGaleri()
        }
    }

    private fun openGaleri() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }

    private fun setData() {
        email = sharedPref.getEmail
        username = sharedPref.getUsername

        binding.tvUsername.text = username
        binding.tvEmail.text = email
    }

    private fun getData() {
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        if (userId != null) {
            db.collection("user").document(userId).get()
                .addOnSuccessListener { document ->
                    val username = document.getString("username")
                    val email = document.getString("email")
                    val image = document.getString("photoUrl")

                    binding.tvUsername.text = username
                    binding.tvEmail.text = email
                    if (!image.isNullOrEmpty()) {
                        Glide.with(requireContext())
                            .load(image)
                            .into(binding.ivProfile)
                    }

                    sharedPref.apply {
                        setStringPref(KEY_USER_EMAIL, email!!)
                        setStringPref(KEY_NAME, username!!)
                    }

                }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.data != null) {
            selectedImageUri = data.data
            binding.ivProfile.setImageURI(selectedImageUri)

            updateProfileImage(selectedImageUri)
        }
    }

    private fun updateProfileImage(imageUri: Uri?) {
        val userId = FirebaseAuth.getInstance().currentUser?.uid

        if (userId != null && imageUri != null) {
            val storage = FirebaseStorage.getInstance()
            val storageRef = storage.reference
            val imageRef = storageRef.child("image/${userId}_profile.jpg")

            imageRef.putFile(imageUri)
                .addOnSuccessListener { taskSnaphot ->
                    imageRef.downloadUrl.addOnSuccessListener { uri ->
                        updateImageUrlInFirestore(userId, uri.toString())
                    }
                }
                .addOnFailureListener { exception ->
                    Toast.makeText(requireContext(), "Gagal upload gambar", Toast.LENGTH_SHORT)
                        .show()
                }
        }
    }

    private fun updateImageUrlInFirestore(userId: String, imageUrl: String) {
        val userRef = db.collection("user").document(userId)

        userRef.update("photoUrl", imageUrl)
            .addOnSuccessListener {
                Toast.makeText(requireContext(), "Gambar berhasil disimpan", Toast.LENGTH_SHORT)
                    .show()
            }
            .addOnFailureListener { exception ->
                Toast.makeText(requireContext(), "Gambar gagal disimpan", Toast.LENGTH_SHORT).show()
            }
    }

}