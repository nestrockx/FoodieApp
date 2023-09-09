package com.wegielek.mvvm_foodie.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.wegielek.mvvm_foodie.databinding.ActivityMainBinding
import com.wegielek.mvvm_foodie.repository.RecipeRepository
import com.wegielek.mvvm_foodie.util.Resource

class MainActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "MainActivity"
    }
    private lateinit var viewModel: RecipeViewModel
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val recipeRepository = RecipeRepository()
        val viewModelProviderFactory = RecipeViewModelProviderFactory(recipeRepository)
        viewModel = ViewModelProvider(this, viewModelProviderFactory)[RecipeViewModel::class.java]

        viewModel.recipe.observe(this) { response ->
            when (response) {
                is Resource.Success -> {
                    response.data?.let { recipeResponse ->
                        val meal = recipeResponse.meals[0]
                        Glide.with(this).load(meal.strMealThumb).into(binding.foodPic)
                        binding.mealName.text = meal.strMeal
                        val ingredientsBuilder = StringBuilder()
                        if (!meal.strIngredient1.isNullOrEmpty()) {
                            ingredientsBuilder.append(meal.strMeasure1).append("  ")
                                .append(meal.strIngredient1).append("\n")
                        }
                        if (!meal.strIngredient2.isNullOrEmpty()) {
                            ingredientsBuilder.append(meal.strMeasure2).append("  ")
                                .append(meal.strIngredient2).append("\n")
                        }
                        if (!meal.strIngredient3.isNullOrEmpty()) {
                            ingredientsBuilder.append(meal.strMeasure3).append("  ")
                                .append(meal.strIngredient3).append("\n")
                        }
                        if (!meal.strIngredient4.isNullOrEmpty()) {
                            ingredientsBuilder.append(meal.strMeasure4).append("  ")
                                .append(meal.strIngredient4).append("\n")
                        }
                        if (!meal.strIngredient5.isNullOrEmpty()) {
                            ingredientsBuilder.append(meal.strMeasure5).append("  ")
                                .append(meal.strIngredient5).append("\n")
                        }
                        if (!meal.strIngredient6.isNullOrEmpty()) {
                            ingredientsBuilder.append(meal.strMeasure6).append("  ")
                                .append(meal.strIngredient6).append("\n")
                        }
                        if (!meal.strIngredient7.isNullOrEmpty()) {
                            ingredientsBuilder.append(meal.strMeasure7).append("  ")
                                .append(meal.strIngredient7).append("\n")
                        }
                        if (!meal.strIngredient8.isNullOrEmpty()) {
                            ingredientsBuilder.append(meal.strMeasure8).append("  ")
                                .append(meal.strIngredient8).append("\n")
                        }
                        if (!meal.strIngredient9.isNullOrEmpty()) {
                            ingredientsBuilder.append(meal.strMeasure9).append("  ")
                                .append(meal.strIngredient9).append("\n")
                        }
                        if (!meal.strIngredient10.isNullOrEmpty()) {
                            ingredientsBuilder.append(meal.strMeasure10).append("  ")
                                .append(meal.strIngredient10).append("\n")
                        }
                        if (!meal.strIngredient11.isNullOrEmpty()) {
                            ingredientsBuilder.append(meal.strMeasure11).append("  ")
                                .append(meal.strIngredient11).append("\n")
                        }
                        if (!meal.strIngredient12.isNullOrEmpty()) {
                            ingredientsBuilder.append(meal.strMeasure12).append("  ")
                                .append(meal.strIngredient12).append("\n")
                        }
                        if (!meal.strIngredient13.isNullOrEmpty()) {
                            ingredientsBuilder.append(meal.strMeasure13).append("  ")
                                .append(meal.strIngredient13).append("\n")
                        }
                        if (!meal.strIngredient14.isNullOrEmpty()) {
                            ingredientsBuilder.append(meal.strMeasure14).append("  ")
                                .append(meal.strIngredient14).append("\n")
                        }
                        if (!meal.strIngredient15.isNullOrEmpty()) {
                            ingredientsBuilder.append(meal.strMeasure15).append("  ")
                                .append(meal.strIngredient15).append("\n")
                        }
                        if (!meal.strIngredient16.isNullOrEmpty()) {
                            ingredientsBuilder.append(meal.strMeasure16).append("  ")
                                .append(meal.strIngredient16).append("\n")
                        }
                        if (!meal.strIngredient17.isNullOrEmpty()) {
                            ingredientsBuilder.append(meal.strMeasure17).append("  ")
                                .append(meal.strIngredient17).append("\n")
                        }
                        if (!meal.strIngredient18.isNullOrEmpty()) {
                            ingredientsBuilder.append(meal.strMeasure18).append("  ")
                                .append(meal.strIngredient18).append("\n")
                        }
                        if (!meal.strIngredient19.isNullOrEmpty()) {
                            ingredientsBuilder.append(meal.strMeasure19).append("  ")
                                .append(meal.strIngredient19).append("\n")
                        }
                        if (!meal.strIngredient20.isNullOrEmpty()) {
                            ingredientsBuilder.append(meal.strMeasure20).append("  ")
                                .append(meal.strIngredient20).append("\n")
                        }
                        binding.mealIngredients.text = ingredientsBuilder.toString()
                        binding.instructions.text = meal.strInstructions
                    }
                }

                is Resource.Error -> {
                    response.message?.let { message ->
                        Log.e(TAG, "An error occured: $message",)
                    }
                }

                is Resource.Loading -> {

                }
            }
        }

        binding.random.setOnClickListener {
            viewModel.getRandomRecipe()
        }


    }
}