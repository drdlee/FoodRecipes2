package com.example.foodrecipes2.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.foodrecipes2.models.Recipe;
import com.example.foodrecipes2.repositories.RecipeRepository;

import java.util.List;

public class RecipeListViewModel  extends ViewModel {

	private RecipeRepository mRecipeRepository;

	public RecipeListViewModel() {
		mRecipeRepository = RecipeRepository.getInstance();
	}

	public LiveData<List<Recipe>> getRecipes() {
		return mRecipeRepository.getRecipes();
	}
}
