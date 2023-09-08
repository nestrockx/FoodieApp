package com.wegielek.mvvm_foodie.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wegielek.mvvm_foodie.model.RecipeResponse
import com.wegielek.mvvm_foodie.repository.RecipeRepository
import com.wegielek.mvvm_foodie.util.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class RecipeViewModel (
    val recipeRepository: RecipeRepository
) : ViewModel() {

    val recipe: MutableLiveData<Resource<RecipeResponse>> = MutableLiveData()

    init {
        getRandomRecipe()
    }

    fun getRandomRecipe() = viewModelScope.launch {
        recipe.postValue(Resource.Loading())
        val response = recipeRepository.getRandomRecipe()
        recipe.postValue(handleRandomRecipeResponse(response))
    }

    private fun handleRandomRecipeResponse(response: Response<RecipeResponse>) : Resource<RecipeResponse> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message())
    }
}