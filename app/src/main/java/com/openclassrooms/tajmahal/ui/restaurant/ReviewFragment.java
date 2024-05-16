package com.openclassrooms.tajmahal.ui.restaurant;


import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.openclassrooms.tajmahal.R;
import com.openclassrooms.tajmahal.databinding.FragmentReviewBinding;
import com.openclassrooms.tajmahal.domain.model.Restaurant;
import com.openclassrooms.tajmahal.domain.model.Review;
import com.openclassrooms.tajmahal.ui.adapter.ReviewAdapter;
import com.squareup.picasso.Picasso;

import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ReviewFragment extends Fragment {

    private FragmentReviewBinding binding;

    private ReviewViewModel mViewModel;

    private ReviewAdapter reviewAdapter;

    private String sUserName;
    private String sURLUserAvatar;

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

        // Recycler View
        binding.fragmentReviewRecyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        //binding.fragmentReviewRecyclerview.setHasFixedSize(true);
        reviewAdapter = new ReviewAdapter();
        binding.fragmentReviewRecyclerview.setAdapter(reviewAdapter);
        //mViewModel.initTajMahalReviews(); // Call API

        // OBSERVERS

        // Observes changes in the restaurant data and updates the UI accordingly.
        mViewModel.getTajMahalRestaurant().observe(requireActivity(), this::updateUIWithRestaurant);

        // Observes changes in the reviews data and updates the UI accordingly.
        //mViewModel.getReviews().observe(requireActivity(), this::updateUIWithReviews);
        mViewModel.aListReviews.observe(requireActivity(), this::updateUIWithReviews);

        mViewModel.getUserName().observe(requireActivity(), this::updateCurrentUser);
        mViewModel.getUserPicture().observe(requireActivity(), this::updateUserPicture);

        // LISTENERS

        // Button back of toolbar
        binding.fragmentReviewToolbar.toolbar.setNavigationOnClickListener(view1 ->
                requireActivity().getSupportFragmentManager().popBackStack()
        );

        // Button 'Validate'
        binding.fragmentReviewButtonValideReview.setOnClickListener(view1 ->
                addReview()
        );


    }

    /**
     * Add the current user review
     */
    private void addReview() {

        // Check required fields
        String sErrorMessage = "";


        // Note
        int nRate = (int) binding.fragmentReviewRbUserNote.getRating();
        if (nRate==0){
            sErrorMessage = getString(R.string.Please_rate_the_restaurant);
        }

        // Comment
        String sComment = binding.fragmentReviewEdtComment.getText().toString();
        if (sComment.isEmpty()){
            sErrorMessage += '\n' + getString(R.string.Please_enter_a_comment);
        }

        // required fields ok
        if (sErrorMessage.isEmpty()){
            Review oUserReview = new Review(this.sUserName,this.sURLUserAvatar,sComment,nRate);
            mViewModel.addReview(oUserReview);

            // Interdit la modification des champs pour ne pas saisir un 2ème avis
            binding.fragmentReviewRbUserNote.setEnabled(false);
            binding.fragmentReviewEdtComment.setEnabled(false);
            binding.fragmentReviewButtonValideReview.setVisibility(View.INVISIBLE);

        }else{
            // Toast message
            Toast toast = Toast.makeText(requireContext(), sErrorMessage, Toast.LENGTH_LONG);
            // TODO : Toast s'affiche toujours en bas
            toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 0);
            toast.show();
        }


    }

    /**
     * Display the user avatar
     * @param sURL : URL of picture to display
     */
    private void updateUserPicture(String sURL) {
        this.sURLUserAvatar = sURL;
        // Picasso : A powerful image downloading and caching library for Android.
        Picasso.get().load(sURL).into(binding.fragmentReviewImgUser);
    }

    /**
     * Display current user name
     * @param sUserP : name to display
     */
    private void updateCurrentUser(String sUserP) {
        this.sUserName = sUserP;
        binding.fragmentReviewTvUserName.setText(sUserP);
    }


    /**
     * Display restaurant name in the Action Bar information
     * @param restaurant : Restaurant of the application
     */
    private void updateUIWithRestaurant(Restaurant restaurant) {
        binding.fragmentReviewToolbar.toolbar.setTitle(restaurant.getName());
    }

    /**
     * Display all reviews
     * @param reviews : reviews
     */
    private void updateUIWithReviews(List<Review> reviews) {
        // Refresh the recyclerView
        reviewAdapter.setReviews(reviews);
    }

    /**
     * Initializes the ViewModel for this activity.
     */
    private void setupViewModel() {
        mViewModel = new ViewModelProvider(this).get(ReviewViewModel.class);
    }

}