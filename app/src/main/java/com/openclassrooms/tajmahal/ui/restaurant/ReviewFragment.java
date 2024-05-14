package com.openclassrooms.tajmahal.ui.restaurant;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.openclassrooms.tajmahal.R;
import com.openclassrooms.tajmahal.databinding.FragmentDetailsBinding;
import com.openclassrooms.tajmahal.databinding.FragmentReviewBinding;
import com.openclassrooms.tajmahal.domain.model.Review;

import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ReviewFragment extends Fragment {

    private FragmentReviewBinding binding;

    private ReviewViewModel mViewModel;

    public static ReviewFragment newInstance() {
        return new ReviewFragment();
    }

    /**
     * This method is called when the fragment is first created.
     * It's used to perform one-time initialization.
     *
     * @param savedInstanceState A bundle containing previously saved instance state.
     * If the fragment is being re-created from a previous saved state, this is the state.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * Creates and returns the view hierarchy associated with the fragment.
     *
     * @param inflater The LayoutInflater object that can be used to inflate any views in the fragment.
     * @param container If non-null, this is the parent view that the fragment's UI should be attached to.
     * The fragment should not add the view itself but return it.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     * from a previous saved state as given here.
     * @return Returns the View for the fragment's UI, or null.
     */
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = FragmentReviewBinding.inflate(inflater, container, false); // Binds the layout using view binding.
        return binding.getRoot(); // Returns the root view.

    }

    /**
     * This method is called immediately after `onCreateView()`.
     * Use this method to perform final initialization once the fragment views have been inflated.
     *
     * @param view The View returned by `onCreateView()`.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     * from a previous saved state as given here.
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        setupViewModel(); // Prepares the ViewModel for the fragment.

        // OBSERVERS

        // Observes changes in the restaurant data and updates the UI accordingly.
        mViewModel.getTajMahalReviews().observe(requireActivity(), this::updateUIWithReviews);


    }

    /**
     * Display all reviews
     * @param reviews
     */
    private void updateUIWithReviews(List<Review> reviews) {

    }

    /**
     * Initializes the ViewModel for this activity.
     */
    private void setupViewModel() {
        mViewModel = new ViewModelProvider(this).get(ReviewViewModel.class);
    }

}