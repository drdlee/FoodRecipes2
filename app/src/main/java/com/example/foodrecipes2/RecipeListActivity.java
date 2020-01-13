package com.example.foodrecipes2;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.foodrecipes2.models.Recipe;
import com.example.foodrecipes2.viewmodels.RecipeListViewModel;

import java.util.List;

public class RecipeListActivity extends BaseActivity {

	private static final String TAG = "RecipeListActivity";
	private RecipeListViewModel mRecipeListViewModel;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_recipe_list);

		mRecipeListViewModel = ViewModelProviders.of(this).get(RecipeListViewModel.class);

		subscribeObservers();
		findViewById(R.id.test).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				testRetrofitRequest();
			}
		});
	}

	private void subscribeObservers() {
		mRecipeListViewModel.getRecipes().observe(this, new Observer<List<Recipe>>() {
			@Override
			public void onChanged(List<Recipe> recipes) {
				if (recipes != null) {
					for (Recipe recipe: recipes) {
						Log.d(TAG, "onChanged: " + recipe.getTitle());
					}
				}
			}
		});
	}

	private void searchRecipeApi(String query, int pageNumber) {
		mRecipeListViewModel.searchRecipeApi(query,pageNumber);
	}

	private void testRetrofitRequest() {
		searchRecipeApi("chicken breast", 1);
	}
}
