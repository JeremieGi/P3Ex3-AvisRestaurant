package com.openclassrooms.tajmahal.ui.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.openclassrooms.tajmahal.databinding.ItemReviewBinding;
import com.openclassrooms.tajmahal.domain.model.Review;
import com.squareup.picasso.Picasso;

import java.util.List;


public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder> {


    private List<Review> reviews;



    // Create new views (invoked by the layout manager) and call ReviewViewHolder
    @NonNull
    @Override
    public ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater adapterLayout = LayoutInflater.from(parent.getContext());
//        View view = adapterLayout.inflate(R.layout.item_review, parent, false);
//        return new ReviewViewHolder(view);
        ItemReviewBinding binding = ItemReviewBinding.inflate(adapterLayout, parent, false);
        return new ReviewViewHolder(binding);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(@NonNull ReviewViewHolder holder, int position) {

        Review itemReview = reviews.get(position);

        // Picasso : A powerful image downloading and caching library for Android.
        Picasso.get().load(itemReview.getPicture()).into(holder.itemBinding.itemReviewImgUser);

        holder.itemBinding.itemReviewTvUserName.setText(itemReview.getUsername());
        holder.itemBinding.itemReviewRbUserNote.setRating(itemReview.getRate());
        holder.itemBinding.itemReviewComment.setText(itemReview.getComment());

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
        // #TODO : Warning : It will always be more efficient to use more specific change events if you can. Rely on 'notifyDataSetChanged' as a last resort.
        notifyDataSetChanged(); // refresh the recycler view
    }

    public static class ReviewViewHolder extends RecyclerView.ViewHolder {

        // Binding in ViewHolder
        public ItemReviewBinding itemBinding;

        ReviewViewHolder(ItemReviewBinding itemBinding) {
            super(itemBinding.getRoot());
            this.itemBinding = itemBinding;
        }
    }
}
