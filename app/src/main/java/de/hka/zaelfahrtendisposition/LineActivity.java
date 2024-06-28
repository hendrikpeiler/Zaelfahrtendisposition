package de.hka.zaelfahrtendisposition;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class LineActivity extends AppCompatActivity {

    private ArrayList<String> linienListe;
    private boolean[] checkedItems;
    private Set<String> selectedLinienSet;
    private Button btn_selectLines;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_line);

        Intent intent = getIntent();
        linienListe = intent.getStringArrayListExtra("linienListe");
        if (linienListe == null) {
            linienListe = new ArrayList<>();
        }
        Log.d("LineActivity", "Linienliste empfangen size: " + linienListe.size()); // Debugging-Log
        for (String linie : linienListe) {
            Log.d("LineActivity", "Linie: " + linie); // Debugging-Log
        }

        checkedItems = new boolean[linienListe.size()];
        selectedLinienSet = new HashSet<>();
        btn_selectLines = findViewById(R.id.btn_selectLines);

        btn_selectLines.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(LineActivity.this);
            builder.setTitle("Wähle Linien");

            builder.setMultiChoiceItems(linienListe.toArray(new CharSequence[0]), checkedItems, (dialog, which, isChecked) -> {
                if (isChecked) {
                    selectedLinienSet.add(linienListe.get(which));
                } else {
                    selectedLinienSet.remove(linienListe.get(which));
                }
            });

            builder.setPositiveButton("OK", (dialog, which) -> {
                Toast.makeText(LineActivity.this, "Ausgewählte Linien: " + selectedLinienSet, Toast.LENGTH_SHORT).show();
                Intent resultIntent = new Intent();
                resultIntent.putStringArrayListExtra("selectedLinien", new ArrayList<>(selectedLinienSet));
                setResult(RESULT_OK, resultIntent);
                finish();
            });

            builder.setNegativeButton("Abbrechen", (dialog, which) -> dialog.dismiss());

            AlertDialog dialog = builder.create();
            dialog.show();
        });
    }

    // In der onBackPressed-Methode oder wo die Auswahl bestätigt wird:
    @Override
    public void onBackPressed() {
        Intent resultIntent = new Intent();
        resultIntent.putStringArrayListExtra("selectedLinien", new ArrayList<>(selectedLinienSet));
        setResult(RESULT_OK, resultIntent);
        super.onBackPressed(); // Wichtig, um das Ergebnis an die aufrufende Activity zu übergeben
    }


}
