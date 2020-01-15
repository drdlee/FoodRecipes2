package com.example.foodrecipes2.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.foodrecipes2.models.Recipe;
import com.example.foodrecipes2.repositories.RecipeRepository;

import java.util.List;

public class RecipeListViewModel extends ViewModel {

	private RecipeRepository mRecipeRepository;
	private boolean mIsViewingRecipes;

	public RecipeListViewModel() {
		mRecipeRepository = RecipeRepository.getInstance();
	}

	public LiveData<List<Recipe>> getRecipes() {
		return mRecipeRepository.getRecipes();
	}

	public void searchRecipeApi(String query, int pageNumber) {
		setIsViewingRecipes(true);
		mRecipeRepository.searchRecipeApi(query, pageNumber);
	}

	public boolean isIsViewingRecipes() {
		return mIsViewingRecipes;
	}

	public void setIsViewingRecipes(boolean mIsViewingRecipes) {
		this.mIsViewingRecipes = mIsViewingRecipes;
	}

	public boolean onBackPressed() {
		if (isIsViewingRecipes()) {
			// if currently viewing recipe, then make it not viewing recipe, which is make it to view category
			mIsViewingRecipes = false;
			return false;
		}
		// return true if not viewing recipe, meaning are viewing category
		return true;
	}
}
