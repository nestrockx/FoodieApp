package com.wegielek.mvvm_foodie.repository

import com.wegielek.mvvm_foodie.api.RetrofitInstance

class RecipeRepository {

    suspend fun getRandomRecipe() =
        RetrofitInstance.api.getRandomRecipe()

    suspend fun searchRecipe(searchQuery : String) =
        RetrofitInstance.api.searchRecipe(searchQuery)
}