package com.wegielek.mvvm_foodie.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.wegielek.mvvm_foodie.R
import com.wegielek.mvvm_foodie.repository.RecipeRepository

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recipeRepository = RecipeRepository()




    }
}