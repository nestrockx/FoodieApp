package com.wegielek.mvvm_foodie.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.wegielek.mvvm_foodie.R
import com.wegielek.mvvm_foodie.adapters.RecipeAdapter
import com.wegielek.mvvm_foodie.databinding.FragmentSearchRecipeBinding
import com.wegielek.mvvm_foodie.ui.MainActivity
import com.wegielek.mvvm_foodie.ui.RecipeViewModel
import com.wegielek.mvvm_foodie.util.Constants.Companion.SEARCH_RECIPE_DELAY
import com.wegielek.mvvm_foodie.util.Resource
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SearchRecipeFragment : Fragment(R.layout.fragment_search_recipe) {

    companion object {
        private const val TAG = "SearchRecipeFragment"
    }
    private lateinit var viewModel: RecipeViewModel
    private lateinit var binding: FragmentSearchRecipeBinding
    private lateinit var recipeAdapter: RecipeAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchRecipeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (activity as MainActivity).viewModel
        setupRecyclerView()

        var job: Job? = null
        binding.searchBox.addTextChangedListener { editable ->
            job?.cancel()
            job = MainScope().launch {
                delay(SEARCH_RECIPE_DELAY)
                editable?.let {
                    if (!editable.toString().isNullOrEmpty()) {
                        viewModel.searchRecipe(editable.toString())
                    }
                }
            }
        }

        viewModel.recipe.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Success -> {
                    response.data?.let { recipeResponse ->
                        recipeAdapter.differ.submitList(recipeResponse.meals)
                    }
                }

                is Resource.Error -> {
                    response.message?.let { message ->
                        Log.e(TAG, "An error occured: $message")
                    }
                }

                is Resource.Loading -> {

                }
            }
        }

    }

    private fun setupRecyclerView() {
        recipeAdapter = RecipeAdapter()
        binding.recipesRv.apply {
            adapter = recipeAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }
}