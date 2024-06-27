package de.hka.zaelfahrtendisposition;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class DaygroupActivity extends AppCompatActivity {

    private boolean schultagEinbeziehen = true;
    private boolean ferientagEinbeziehen = true;
    private boolean samstagEinbeziehen = true;
    private boolean sonnFeiertagEinbeziehen = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daygroup);

        Button btnSchultag = findViewById(R.id.btn_schoolday);
        Button btnFerien = findViewById(R.id.btn_vacation);
        Button btnSamstag = findViewById(R.id.btn_saturday);
        Button btnSonnFeiertag = findViewById(R.id.btn_sun_holiday);
        Button btnApply = findViewById(R.id.btn_apply);

        // Button-Click-Handler für Schultag
        btnSchultag.setOnClickListener(v -> {
            schultagEinbeziehen = !schultagEinbeziehen;
            updateButtonState(btnSchultag, schultagEinbeziehen);
        });

        // Button-Click-Handler für Ferien
        btnFerien.setOnClickListener(v -> {
            ferientagEinbeziehen = !ferientagEinbeziehen;
            updateButtonState(btnFerien, ferientagEinbeziehen);
        });

        // Button-Click-Handler für Samstag
        btnSamstag.setOnClickListener(v -> {
            samstagEinbeziehen = !samstagEinbeziehen;
            updateButtonState(btnSamstag, samstagEinbeziehen);
        });

        // Button-Click-Handler für Sonn- und Feiertag
        btnSonnFeiertag.setOnClickListener(v -> {
            sonnFeiertagEinbeziehen = !sonnFeiertagEinbeziehen;
            updateButtonState(btnSonnFeiertag, sonnFeiertagEinbeziehen);
        });

        // Button-Click-Handler für Übernehmen
        btnApply.setOnClickListener(v -> {
            Intent resultIntent = new Intent();
            resultIntent.putExtra("schultag", schultagEinbeziehen);
            resultIntent.putExtra("ferientag", ferientagEinbeziehen);
            resultIntent.putExtra("samstag", samstagEinbeziehen);
            resultIntent.putExtra("sonnFeiertag", sonnFeiertagEinbeziehen);
            setResult(RESULT_OK, resultIntent);
            finish();
        });

        // Aktualisiere die Button-Texte basierend auf den aktuellen Auswahlzuständen
        updateButtonState(btnSchultag, schultagEinbeziehen);
        updateButtonState(btnFerien, ferientagEinbeziehen);
        updateButtonState(btnSamstag, samstagEinbeziehen);
        updateButtonState(btnSonnFeiertag, sonnFeiertagEinbeziehen);
    }

    // Methode zur Aktualisierung der Button-Texte
    private void updateButtonState(Button button, boolean isActive) {
        if (isActive) {
            button.setText(button.getText().toString() + " (einbeziehen)");
        } else {
            button.setText(button.getText().toString().replace(" (einbeziehen)", ""));
        }
    }
}
