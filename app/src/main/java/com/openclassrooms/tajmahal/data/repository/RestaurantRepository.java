package com.openclassrooms.tajmahal.data.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.openclassrooms.tajmahal.data.service.RestaurantApi;
import com.openclassrooms.tajmahal.domain.model.Restaurant;
import com.openclassrooms.tajmahal.domain.model.Review;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;


/**
 * This is the repository class for managing restaurant data. Repositories are responsible
 * for coordinating data operations from data sources such as network APIs, databases, etc.
 *
 * Typically in an Android app built with architecture components, the repository will handle
 * the logic for deciding whether to fetch data from a network source or use data from a local cache.
 *
 *
 * @see Restaurant
 * @see RestaurantApi
 */
@Singleton
public class RestaurantRepository {


    // The API interface instance that will be used for network requests related to restaurant data.
    private final RestaurantApi restaurantApi;

    public final static int CST_NOTE_MAX = 5;



    /**
     * Constructs a new instance of {@link RestaurantRepository} with the given {@link RestaurantApi}.
     *
     * @param restaurantApi The network API interface for fetching restaurant data.
     */
    @Inject
    public RestaurantRepository(RestaurantApi restaurantApi) {
        this.restaurantApi = restaurantApi;
    }

    /**
     * Fetches the restaurant details.
     *
     * This method will make a network call using the provided {@link RestaurantApi} instance
     * to fetch restaurant data. Note that error handling and any transformations on the data
     * would need to be managed.
     *
     *
     * @return LiveData holding the restaurant details.
     */
    public LiveData<Restaurant> getRestaurant() {
        // Est ce une bonne méthode que le repository renvoie directement du LiveData ? pas pratique pour les tests unitaires + j'aurai tendance à mettre le LiveData uniquement dans le ViewModel
        return new MutableLiveData<>(restaurantApi.getRestaurant());
    }

    /** Get all reviews*/
    public MutableLiveData<List<Review>> getReviews() {
        return new MutableLiveData<>(restaurantApi.getReviews());
    }

    /**
     * Calculate the reviews average
     * @return reviews average
     */
    public double getReviewsAverage() {

        // Call the API
        List<Review> reviews = restaurantApi.getReviews();

        double rAverage;

        if (reviews == null || reviews.isEmpty()) {
            // Empty list or null
            rAverage = 0.0;
        }
        else{
            int[] anRates= new int[reviews.size()]; // Extract the rate
            int i = 0;
            for (Review r : reviews) {
                anRates[i] = r.getRate();
                i++;
            }
            rAverage = Arrays.stream(anRates).average().orElse(0.0);
        }

        return rAverage;
    }

    /**
     * Total of reviews
     * @return Total of reviews
     */
    public int getTotalReviews() {
        return restaurantApi.getReviews().size();
    }

    /**
     * Calculate the reviews repartition by rate (0,1...5)
     * @return : reviews repartition by rate (0,1...5) in percentage (value 0 .. 100)
     */
    public int[] getReviewsRepartition() {

        // Call the API
        List<Review> reviews = restaurantApi.getReviews();

        // Index = possible rate (0,1...5)
        int[] anPercentPerNote = new int[CST_NOTE_MAX+1];

        // If there are reviews
        int nTotalReview = reviews.size();
        if (nTotalReview>0){

            // Count the number of 1, the number of 2 ....
            for (Review r : reviews) {
                int nRate = r.getRate();
                if (nRate>0 && nRate<=CST_NOTE_MAX){
                    anPercentPerNote[nRate]++;
                }
                else{
                    // TODO : possible de breaker directement ici en cas d'erreur ?
                    assert false : "Incorrect rate : "+nRate;
                }
            }

            // Transform value in percentage (value 0 .. 100)
            for (int i = 0; i<=CST_NOTE_MAX; i++){
                anPercentPerNote[i] = anPercentPerNote[i] * 100 / nTotalReview ;
            }

        }

        return anPercentPerNote;

    }


    /**
     * Add a review
     * @param oReviewP : review to add
     * @return : if there is a problem => Error code CST_ERROR_REVIEW_XXX, if everything is ok return 0
     */
    public int addReview(@NotNull Review oReviewP) {
        return restaurantApi.addReview(oReviewP);
    }

    /**
     * Test in the user has already give a review
     * @param sUserName :
     * @return : the user review if the user name is find in review user name, else null
     */
    public Review getUserReviewIfExist(String sUserName) {
        return restaurantApi.getUserReviewIfExist(sUserName);
    }

    /** Give the error code => NO RATE */
    public int get_error_review_with_no_rate() {
        return restaurantApi.CST_ERROR_REVIEW_WITH_NO_RATE;
    }

    /** Give the error code => NO COMMENT */
    public int get_error_review_with_no_comment() {
        return restaurantApi.CST_ERROR_REVIEW_WITH_NO_COMMENT;
    }

    /** Give the error code => NO USER */
    public int get_error_review_with_no_user() {
        return restaurantApi.CST_ERROR_REVIEW_WITH_NO_USER;
    }
}
