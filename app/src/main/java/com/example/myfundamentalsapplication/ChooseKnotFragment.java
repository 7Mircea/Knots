package com.example.myfundamentalsapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ChooseKnotFragment extends Fragment {
    private RecyclerView recyclerView;
    private KnotViewModel knotViewModel;
    private OnClickInterface onClickInterface;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_choose_knot,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        onClickInterface = new OnClickInterface() {
            @Override
            public void onClick(int position) {
                Intent intent = new Intent(getActivity(),KnotActivity.class);
                intent.putExtra("position",position);
                startActivity(intent);
            }
        };
        final KnotAdapter adapter = new KnotAdapter(getContext(),onClickInterface);
        recyclerView = view.findViewById(R.id.recycler_view_knot);
        recyclerView.setAdapter(adapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        knotViewModel = new ViewModelProvider(this,
                ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().
                        getApplication())).get(KnotViewModel.class);
        knotViewModel.getAllKnots().observe(this, new Observer<List<Knot>>() {
            @Override
            public void onChanged(List<Knot> knots) {
                adapter.setList(knots);
            }
        });


    }
}
