package com.example.foodrecipes2;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;

import com.example.foodrecipes2.models.Recipe;

public class RecipeActivity extends BaseActivity {

	private AppCompatImageView mRecipeImage;
	private TextView mRecipeTitle, mRecipeSocialRank;
	private LinearLayout mRecipeIngredientsContainer;
	private ScrollView mScrollView;

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
		super.onCreate(savedInstanceState, persistentState);
		setContentView(R.layout.activity_recipe);

		mRecipeImage = findViewById(R.id.recipe_image);
		mRecipeTitle = findViewById(R.id.recipe_title);
		mRecipeSocialRank = findViewById(R.id.recipe_social_score);
		mRecipeIngredientsContainer = findViewById(R.id.ingredients_container);
		mScrollView = findViewById(R.id.parent);

		getIncomingIntent();
	}

	private void getIncomingIntent() {
		if (getIntent().hasExtra("recipe")) {
			Recipe recipe = getIntent().getParcelableExtra("recipe");
			Log.d("RecipeActivity", "getIncomingIntent: " + recipe.getTitle());
		}
	}
}
