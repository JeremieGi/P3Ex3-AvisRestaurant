package com.openclassrooms.tajmahal.ui.restaurant;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.openclassrooms.tajmahal.R;
import com.openclassrooms.tajmahal.data.repository.RestaurantRepository;
import com.openclassrooms.tajmahal.domain.model.Restaurant;
import com.openclassrooms.tajmahal.domain.model.Review;

import javax.inject.Inject;

import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dagger.hilt.android.lifecycle.HiltViewModel;

/**
 * MainViewModel is responsible for preparing and managing the data for the {@link DetailsFragment}.
 * It communicates with the {@link RestaurantRepository} to fetch restaurant details and provides
 * utility methods related to the restaurant UI.
 *
 * This ViewModel is integrated with Hilt for dependency injection.
 */
@HiltViewModel
public class DetailsViewModel extends ViewModel {

    private final RestaurantRepository restaurantRepository;

    // States observed by the DetailsFragment

    MutableLiveData<Double> oAverage = new MutableLiveData<>();
    MutableLiveData<int[]> oPercentPerNote = new MutableLiveData<>();

    public final static int CST_NOTE_MAX = 5;

    /**
     * Constructor that Hilt will use to create an instance of MainViewModel.
     *
     * @param restaurantRepository The repository which will provide restaurant data.
     */
    @Inject
    public DetailsViewModel(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    /**
     * Fetches the details of the Taj Mahal restaurant.
     *
     * @return LiveData object containing the details of the Taj Mahal restaurant.
     */
    public LiveData<Restaurant> getTajMahalRestaurant() {
        return restaurantRepository.getRestaurant();
    }

    public LiveData<List<Review>> getTajMahalReviews() {
        return restaurantRepository.getReviews();
    }

    /**
     * Retrieves the current day of the week in French.
     *
     * @return A string representing the current day of the week in French.
     */
    public String getCurrentDay(Context context) {
        Calendar calendar = Calendar.getInstance();
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        String dayString;

        switch (dayOfWeek) {
            case Calendar.MONDAY:
                dayString = context.getString(R.string.monday);
                break;
            case Calendar.TUESDAY:
                dayString = context.getString(R.string.tuesday);
                break;
            case Calendar.WEDNESDAY:
                dayString = context.getString(R.string.wednesday);
                break;
            case Calendar.THURSDAY:
                dayString = context.getString(R.string.thursday);
                break;
            case Calendar.FRIDAY:
                dayString = context.getString(R.string.friday);
                break;
            case Calendar.SATURDAY:
                dayString = context.getString(R.string.saturday);
                break;
            case Calendar.SUNDAY:
                dayString = context.getString(R.string.sunday);
                break;
            default:
                dayString = "";
        }
        return dayString;
    }

    /**
     * Calculate the reviews average and post the value to the fragment
     * @param reviews : List of reviews
     */
    public void setTajMahalReviewsAverage(List<Review> reviews) {

        double rAverage;

        if (reviews == null || reviews.isEmpty()) {
            // Empty list or null
            rAverage = 0.0;
        }
        else{
            int[] anReview = new int[reviews.size()];
            int i = 0;
            for (Review r : reviews) {
                anReview[i] = r.getRate();
                i++;
            }
            rAverage = Arrays.stream(anReview).average().orElse(0.0);
        }

        oAverage.postValue(rAverage);

    }

    /**
     *  Calculate the reviews repartition by rate (0,1...5)
     * @param : List of reviews
     */
    public void setTajMahalReviewsRepartition(List<Review> reviews) {

        int[] anPercentPerNote = new int[CST_NOTE_MAX+1];

        int nTotalReview = reviews.size();
        if (nTotalReview>0){

            // Use HashMap : Index = RAte (0,1..5) and value = counter review with the associated rate
            Map<Integer, Integer> map = new HashMap<>();
            for (Review r : reviews) {
                int nRate = r.getRate();
                int nNbReview = 0;
                if (map.containsKey(nRate)) {
                    nNbReview = map.get(nRate); // TODO : Pourquoi ce warning : Unboxing of 'map.get(nRate)' may produce 'NullPointerException'
                }
                nNbReview++;
                map.put(nRate,nNbReview);
            }

            // Copy in an array of int
            for (int i = 0; i<=CST_NOTE_MAX; i++){
                if (map.containsKey(i)){
                    int nNbReview = map.get(i); // TODO : Pourquoi ce warning : Unboxing of 'map.get(nRate)' may produce 'NullPointerException'
                    anPercentPerNote[i] = nNbReview *100 / nTotalReview ;
                }
            }


        }

        oPercentPerNote.postValue(anPercentPerNote);

    }
}
