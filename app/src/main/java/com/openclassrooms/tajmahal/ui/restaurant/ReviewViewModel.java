package com.openclassrooms.tajmahal.ui.restaurant;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.openclassrooms.tajmahal.data.repository.RestaurantRepository;
import com.openclassrooms.tajmahal.data.repository.UserRepository;
import com.openclassrooms.tajmahal.domain.model.Restaurant;
import com.openclassrooms.tajmahal.domain.model.Review;

import java.util.List;

import javax.inject.Inject;
import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class ReviewViewModel extends ViewModel {

    private final RestaurantRepository restaurantRepository;
    private final UserRepository userRepository;

    public MutableLiveData<List<Review>> aListReviews;


    /**
     * Constructor that Hilt will use to create an instance of ReviewViewModel.
     *
     * @param restaurantRepository The repository which will provide restaurant data.
     */
    @Inject
    public ReviewViewModel(RestaurantRepository restaurantRepository, UserRepository userRepository) {
        this.restaurantRepository = restaurantRepository;
        aListReviews = restaurantRepository.getReviews();
        this.userRepository = userRepository;
    }


    /**
     * Fetches the details of the Taj Mahal restaurant.
     *
     * @return LiveData object containing the details of the Taj Mahal restaurant.
     */
    public LiveData<Restaurant> getTajMahalRestaurant() {
        return restaurantRepository.getRestaurant();
    }

    /**
     * Get user name in repository and notify the view
     * @return : user name
     */
    public LiveData<String> getUserName() {

        MutableLiveData<String> currentUser = new MutableLiveData();
        currentUser.setValue(userRepository.getUserName());
        return currentUser;

    }

    /**
     * Get user avatar in repository and notify the view
     * @return : user avatar
     */
    public LiveData<String> getUserPicture() {

        MutableLiveData<String> imgUser = new MutableLiveData();
        imgUser.setValue(userRepository.getUserPicture());
        return imgUser;

    }

    /**
     * Add review in the repository
     * @param oReviewP : Review to add
     */
    public int addReview(Review oReviewP) {

        // Ajout dans le repository (pour que l'avis reste en mémoire de l'appli)
        int nCodeError = restaurantRepository.addReview(oReviewP);

        // Add OK
        if (nCodeError==0){
            aListReviews.setValue(aListReviews.getValue());
        }

        return nCodeError;

    }

    /**
     * Test in the user has already give a review
     * @param sUserName :
     * @return : the user review if the user name is find in review user name, else null
     */
    public Review getUserReviewIfExist(String sUserName) {
        return restaurantRepository.getUserReviewIfExist(sUserName);
    }

    /** Give the error code => NO RATE */
    public int get_error_review_with_no_rate(){
        return restaurantRepository.get_error_review_with_no_rate();
    }

    /** Give the error code => NO COMMENT */
    public int get_error_review_with_no_comment() {
        return restaurantRepository.get_error_review_with_no_comment();
    }
}