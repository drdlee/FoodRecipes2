package com.example.foodrecipes2.request;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.foodrecipes2.AppExecutors;
import com.example.foodrecipes2.models.Recipe;
import com.example.foodrecipes2.util.Constants;

import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class RecipeApiClient {

	private static RecipeApiClient instance;
	private MutableLiveData<List<Recipe>> mRecipes;

	public static RecipeApiClient getInstance() {
		if (instance == null) {
			instance = new RecipeApiClient();
		}
		return instance;
	}

	private RecipeApiClient() {
	  mRecipes = new MutableLiveData<>();
	}

	public LiveData<List<Recipe>> getRecipes() {
		return mRecipes;
	}

	public void searchRecipeApi() {
		final Future handler = AppExecutors.getInstance().networkIO().submit(new Runnable() {
			@Override
			public void run() {
				// retrieve data from rest api
//				mRecipes.postValue();
			}
		});

		// below code will fun after 3000ms / 3sec to cancel the Future handler created above
		AppExecutors.getInstance().networkIO().schedule(new Runnable() {
			@Override
			public void run() {
				//Let the user know its time out
				handler.cancel(true);
			}
		}, Constants.NETWORK_TIMEOUT, TimeUnit.MILLISECONDS);
	}
}
