package com.example.foodrecipes2.request;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.foodrecipes2.AppExecutors;
import com.example.foodrecipes2.models.Recipe;
import com.example.foodrecipes2.request.responses.RecipeSearchResponse;
import com.example.foodrecipes2.util.Constants;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Response;

public class RecipeApiClient {

	private static final String TAG = "RecipeApiClient";
	private static RecipeApiClient instance;
	private MutableLiveData<List<Recipe>> mRecipes;
	private RetrieveRecipesRunnable mRetrieveRecipeRunnable;

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

	public void searchRecipeApi(String query, int pageNumber) {
		if (mRetrieveRecipeRunnable != null) {
			mRetrieveRecipeRunnable = null;
		}
		mRetrieveRecipeRunnable = new RetrieveRecipesRunnable(query, pageNumber);
		final Future handler = AppExecutors.getInstance().networkIO().submit(mRetrieveRecipeRunnable);

		// below code will fun after 3000ms / 3sec to cancel the Future handler created above
		AppExecutors.getInstance().networkIO().schedule(new Runnable() {
			@Override
			public void run() {
				//Let the user know its time out
				handler.cancel(true);
			}
		}, Constants.NETWORK_TIMEOUT, TimeUnit.MILLISECONDS);
	}

	private class RetrieveRecipesRunnable implements Runnable {

		private String query;
		private int pageNumber;
		boolean cancelRequest;

		public RetrieveRecipesRunnable(String query, int pageNumber) {
			this.query = query;
			this.pageNumber = pageNumber;
			cancelRequest = false;
		}

		@Override
		public void run() {
			try {
				Response response = getRecipes(query, pageNumber).execute();
				if (cancelRequest) {
					return;
				}
				if (response.code() == 200) {
					List<Recipe> list = new ArrayList<>(((RecipeSearchResponse) response.body()).getRecipes());
					if (pageNumber == 1) {
						mRecipes.postValue(list);
					} else {
						List<Recipe> currentRecipes = mRecipes.getValue();
						currentRecipes.addAll(list);
						mRecipes.postValue(currentRecipes);
					}
				} else {
					String error = response.errorBody().string();
					Log.e(TAG, "run: " + error);
					mRecipes.postValue(null);
				}
			} catch (IOException e) {
				e.printStackTrace();
				mRecipes.postValue(null);
			}
		}

		private Call<RecipeSearchResponse> getRecipes(String query, int pageNumber) {
			return ServiceGenerator.getRecipeAPI().searchRecipe(
					Constants.API_KEY,
					query,
					String.valueOf(pageNumber)
			);
		}

		private void cancelRequest() {
			Log.d(TAG, "cancelRequest: cancelling the search request");
			cancelRequest = true;
		}
	}

	public void cancelRequest() {
		if (mRetrieveRecipeRunnable != null) {
			mRetrieveRecipeRunnable.cancelRequest();
		}
	}
}
