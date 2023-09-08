package com.wegielek.mvvm_foodie.api

import com.wegielek.mvvm_foodie.model.RecipeResponse
import retrofit2.Response
import retrofit2.http.GET

interface RecipeAPI {

    @GET("api/json/v1/1/random.php")
    suspend fun getRandomRecipe(): Response<RecipeResponse>


}