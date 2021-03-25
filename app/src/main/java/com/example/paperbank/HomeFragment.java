package com.example.paperbank;

import android.app.FragmentManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.card.MaterialCardView;

public class HomeFragment extends Fragment {

    private MaterialCardView profileCardView, paperCardView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        profileCardView = view.findViewById(R.id.profile);
        paperCardView = view.findViewById(R.id.papers);

        profileCardView.setOnClickListener(v-> {

            getFragmentManager().beginTransaction().replace(R.id.frameLayout, new ProfileFragment()).addToBackStack(null).commit();
        });

        paperCardView.setOnClickListener(v -> {

            getFragmentManager().beginTransaction().replace(R.id.frameLayout, new PaperFragment()).addToBackStack(null).commit();
        });
    }
}