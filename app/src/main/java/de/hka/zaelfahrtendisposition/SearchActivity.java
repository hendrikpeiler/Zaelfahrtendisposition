package de.hka.zaelfahrtendisposition;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FahrtAdapter fahrtAdapter;
    private ArrayList<Fahrt> fahrtList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        // Empfange die Daten aus dem Intent
        fahrtList = getIntent().getParcelableArrayListExtra("bewertung_list");

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        fahrtAdapter = new FahrtAdapter(fahrtList);
        recyclerView.setAdapter(fahrtAdapter);
    }
}
