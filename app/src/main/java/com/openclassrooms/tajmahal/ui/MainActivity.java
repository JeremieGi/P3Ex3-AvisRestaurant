package com.openclassrooms.tajmahal.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.View;

import com.openclassrooms.tajmahal.R;
import com.openclassrooms.tajmahal.databinding.ActivityMainBinding;
import com.openclassrooms.tajmahal.ui.restaurant.DetailsFragment;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, DetailsFragment.newInstance())
                    .commitNow();
        }


//        /**
//         * Listener of fragment changement in de fragment container
//         */
//        getSupportFragmentManager().addOnBackStackChangedListener(() -> {
//
//            // get the current fragment
//            Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.container);
//
//            // fragment Détails
//            if (currentFragment.getClass() == DetailsFragment.class){
//                DetailsFragment fragmentDetail = (DetailsFragment) currentFragment;
//                if (fragmentDetail != null) {
//                    fragmentDetail.onChildFragmentClosed();
//                }
//            }
//
//
//        });

    }

}