package de.hka.zaelfahrtendisposition;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
public class LineActivity extends AppCompatActivity {
    private Spinner Spinner_line;
    private TextView selectedText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_line);

        Intent intent = this.getIntent();
        Spinner_line = findViewById(R.id.Spinner_line);
        selectedText= findViewById(R.id.selectedText);

        // Erhalte die übergebenen Linien
        ArrayList<String> linienListe = getIntent().getStringArrayListExtra("linienListe");

        // Adapter für Spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, linienListe);
        Spinner_line.setAdapter(adapter);

        // Item selected listener
        Spinner_line.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String selectedItem = (String) parentView.getItemAtPosition(position);
                selectedText.setText("Selected: " + selectedItem);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Do nothing
            }
        });
    }
}