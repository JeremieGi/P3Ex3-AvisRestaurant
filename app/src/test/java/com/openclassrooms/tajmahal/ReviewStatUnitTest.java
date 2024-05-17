package com.openclassrooms.tajmahal;


import static org.junit.Assert.assertEquals;

import com.openclassrooms.tajmahal.data.repository.RestaurantRepository;
import com.openclassrooms.tajmahal.data.service.RestaurantFakeApi;
import com.openclassrooms.tajmahal.domain.model.Review;

import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import java.util.ArrayList;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class ReviewStatUnitTest {

    // This mock simulate test datas
    @Mock
    RestaurantFakeApi mokeFakeApi;

    /**
     * One element
     */
    @Test
    public void test_Average_oneReview(){

        //Create a list of Review for the test
        List<Review> listReviews = new ArrayList<>();
        listReviews.add(new Review("user1", "xx.jpg", "comment", 5));
        Mockito.when(mokeFakeApi.getReviews()).thenReturn(listReviews);

        RestaurantRepository mockRepository = new RestaurantRepository(mokeFakeApi);

        double dAvg = mockRepository.getReviewsAverage();
        double delta = 0.0;  // Tolerance
        assertEquals(5,dAvg,delta);

        System.out.println("test_Average_oneReview OK");
    }

    /**
     * Empty list
     */
    @Test
    public void test_Average_empyList(){

        //Create a list of Review for the test
        List<Review> listReviews = new ArrayList<>();
        Mockito.when(mokeFakeApi.getReviews()).thenReturn(listReviews);

        RestaurantRepository mockRepository = new RestaurantRepository(mokeFakeApi);

        double dAvg = mockRepository.getReviewsAverage();
        double delta = 0.0;  // Tolerance
        assertEquals(0,dAvg,delta);

        System.out.println("test_Average_empyList OK");
    }

    /**
     * Normal case
     */
    @Test
    public void test_Average_NoIntResult(){

        //Create a list of Review for the test
        List<Review> listReviews = new ArrayList<>();
        for (int nRate = 1; nRate<=4; nRate++){
            listReviews.add(new Review("user", "test.jpg", "comment", nRate));
        }

        Mockito.when(mokeFakeApi.getReviews()).thenReturn(listReviews);

        RestaurantRepository mockRepository = new RestaurantRepository(mokeFakeApi);

        double dAvg = mockRepository.getReviewsAverage();
        double delta = 0.0;  // Tolerance
        assertEquals(2.5,dAvg,delta);

        System.out.println("test_Average_NoIntResult OK");

    }






}
