package de.hka.zaelfahrtendisposition;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class DirectionActivity extends AppCompatActivity {

    private boolean richtung1Einbeziehen = true;
    private boolean richtung2Einbeziehen = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_direction);

        Button btnRichtung1 = findViewById(R.id.btn_richtung_1);
        Button btnRichtung2 = findViewById(R.id.btn_richtung_2);
        Button btnApply = findViewById(R.id.btn_apply);

        btnRichtung1.setOnClickListener(v -> {
            richtung1Einbeziehen = !richtung1Einbeziehen;
            updateButtonState(btnRichtung1, richtung1Einbeziehen);
        });

        btnRichtung2.setOnClickListener(v -> {
            richtung2Einbeziehen = !richtung2Einbeziehen;
            updateButtonState(btnRichtung2, richtung2Einbeziehen);
        });

        btnApply.setOnClickListener(v -> {
            Intent resultIntent = new Intent();
            resultIntent.putExtra("richtung1", richtung1Einbeziehen);
            resultIntent.putExtra("richtung2", richtung2Einbeziehen);
            setResult(RESULT_OK, resultIntent);
            finish();
        });

        updateButtonState(btnRichtung1, richtung1Einbeziehen);
        updateButtonState(btnRichtung2, richtung2Einbeziehen);
    }

    private void updateButtonState(Button button, boolean isActive) {
        if (isActive) {
            button.setText(button.getText().toString() + " (einbeziehen)");
        } else {
            button.setText(button.getText().toString().replace(" (einbeziehen)", ""));
        }
    }
}
