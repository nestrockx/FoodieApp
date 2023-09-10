package com.wegielek.mvvm_foodie.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.wegielek.mvvm_foodie.R
import com.wegielek.mvvm_foodie.model.Meal

class RecipeAdapter : RecyclerView.Adapter<RecipeAdapter.MealViewHolder>() {

    inner class MealViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    private val differCallback = object : DiffUtil.ItemCallback<Meal>() {
        override fun areItemsTheSame(oldItem: Meal, newItem: Meal): Boolean {
            return oldItem.strMeal == newItem.strMeal
        }

        override fun areContentsTheSame(oldItem: Meal, newItem: Meal): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    private var onItemClickListener: ((Meal) -> Unit)? = null

    fun setOnItemClickListener(listener: (Meal) -> Unit) {
        onItemClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealViewHolder {
        return MealViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_recipe_preview,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: MealViewHolder, position: Int) {
        val meal = differ.currentList[position]
        holder.itemView.apply {
            Glide.with(this).load(meal.strMealThumb).into(findViewById(R.id.mealThumbnail))
            findViewById<TextView>(R.id.mealNameTv).text = meal.strMeal
            findViewById<TextView>(R.id.mealAreaTv).text = meal.strArea

            setOnClickListener {
                onItemClickListener?.let { it(meal) }
            }

        }
    }



}