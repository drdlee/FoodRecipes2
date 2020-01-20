package com.example.foodrecipes2;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.foodrecipes2.models.Recipe;
import com.example.foodrecipes2.viewmodels.RecipeViewModel;

public class RecipeActivity extends BaseActivity {

	private static final String TAG = "RecipeActivity";

	//UI Component
	private AppCompatImageView mRecipeImage;
	private TextView mRecipeTitle, mRecipeRank;
	private LinearLayout mRecipeIngredientsContainer;
	private ScrollView mRScrollView;

	private RecipeViewModel mRecipeViewModel;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_recipe);
		Log.d("RecipeActivity", "onCreate: RecipeActivity Created");

		mRecipeImage = findViewById(R.id.recipe_image);
		mRecipeTitle = findViewById(R.id.recipe_title);
		mRecipeRank = findViewById(R.id.recipe_social_score);
		mRecipeIngredientsContainer = findViewById(R.id.ingredients_container);
		mRScrollView = findViewById(R.id.parent);

		mRecipeViewModel = ViewModelProviders.of(this).get(RecipeViewModel.class);

		showProgressBar(true);
		subscribeObservers();
		getIncomingIntent();
	}

	private void getIncomingIntent(){
		if (getIntent().hasExtra("recipe")){
			Recipe recipe = getIntent().getParcelableExtra("recipe");
			Log.d(TAG, "getIncomingIntent: " + recipe.getRecipe_id());
			mRecipeViewModel.searchRecipeById(recipe.getRecipe_id());
		}
	}

	private void subscribeObservers() {
		mRecipeViewModel.getRecipe().observe(this, new Observer<Recipe>() {
			@Override
			public void onChanged(Recipe recipe) {
				if (recipe != null) {
					Log.d("RecipeActivity", "onChanged: " + recipe.getTitle());
					if (recipe.getRecipe_id().equals(mRecipeViewModel.getmRecipeId())){
						// check if the recipeId we set on mRecipeViewModel.searchRecipeById the same as current received
						// if its the same then run below
						setRecipeProperties(recipe);
					}
				}
			}
		});
	}

	private void setRecipeProperties(Recipe recipe) {
		if (recipe != null) {
			RequestOptions requestOptions = new RequestOptions()
					.placeholder(R.drawable.ic_launcher_background);
			Glide.with(this)
					.setDefaultRequestOptions(requestOptions)
					.load(recipe.getImage_url())
					.into(mRecipeImage);

			mRecipeTitle.setText(recipe.getTitle());
			mRecipeRank.setText(String.valueOf(Math.round(recipe.getSocial_rank())));

			mRecipeIngredientsContainer.removeAllViews();
			for (String ingredient: recipe.getIngredients()){
				TextView textView = new TextView(this);
				textView.setText(ingredient);
				textView.setTextSize(15);
				textView.setLayoutParams(new LinearLayout.LayoutParams(
						ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
				));
				mRecipeIngredientsContainer.addView(textView);
			}
			showProgressBar(false);
			showParent();
		}
	}

	private void showParent() {
		mRScrollView.setVisibility(View.VISIBLE);
	}
}
