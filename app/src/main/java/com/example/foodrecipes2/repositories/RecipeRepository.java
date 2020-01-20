package com.example.foodrecipes2.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.example.foodrecipes2.models.Recipe;
import com.example.foodrecipes2.request.RecipeApiClient;

import java.util.List;

public class RecipeRepository {

	private static RecipeRepository instance;
	private RecipeApiClient mRecipeApiClient;
	private String mQuery;
	private int mPageNumber;
	private MutableLiveData<Boolean> mIsQueryExhausted = new MutableLiveData<>();
	private MediatorLiveData<List<Recipe>> mRecipes = new MediatorLiveData<>();

	public static RecipeRepository getInstance() {
		if (instance == null) {
			instance = new RecipeRepository();
		}
		return instance;
	}

	private RecipeRepository() {
		mRecipeApiClient = RecipeApiClient.getInstance();
		initMediators();
	}

	private void initMediators() {
		LiveData<List<Recipe>> recipeListApiSource = mRecipeApiClient.getRecipes();
		mRecipes.addSource(recipeListApiSource, new Observer<List<Recipe>>() {
			@Override
			public void onChanged(List<Recipe> recipes) {
				if (recipes != null) {
					mRecipes.setValue(recipes);
					doneQuery(recipes);
				} else {
					// search database cache
				}
			}
		});
	}

	public void doneQuery(List<Recipe> list) {
		if (list != null) {
			if (list.size() % 30 != 0) {
				// why 30 because by the API 1 page = 30 item, so if list.size modulus(%) 30 != 0 then no more result = query is exhausted
				// and if list.size & 30 == 0 meaning there are still result and will run searchNextPage
				mIsQueryExhausted.setValue(true);
			}
		} else {
			mIsQueryExhausted.setValue(true);
		}
	}

	public LiveData<Boolean> isQueryExhausted() {
		return mIsQueryExhausted;
	}

	public LiveData<List<Recipe>> getRecipes() {
		return mRecipes;
	}

	public LiveData<Recipe> getRecipe() {
		return mRecipeApiClient.getRecipe();
	}

	public LiveData<Boolean> isRecipeRquestTimeout() {
		return mRecipeApiClient.isRecipeRequestTimeout();
	}

	public void searchRecipeApi(String query, int pageNumber) {
		if (pageNumber == 0) {
			pageNumber = 1;
		}
		mQuery = query;
		mPageNumber = pageNumber;
		mRecipeApiClient.searchRecipeApi(query, pageNumber);
		mIsQueryExhausted.setValue(false);
	}

	public void searchNextPage() {
		searchRecipeApi(mQuery, mPageNumber + 1);
	}

	public void searchRecipeById(String recipeId) {
		mRecipeApiClient.searchRecipeById(recipeId);
	}

	public void cancelRequest() {
		mRecipeApiClient.cancelRequest();
	}
}
