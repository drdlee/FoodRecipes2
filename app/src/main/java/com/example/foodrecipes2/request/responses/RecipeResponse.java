package com.example.foodrecipes2.request.responses;

import com.example.foodrecipes2.models.Recipe;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RecipeResponse {

	@SerializedName("recipe") // why serialize "recipe" because on the GET response we get from API, the field names are contained in "recipe object" from API
	@Expose()  // Gson converter will serialize and deserialize the GET response from web API response
	private Recipe recipe;

	public Recipe getRecipe() {
		return recipe;
	}

	@Override
	public String toString() {
		return "RecipeResponse{" +
				"recipe=" + recipe +
				'}';
	}
}
