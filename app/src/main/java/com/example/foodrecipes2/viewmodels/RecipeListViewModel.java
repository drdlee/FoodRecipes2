package com.example.foodrecipes2.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.foodrecipes2.models.Recipe;
import com.example.foodrecipes2.repositories.RecipeRepository;

import java.util.List;

public class RecipeListViewModel  extends ViewModel {

	private RecipeRepository mRecipeRepository;
	private boolean mIsViewingRecipes;

	public RecipeListViewModel() {
		mRecipeRepository = RecipeRepository.getInstance();
		mIsViewingRecipes = false;
	}

	public LiveData<List<Recipe>> getRecipes() {
		return mRecipeRepository.getRecipes();
	}

	public void searchRecipeApi(String query, int pageNumber) {
		setIsViewingRecipes(true);
		mRecipeRepository.searchRecipeApi(query,pageNumber);
	}

	public boolean isIsViewingRecipes() {
		return mIsViewingRecipes;
	}

	public void setIsViewingRecipes(boolean mIsViewingRecipes) {
		this.mIsViewingRecipes = mIsViewingRecipes;
	}
}
