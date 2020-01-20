package com.example.foodrecipes2.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.foodrecipes2.BaseActivity;
import com.example.foodrecipes2.models.Recipe;
import com.example.foodrecipes2.repositories.RecipeRepository;

public class RecipeViewModel extends ViewModel {
	private RecipeRepository mRecipeRepository;

	public RecipeViewModel() {
		this.mRecipeRepository = RecipeRepository.getInstance();
	}

	public LiveData<Recipe> getRecipe() {
		return mRecipeRepository.getRecipe();
	}

	public void searchRecipeById(String recipeId) {
		mRecipeRepository.searchRecipeById(recipeId);
	}
}
