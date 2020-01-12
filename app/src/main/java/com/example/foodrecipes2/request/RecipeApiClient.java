package com.example.foodrecipes2.request;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.foodrecipes2.models.Recipe;

import java.util.List;

public class RecipeApiClient {

	private static RecipeApiClient instance;
	private MutableLiveData<List<Recipe>> mRecipes;

	public static RecipeApiClient getInstance() {
		if (instance == null) {
			instance = new RecipeApiClient();
		}
		return instance;
	}

	private RecipeApiClient() {
	  mRecipes = new MutableLiveData<>();
	}

	public LiveData<List<Recipe>> getRecipes() {
		return mRecipes;
	}
}
