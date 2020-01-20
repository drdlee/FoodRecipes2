package com.example.foodrecipes2.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.foodrecipes2.models.Recipe;
import com.example.foodrecipes2.repositories.RecipeRepository;

public class RecipeViewModel extends ViewModel {
	private RecipeRepository mRecipeRepository;
	private String mRecipeId;
	private Boolean mDidRetrieveRecipe;

	public RecipeViewModel() {
		this.mRecipeRepository = RecipeRepository.getInstance();
		mDidRetrieveRecipe = false;
	}

	public LiveData<Recipe> getRecipe() {
		return mRecipeRepository.getRecipe();
	}

	public LiveData<Boolean> isRecipeRequestTimeout() {
		return mRecipeRepository.isRecipeRquestTimeout();
	}

	public void searchRecipeById(String recipeId) {
		mRecipeId = recipeId;
		mRecipeRepository.searchRecipeById(recipeId);
	}

	public String getmRecipeId() {
		return mRecipeId;
	}

	public Boolean getmDidRetrieveRecipe() {
		return mDidRetrieveRecipe;
	}

	public void setmDidRetrieveRecipe(Boolean mDidRetrieveRecipe) {
		this.mDidRetrieveRecipe = mDidRetrieveRecipe;
	}
}
