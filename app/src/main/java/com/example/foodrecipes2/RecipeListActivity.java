package com.example.foodrecipes2;

import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodrecipes2.adapters.OnRecipeListener;
import com.example.foodrecipes2.adapters.RecipeRecyclerAdapter;
import com.example.foodrecipes2.models.Recipe;
import com.example.foodrecipes2.util.VerticalSpacingItemDecorator;
import com.example.foodrecipes2.viewmodels.RecipeListViewModel;

import java.util.List;

public class RecipeListActivity extends BaseActivity implements OnRecipeListener {

	private RecipeListViewModel mRecipeListViewModel;
	private RecyclerView mRecyclerView;
	private RecipeRecyclerAdapter mAdapter;
	private SearchView mSearchView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_recipe_list);

		mRecipeListViewModel = ViewModelProviders.of(this).get(RecipeListViewModel.class);
		mRecyclerView = findViewById(R.id.recipe_list);
		mSearchView = findViewById(R.id.search_view);

		subscribeObservers();
		initRecyclerView();
		initSearchBar();

		if (!mRecipeListViewModel.isIsViewingRecipes()) {
			// display search category
			displaySearchCategories();
		}
	}

	private void subscribeObservers() {
		mRecipeListViewModel.getRecipes().observe(this, new Observer<List<Recipe>>() {
			@Override
			public void onChanged(List<Recipe> recipes) {
				if (recipes != null) {
					if (mRecipeListViewModel.isIsViewingRecipes()) {
						mAdapter.setRecipes(recipes);
						mRecipeListViewModel.setIsPerformingQuery(false);
					}
				}
			}
		});
	}

	private void initRecyclerView() {
		mAdapter = new RecipeRecyclerAdapter(this);
		mRecyclerView.setAdapter(mAdapter);
		mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
		VerticalSpacingItemDecorator itemDecorator = new VerticalSpacingItemDecorator(5);
		mRecyclerView.addItemDecoration(itemDecorator);
	}

	private void initSearchBar() {
		mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
			@Override
			public boolean onQueryTextSubmit(String query) {
				mRecipeListViewModel.searchRecipeApi(query,1);
				mAdapter.displayLoading();
				mSearchView.clearFocus();
				return false;
			}

			@Override
			public boolean onQueryTextChange(String newText) {
				return false;
			}
		});
	}

	@Override
	public void onRecipeClick(int position) {

	}

	@Override
	public void onCategoryClick(String category) {
		mRecipeListViewModel.searchRecipeApi(category,1);
		mAdapter.displayLoading();
		mSearchView.clearFocus();
	}

	private void displaySearchCategories() {
		mRecipeListViewModel.setIsViewingRecipes(false);
		mAdapter.displaySearchCategories();
	}

	@Override
	public void onBackPressed() {
		if (mRecipeListViewModel.onBackPressed()) {
			super.onBackPressed();
		}
		else {
			displaySearchCategories();
		}
	}
}
