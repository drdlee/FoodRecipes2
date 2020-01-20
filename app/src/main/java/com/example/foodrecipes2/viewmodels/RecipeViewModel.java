package com.example.foodrecipes2.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.foodrecipes2.models.Recipe;
import com.example.foodrecipes2.repositories.RecipeRepository;

public class RecipeViewModel extends ViewModel {
	private RecipeRepository mRecipeRepository;
	private String mRecipeId;

	public RecipeViewModel() {
		this.mRecipeRepository = RecipeRepository.getInstance();
	}

	public LiveData<Recipe> getRecipe() {
		return mRecipeRepository.getRecipe();
	}

	public void searchRecipeById(String recipeId) {
		mRecipeId = recipeId;
		mRecipeRepository.searchRecipeById(recipeId);
	}

	public String getmRecipeId() {
		return mRecipeId;
	}
}
