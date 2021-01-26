package com.example.myfundamentalsapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import java.util.List;

public class KnotFragment extends Fragment {
    private ImageView mImage;
    private TextView mTitle;
    private TextView mDescription;
    private KnotViewModel mKnotViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_knot,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mImage = view.findViewById(R.id.image_knot_activity);
        mTitle = view.findViewById(R.id.title_knot_activity);
        mDescription = view.findViewById(R.id.description_knot_activity);

        final int position = getArguments().getInt("position");

        mKnotViewModel = new ViewModelProvider(this,
                ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication()))
                .get(KnotViewModel.class);
        mKnotViewModel.getAllKnots().observe(this, new Observer<List<Knot>>() {
            @Override
            public void onChanged(List<Knot> knots) {
                Knot knot = knots.get(position);
                mImage.setImageDrawable(ContextCompat.getDrawable(getActivity().getApplication(),knot.getImage()));
                mTitle.setText(knot.getName());
                mDescription.setText(getString(knot.getDescription()));
            }
        });
    }
}
