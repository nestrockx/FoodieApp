package com.wegielek.mvvm_foodie.ui

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.wegielek.mvvm_foodie.repository.RecipeRepository

class RecipeViewModelProviderFactory (
    val app: Application,
    val recipeRepository: RecipeRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        return RecipeViewModel(app, recipeRepository) as T
    }

}