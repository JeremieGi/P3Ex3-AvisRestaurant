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
        this.userRepository = userRepository;
    }

    /**
     * List all the reviews
     *
     * @return LiveData object containing the reviews.
     */
    public void initTajMahalReviews() {
        // Transform LiveData in MutableLiveData
        LiveData<List<Review>> livedata = restaurantRepository.getReviews();
        aListReviews = new MutableLiveData<>(livedata.getValue()) ;
    }

    public LiveData<List<Review>> getReviews(){
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

    public LiveData<String> getUserName() {

        MutableLiveData currentUser = new MutableLiveData<>();
        currentUser.setValue(userRepository.getUserName());
        return currentUser;

    }

    public LiveData<String> getUserPicture() {

        MutableLiveData imgUser = new MutableLiveData<>();
        imgUser.setValue(userRepository.getUserPicture());
        return imgUser;

    }

    public void addReview(Review oUserReviewP) {


        List<Review> reviewList = aListReviews.getValue();
        List<Review> reviewListMutable;
        if (reviewList == null) {
            reviewListMutable = new ArrayList<>();
        }
        else{
            reviewListMutable = new ArrayList<>(reviewList); // Pour avoir une copie modifiable sinon le add plante
        }


        //reviewListMutable.add(oUserReviewP); // Par défaut, ajoute à la fin

        // Ajout au début
        reviewListMutable.add(0,oUserReviewP);

        aListReviews.postValue(reviewListMutable);


    }
}