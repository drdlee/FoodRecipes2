package com.example.foodrecipes2.request;

import com.example.foodrecipes2.request.responses.RecipeResponse;
import com.example.foodrecipes2.request.responses.RecipeSearchResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RecipeAPI {

	// SEARCH
	@GET("api/search")
	Call<RecipeSearchResponse> searchRecipe(
		@Query("key") String key,
		@Query("q") String query,
		@Query("page") String page
	);

	// GET RECIPE
	@GET("api/get")
	Call<RecipeResponse> getRecipe(
		@Query("key") String key,
		@Query("rId") String recipe_id
	);
}
