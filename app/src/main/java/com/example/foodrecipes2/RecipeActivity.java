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
		mRecipeImage = findViewById(R.id.recipe_image);
		mRecipeTitle = findViewById(R.id.recipe_title);
		mRecipeRank = findViewById(R.id.recipe_social_score);
		mRecipeIngredientsContainer = findViewById(R.id.ingredients_container);
		mRScrollView = findViewById(R.id.parent);

		mRecipeViewModel = ViewModelProviders.of(this).get(RecipeViewModel.class);

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
					for (String ing: recipe.getIngredients()){
						Log.d("RecipeActivity", "onChanged: " + ing);
					}
				}
			}
		});
	}
}
