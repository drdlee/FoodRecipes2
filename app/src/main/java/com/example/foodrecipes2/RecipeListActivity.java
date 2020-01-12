package com.example.foodrecipes2;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.foodrecipes2.models.Recipe;
import com.example.foodrecipes2.request.RecipeAPI;
import com.example.foodrecipes2.request.ServiceGenerator;
import com.example.foodrecipes2.request.responses.RecipeResponse;
import com.example.foodrecipes2.request.responses.RecipeSearchResponse;
import com.example.foodrecipes2.util.Constants;
import com.example.foodrecipes2.viewmodels.RecipeListViewModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecipeListActivity extends BaseActivity {

	private static final String TAG = "RecipeListActivity";
	private RecipeListViewModel mRecipeListViewModel;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_recipe_list);

		mRecipeListViewModel = ViewModelProviders.of(this).get(RecipeListViewModel.class);

		subscribeObservers();
	}

	private void subscribeObservers() {
		mRecipeListViewModel.getRecipes().observe(this, new Observer<List<Recipe>>() {
			@Override
			public void onChanged(List<Recipe> recipes) {

			}
		});
	}

	private void testRetrofitRequest() {
		RecipeAPI recipeAPI = ServiceGenerator.getRecipeAPI();

//		Call<RecipeSearchResponse> responseCall = recipeAPI
//				.searchRecipe(
//						Constants.API_KEY,
//						"chicken breast",
//						"1"
//						);
//
//		responseCall.enqueue(new Callback<RecipeSearchResponse>() {
//			@Override
//			public void onResponse(Call<RecipeSearchResponse> call, Response<RecipeSearchResponse> response) {
//				Log.d(TAG, "onResponse: server response" + response.toString());
//				if (response.code() == 200) {
//					Log.d(TAG, "onResponse: " + response.body().toString());
//					List<Recipe> recipes = new ArrayList<>(response.body().getRecipes());
//					for (Recipe recipe: recipes) {
//						Log.d(TAG, "onResponse: " + recipe.getTitle());
//					}
//				}
//				else {
//					Log.d(TAG, "onResponse: " + response.errorBody().toString());
//				}
//			}
//
//			@Override
//			public void onFailure(Call<RecipeSearchResponse> call, Throwable t) {
//
//			}
//		});

		Call<RecipeResponse> responseCall = recipeAPI
				.getRecipe(
						Constants.API_KEY,
						"30373"
				);

		responseCall.enqueue(new Callback<RecipeResponse>() {
			@Override
			public void onResponse(Call<RecipeResponse> call, Response<RecipeResponse> response) {
				Log.d(TAG, "onResponse: server response" + response.toString());
				if (response.code() == 200) {
					Log.d(TAG, "onResponse: " + response.body().toString());
					Recipe recipe = response.body().getRecipe();
					Log.d(TAG, "onResponse: " + recipe.getTitle());
				}
				else {
					Log.d(TAG, "onResponse: " + response.errorBody().toString());
				}
			}

			@Override
			public void onFailure(Call<RecipeResponse> call, Throwable t) {

			}
		});
	}
}
