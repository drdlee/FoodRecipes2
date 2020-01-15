package com.example.foodrecipes2.repositories;

import androidx.lifecycle.LiveData;

import com.example.foodrecipes2.models.Recipe;
import com.example.foodrecipes2.request.RecipeApiClient;

import java.util.List;

public class RecipeRepository {

	private static RecipeRepository instance;
	private RecipeApiClient mRecipeApiClient;
	private String mQuery;
	private int mPageNumber;

	public static RecipeRepository getInstance() {
		if (instance == null) {
			instance = new RecipeRepository();
		}
		return instance;
	}

	private RecipeRepository() {
		mRecipeApiClient = RecipeApiClient.getInstance();
	}

	public LiveData<List<Recipe>> getRecipes() {
		return mRecipeApiClient.getRecipes();
	}

	public void searchRecipeApi(String query, int pageNumber) {
		if (pageNumber == 0) {
			pageNumber = 1;
		}
		mQuery = query;
		mPageNumber = pageNumber;
		mRecipeApiClient.searchRecipeApi(query,pageNumber);
	}

	public void searchNextPage() {
		searchRecipeApi(mQuery, mPageNumber +1);
	}

	public void cancelRequest() {
		mRecipeApiClient.cancelRequest();
	}
}
