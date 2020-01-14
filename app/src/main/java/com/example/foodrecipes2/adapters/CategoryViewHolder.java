package com.example.foodrecipes2.adapters;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodrecipes2.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class CategoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

	OnRecipeListener listener;
	CircleImageView categoryImage;
	TextView categoryTitle;

	public CategoryViewHolder(@NonNull View itemView, OnRecipeListener listener) {
		super(itemView);

		this.listener = listener;
		categoryTitle = itemView.findViewById(R.id.category_title);
		categoryImage = itemView.findViewById(R.id.category_image);

		itemView.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		listener.onCategoryClick(categoryTitle.getText().toString());
	}
}
