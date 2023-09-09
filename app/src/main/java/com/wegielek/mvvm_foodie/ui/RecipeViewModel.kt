package com.wegielek.mvvm_foodie.ui

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.ConnectivityManager.TYPE_ETHERNET
import android.net.ConnectivityManager.TYPE_MOBILE
import android.net.ConnectivityManager.TYPE_WIFI
import android.net.NetworkCapabilities.TRANSPORT_CELLULAR
import android.net.NetworkCapabilities.TRANSPORT_ETHERNET
import android.net.NetworkCapabilities.TRANSPORT_WIFI
import android.os.Build
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.wegielek.mvvm_foodie.FoodieApplication
import com.wegielek.mvvm_foodie.model.RecipeResponse
import com.wegielek.mvvm_foodie.repository.RecipeRepository
import com.wegielek.mvvm_foodie.util.Resource
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException

class RecipeViewModel (
    app: Application,
    private val recipeRepository: RecipeRepository
) : AndroidViewModel(app) {

    val recipe: MutableLiveData<Resource<RecipeResponse>> = MutableLiveData()

    init {
        getRandomRecipe()
    }

    fun getRandomRecipe() = viewModelScope.launch {
        recipe.postValue(Resource.Loading())
        try {
            if(hasInternetConnection()) {
                val response = recipeRepository.getRandomRecipe()
                recipe.postValue(handleRandomRecipeResponse(response))
            } else {
                recipe.postValue(Resource.Error("No internet connection"))
            }
        } catch (t: Throwable) {
            when(t) {
                is IOException -> recipe.postValue(Resource.Error("Network Failure"))
                else -> recipe.postValue(Resource.Error("Conversion Error"))
            }
        }
    }

    private fun handleRandomRecipeResponse(response: Response<RecipeResponse>) : Resource<RecipeResponse> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

    fun searchRecipe(searchQuery: String) = viewModelScope.launch {
        recipe.postValue(Resource.Loading())
        val response = recipeRepository.searchRecipe(searchQuery)
        recipe.postValue(handleSearchRecipeResponse(response))
    }

    private fun handleSearchRecipeResponse(response: Response<RecipeResponse>) : Resource<RecipeResponse> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

    private fun hasInternetConnection():Boolean {
        val connectivityManager = getApplication<FoodieApplication>().getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val activeNetwork = connectivityManager.activeNetwork ?: return false
            val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
            return when {
                capabilities.hasTransport(TRANSPORT_WIFI) -> true
                capabilities.hasTransport(TRANSPORT_CELLULAR) -> true
                capabilities.hasTransport(TRANSPORT_ETHERNET) -> true
                else -> false
            }
        } else {
            connectivityManager.activeNetworkInfo?.run {
                return when(type) {
                    TYPE_WIFI -> true
                    TYPE_MOBILE -> true
                    TYPE_ETHERNET -> true
                    else -> false
                }
            }
        }
        return false
    }

}