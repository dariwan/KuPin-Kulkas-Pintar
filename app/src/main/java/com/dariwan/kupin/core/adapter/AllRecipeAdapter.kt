package com.dariwan.kupin.core.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dariwan.kupin.core.data.remote.response.AllRecipeItem
import com.dariwan.kupin.databinding.RecipeListBinding
import com.dariwan.kupin.view.recipe.detail.DetailRecipeActivity

class AllRecipeAdapter: RecyclerView.Adapter<AllRecipeAdapter.AllRecipeViewHolder>() {
    private val recipe: MutableList<AllRecipeItem> = mutableListOf()

    class AllRecipeViewHolder(val binding: RecipeListBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(recipe: AllRecipeItem){
            binding.tvRecipe.text = recipe.title
            binding.tvRecipeDescription.text = recipe.deskripsi
            Glide.with(itemView.context)
                .load(recipe.image)
                .into(binding.imgFoodRecipe)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): AllRecipeAdapter.AllRecipeViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = RecipeListBinding.inflate(inflater, parent, false)
        return AllRecipeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AllRecipeAdapter.AllRecipeViewHolder, position: Int) {
        val recipes = recipe[position]
        holder.bind(recipes)

        holder.binding.root.setOnClickListener {
            val intent = Intent(holder.itemView.context, DetailRecipeActivity::class.java)
            intent.putExtra(DetailRecipeActivity.NAME_EXTRA, recipes.title)
            intent.putExtra(DetailRecipeActivity.MATERIAL, recipes.bahan)
            intent.putExtra(DetailRecipeActivity.STEP, recipes.step)
            intent.putExtra(DetailRecipeActivity.DESCRIPTION_EXTRA, recipes.deskripsi)
            intent.putExtra(DetailRecipeActivity.IMAGE_URL_EXTRA, recipes.image)

            val optionsCOmpact: ActivityOptionsCompat =
                ActivityOptionsCompat.makeSceneTransitionAnimation(
                    holder.itemView.context as Activity,
                    Pair(holder.binding.foodRecipe, "name"),
                    Pair(holder.binding.tvRecipeDescription, "desc"),
                    Pair(holder.binding.imgFoodRecipe, "img")
                )
            holder.itemView.context.startActivity(intent, optionsCOmpact.toBundle())
        }
    }

    override fun getItemCount(): Int {
        return recipe.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun submitAllList(newAllRecipe: List<AllRecipeItem>){
        recipe.clear()
        recipe.addAll(newAllRecipe)
        notifyDataSetChanged()
    }
}