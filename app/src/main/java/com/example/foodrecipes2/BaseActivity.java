package com.example.foodrecipes2;

import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public abstract class BaseActivity extends AppCompatActivity {
/*  make this class as abstract class to disable the ability to be instantiated
	so this class can only be extended
 */

	public ProgressBar mProgressBar;

	@Override
	public void setContentView(int layoutResID) {

		ConstraintLayout constraintLayout = (ConstraintLayout) getLayoutInflater().inflate(R.layout.activity_base, null);
		FrameLayout frameLayout = constraintLayout.findViewById(R.id.activity_content);
		mProgressBar = constraintLayout.findViewById(R.id.progress_bar);

		getLayoutInflater().inflate(layoutResID, frameLayout, true);
		// above line are to associate this BaseActivity with the framelayout in resource file
		// so the framelayout will act as an container to the activity that extends this class

		super.setContentView(constraintLayout);
		// remember to set the constraintlayout as the ContentView
	}

	public void showProgressBar(boolean visible) {
		mProgressBar.setVisibility(visible ? View.VISIBLE : View.INVISIBLE);
	}
}
