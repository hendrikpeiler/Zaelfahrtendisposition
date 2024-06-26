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
public class LineActivity extends AppCompatActivity {
    private Spinner mySpinner;
    private TextView selectedTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_line);

        Intent intent = this.getIntent();
        mySpinner = findViewById(R.id.mySpinner);
        selectedTextView = findViewById(R.id.selectedTextView);

        String[] items = new String[]{"Option 1", "Option 2", "Option 3", "Option 4"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        mySpinner.setAdapter(adapter);

        mySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String selectedItem = (String) parentView.getItemAtPosition(position);
                selectedTextView.setText("Selected: " + selectedItem);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }
        });
    }
}