package com.wegielek.mvvm_foodie.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.wegielek.mvvm_foodie.R
import com.wegielek.mvvm_foodie.databinding.FragmentRecipeBinding
import com.wegielek.mvvm_foodie.ui.MainActivity
import com.wegielek.mvvm_foodie.ui.RecipeViewModel

class RecipeFragment : Fragment(R.layout.fragment_recipe) {

    companion object {
        private const val TAG = "SearchRecipeFragment"
    }
    private lateinit var viewModel: RecipeViewModel
    private lateinit var binding: FragmentRecipeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRecipeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (activity as MainActivity).viewModel


    }

}