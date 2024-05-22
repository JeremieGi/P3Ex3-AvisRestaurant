package com.openclassrooms.tajmahal.domain.model;


/**
 * Class with all stat of the restaurant
 */
public class RestaurantStat {

    double dAverage;
    int nTotalReviews;
    int[] anPercentPerNote;


    public RestaurantStat(Double dAverage, int nTotalReviews, int[] anPercentPerNote) {
        this.dAverage = dAverage;
        this.nTotalReviews = nTotalReviews;
        this.anPercentPerNote = anPercentPerNote;
    }

    public double getdAverage() {
        return dAverage;
    }

    public int getnTotalReviews() {
        return nTotalReviews;
    }

    public int[] getAnPercentPerNote() {
        return anPercentPerNote;
    }
}
