package de.hka.zaelfahrtendisposition;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;



public class SceduleActivity extends AppCompatActivity {
    private EditText startTimeEditText;
    private EditText endTimeEditText;
    private Button showTableButton;

    private Calendar startTime;
    private Calendar endTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scedule);
        startTimeEditText = findViewById(R.id.startTimeEditText);
        endTimeEditText = findViewById(R.id.endTimeEditText);
        showTableButton = findViewById(R.id.btn_apply);

        startTimeEditText.setOnClickListener(v -> showTimePicker(time -> {
            startTime = time;
            startTimeEditText.setText(formatTime(time));
        }));

        endTimeEditText.setOnClickListener(v -> showTimePicker(time -> {
            endTime = time;
            endTimeEditText.setText(formatTime(time));
        }));

        showTableButton.setOnClickListener(v -> {
            if (startTime == null || endTime == null) {
                Toast.makeText(this, "Bitte wähle Start und Endzeit", Toast.LENGTH_LONG).show();
            } else if (startTime.after(endTime)) {
                Toast.makeText(this, "Endzeit muss immer später als die Startzeit sein", Toast.LENGTH_LONG).show();
            } else {
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void showTimePicker(TimePickerCallback callback) {
        Calendar currentTime = Calendar.getInstance();
        int hour = currentTime.get(Calendar.HOUR_OF_DAY);
        int minute = currentTime.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, (view, selectedHour, selectedMinute) -> {
            Calendar selectedTime = Calendar.getInstance();
            selectedTime.set(Calendar.HOUR_OF_DAY, selectedHour);
            selectedTime.set(Calendar.MINUTE, selectedMinute);
            callback.onTimeSelected(selectedTime);
        }, hour, minute, true);

        timePickerDialog.show();
    }

    private String formatTime(Calendar calendar) {
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        return String.format("%02d:%02d", hour, minute);
    }

    interface TimePickerCallback {
        void onTimeSelected(Calendar time);
    }

    }
