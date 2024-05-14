package com.openclassrooms.tajmahal.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.openclassrooms.tajmahal.R;
import com.openclassrooms.tajmahal.domain.model.Review;

import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder> {


    private List<Review> reviews;



    // Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater adapterLayout = LayoutInflater.from(parent.getContext());
        View view = adapterLayout.inflate(R.layout.item_review, parent, false);
        return new ReviewViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(@NonNull ReviewViewHolder holder, int position) {
        Review itemReview = reviews.get(position);
        holder.tvComment.setText(itemReview.getComment());
    }

    /**
     * Return the size of your dataset (invoked by the layout manager)
     */
    @Override
    public int getItemCount() {
        return reviews.size();
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
        notifyDataSetChanged();
    }

    public static class ReviewViewHolder extends RecyclerView.ViewHolder {

        // TODO : Binding possible ici ?
        TextView tvComment;

        ReviewViewHolder(View itemViewP) {
            super(itemViewP);
            tvComment = itemView.findViewById(R.id.item_review_comment);
        }
    }
}
