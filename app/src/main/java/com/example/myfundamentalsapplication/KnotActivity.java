package com.example.myfundamentalsapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class KnotActivity extends AppCompatActivity {
    private ImageView mImage;
    private TextView mTitle;
    private TextView mDescription;
    private KnotViewModel mKnotViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_knot);

        mImage = findViewById(R.id.image_knot_activity);
        mTitle = findViewById(R.id.title_knot_activity);
        mDescription = findViewById(R.id.description_knot_activity);

        Intent intent = getIntent();
        final int position = intent.getIntExtra("position",0);

        mKnotViewModel = new ViewModelProvider(this,
                ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication()))
                .get(KnotViewModel.class);
        mKnotViewModel.getAllKnots().observe(this, new Observer<List<Knot>>() {
            @Override
            public void onChanged(List<Knot> knots) {
                Knot knot = knots.get(position);
                mImage.setImageDrawable(ContextCompat.getDrawable(getApplication(),knot.getImage()));
                mTitle.setText(knot.getName());
                mDescription.setText(getString(knot.getDescription()));
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }
}