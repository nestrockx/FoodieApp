package com.wegielek.mvvm_foodie.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.wegielek.mvvm_foodie.R
import com.wegielek.mvvm_foodie.databinding.ActivityMainBinding
import com.wegielek.mvvm_foodie.repository.RecipeRepository

class MainActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "MainActivity"
    }
    lateinit var viewModel: RecipeViewModel
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val recipeRepository = RecipeRepository()
        val viewModelProviderFactory = RecipeViewModelProviderFactory(application, recipeRepository)
        viewModel = ViewModelProvider(this, viewModelProviderFactory)[RecipeViewModel::class.java]

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        supportFragmentManager.findFragmentById(R.id.recipeNavHostFragment)
            ?.let {
                binding.bottomNavigationView.setOnItemSelectedListener {item ->
                    NavigationUI.onNavDestinationSelected(item, it.findNavController())
                    return@setOnItemSelectedListener true
                }
            }

    }

}