package de.hka.zaelfahrtendisposition;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.TimePickerDialog;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class SceduleActivity extends AppCompatActivity {
    private EditText startTimeEditText;
    private EditText endTimeEditText;
    private Button showTableButton;

    private LocalTime startTime;
    private LocalTime endTime;

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scedule);

        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);

        // Zeiten aus den SharedPreferences abrufen, oder Standardwerte setzen
        startTime = retrieveTime("start_time", LocalTime.MIN);
        endTime = retrieveTime("end_time", LocalTime.MAX);

        startTimeEditText = findViewById(R.id.startTimeEditText);
        endTimeEditText = findViewById(R.id.endTimeEditText);
        showTableButton = findViewById(R.id.btn_apply);

        // Wenn Zeiten vorhanden sind, setzen wir die EditText-Felder
        startTimeEditText.setText(formatTime(startTime));
        endTimeEditText.setText(formatTime(endTime));

        startTimeEditText.setOnClickListener(v -> showTimePicker(time -> {
            startTime = time;
            startTimeEditText.setText(formatTime(time));
            saveTime("start_time", time); // Zeit speichern
        }));

        endTimeEditText.setOnClickListener(v -> showTimePicker(time -> {
            endTime = time;
            endTimeEditText.setText(formatTime(time));
            saveTime("end_time", time); // Zeit speichern
        }));

        showTableButton.setOnClickListener(v -> {
            if (startTime == null || endTime == null) {
                Toast.makeText(this, "Bitte wähle Start und Endzeit", Toast.LENGTH_LONG).show();
            } else if (startTime.isAfter(endTime)) {
                Toast.makeText(this, "Endzeit muss immer später als die Startzeit sein", Toast.LENGTH_LONG).show();
            } else {
                // Die Zeiten als Ergebnis an die MainActivity zurückgeben
                Intent resultIntent = new Intent();
                resultIntent.putExtra("start_time", startTime.format(DateTimeFormatter.ISO_LOCAL_TIME));
                resultIntent.putExtra("end_time", endTime.format(DateTimeFormatter.ISO_LOCAL_TIME));
                setResult(RESULT_OK, resultIntent);
                finish();
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Beim Schließen der Activity die gespeicherten Zeiten löschen
        clearSavedTimes();

        // Optional: Setze die Startzeit auf 0 Uhr und Endzeit auf 23:59
        saveTime("start_time", LocalTime.MIN);
        saveTime("end_time", LocalTime.of(23, 59));
    }


    private void showTimePicker(TimePickerCallback callback) {
        LocalTime currentTime = LocalTime.now();

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, (view, hourOfDay, minute) -> {
            LocalTime selectedTime = LocalTime.of(hourOfDay, minute);
            callback.onTimeSelected(selectedTime);
        }, currentTime.getHour(), currentTime.getMinute(), true);

        timePickerDialog.show();
    }

    private void saveTime(String key, LocalTime time) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, time.format(DateTimeFormatter.ISO_LOCAL_TIME));
        editor.apply();
    }

    private LocalTime retrieveTime(String key, LocalTime defaultValue) {
        String timeStr = sharedPreferences.getString(key, null);
        if (timeStr != null) {
            try {
                return LocalTime.parse(timeStr, DateTimeFormatter.ISO_LOCAL_TIME);
            } catch (DateTimeParseException e) {
                e.printStackTrace();
            }
        }
        return defaultValue;
    }

    private void clearSavedTimes() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("start_time");
        editor.remove("end_time");
        editor.apply();
    }

    private String formatTime(LocalTime time) {
        return time.format(DateTimeFormatter.ofPattern("HH:mm"));
    }

    interface TimePickerCallback {
        void onTimeSelected(LocalTime time);
    }
}
