package com.openclassrooms.tajmahal.ui.restaurant;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.openclassrooms.tajmahal.data.repository.RestaurantRepository;
import com.openclassrooms.tajmahal.data.repository.UserRepository;
import com.openclassrooms.tajmahal.domain.model.Restaurant;
import com.openclassrooms.tajmahal.domain.model.Review;

import java.util.ArrayList;
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
     * Init all the reviews from the repository
     */
    public MutableLiveData<List<Review>> getReviews() {
        return aListReviews;
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

        MutableLiveData<String> currentUser = new MutableLiveData<String>();
        currentUser.setValue(userRepository.getUserName());
        return currentUser;

    }

    /**
     * Get user avatar in repository and notify the view
     * @return : user avatar
     */
    public LiveData<String> getUserPicture() {

        MutableLiveData<String> imgUser = new MutableLiveData<String>();
        imgUser.setValue(userRepository.getUserPicture());
        return imgUser;

    }

    /**
     * Add review in the repository
     * @param oUserReviewP
     */
    public void addReview(Review oUserReviewP) {

        // Ajout dans le repository (pour que l'avis reste en mémoire de l'appli)
        restaurantRepository.addReview(oUserReviewP);

        // TODO : A la sortie de cet appel aListReviews est bien modifié
        //  mais l'observer non appelé, il faut que je l'appelle explicitement : normal ?
        aListReviews.postValue(aListReviews.getValue());


    }

}