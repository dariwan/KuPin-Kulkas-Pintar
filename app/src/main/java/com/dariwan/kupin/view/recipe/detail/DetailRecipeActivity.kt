package com.dariwan.kupin.view.recipe.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.dariwan.kupin.databinding.ActivityDetailRecipeBinding

class DetailRecipeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailRecipeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailRecipeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
    }

    private fun setupView() {
        val name = intent.getStringExtra(NAME_EXTRA)
        val material = intent.getStringExtra(MATERIAL)
        val step = intent.getStringExtra(STEP)
        val desc = intent.getStringExtra(DESCRIPTION_EXTRA)
        val img = intent.getStringExtra(IMAGE_URL_EXTRA)

        val cleanMaterial = material?.replace("--", "\n")
        val cleanStep = step?.replace("--","\n\n")?.replace("?", "")

        binding.foodName.text = name
        binding.tvBahan.text = cleanMaterial
        binding.tvStep.text = cleanStep
        binding.descRecipe.text = desc
        Glide.with(this)
            .load(img)
            .into(binding.imgFoodDetail)
    }


    companion object {
        const val NAME_EXTRA = "name_extra"
        const val DESCRIPTION_EXTRA = "desc_extra"
        const val IMAGE_URL_EXTRA = "img_extra"
        const val MATERIAL = "material_extra"
        const val STEP = "step_extra"
    }
}