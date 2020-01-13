package com.example.foodrecipes2;

import android.os.Bundle;
import android.util.Log;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodrecipes2.adapters.OnRecipeListener;
import com.example.foodrecipes2.adapters.RecipeRecyclerAdapter;
import com.example.foodrecipes2.models.Recipe;
import com.example.foodrecipes2.viewmodels.RecipeListViewModel;

import java.util.List;

public class RecipeListActivity extends BaseActivity implements OnRecipeListener {

	private static final String TAG = "RecipeListActivity";
	private RecipeListViewModel mRecipeListViewModel;
	private RecyclerView mRecyclerView;
	private RecipeRecyclerAdapter mAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_recipe_list);

		mRecipeListViewModel = ViewModelProviders.of(this).get(RecipeListViewModel.class);
		mRecyclerView = findViewById(R.id.recipe_list);

		subscribeObservers();
		initRecyclerView();
		testRetrofitRequest();
	}

	private void subscribeObservers() {
		mRecipeListViewModel.getRecipes().observe(this, new Observer<List<Recipe>>() {
			@Override
			public void onChanged(List<Recipe> recipes) {
				if (recipes != null) {
					for (Recipe recipe : recipes) {
						Log.d(TAG, "onChanged: " + recipe.getTitle());
						mAdapter.setRecipes(recipes);
					}
				}
			}
		});
	}

	private void initRecyclerView() {
		mAdapter = new RecipeRecyclerAdapter(this);
		mRecyclerView.setAdapter(mAdapter);
		mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

	}

	private void searchRecipeApi(String query, int pageNumber) {
		mRecipeListViewModel.searchRecipeApi(query, pageNumber);
	}

	private void testRetrofitRequest() {
		searchRecipeApi("chicken breast", 1);
	}

	@Override
	public void onRecipeClick(int position) {

	}

	@Override
	public void onCategoryClick(String category) {

	}
}
