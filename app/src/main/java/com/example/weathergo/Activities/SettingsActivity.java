package com.example.weathergo.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.weathergo.Fragments.HomeFragment;
import com.example.weathergo.R;

public class SettingsActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    private String selectedUnit = "Celsius";

    public static String Name;

    public static String getName() {
        return Name;
    }

    public static void setUser(String name) {
        Name = name;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        sharedPreferences = getSharedPreferences("MyPreferences", MODE_PRIVATE);

        RadioGroup radioGroup = findViewById(R.id.radioGroup);
        EditText locationEdt = findViewById(R.id.locationEdt);

        selectedUnit = sharedPreferences.getString("unit", "Celsius");
        String selectedLocation = sharedPreferences.getString("location", "");
        locationEdt.setText(selectedLocation);
        // Đặt đơn vị đúng RadioButton tương ứng
        if (selectedUnit.equals("Celsius")) {
            RadioButton celsiusRadioButton = findViewById(R.id.celsiusRadioButton);
            celsiusRadioButton.setChecked(true);
        } else if (selectedUnit.equals("Fahrenheit")) {
            RadioButton fahrenheitRadioButton = findViewById(R.id.fahrenheitRadioButton);
            fahrenheitRadioButton.setChecked(true);
        }

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                if (checkedId == R.id.celsiusRadioButton){
                    selectedUnit="Celsius";
                }
                else if (checkedId == R.id.fahrenheitRadioButton){
                    selectedUnit = "Fahrenheit";
                }

            }
        });

        Button saveButton = findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Lưu đơn vị được chọn vào SharedPreferences
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("unit", selectedUnit);
                editor.putString("location", locationEdt.getText().toString());
                editor.apply();
                //
                Intent intent = new Intent(SettingsActivity.this, MainActivity.class);
                startActivity(intent);
                Log.d("Đã chọn đơn vị:", selectedUnit);
                Log.d("Đã chuyển sang địa điểm: ", selectedLocation);
            }
        });

        EditText nameEdt = findViewById(R.id.nameEdt);
        Button startBtn = findViewById(R.id.startBtn);
        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txtName = nameEdt.getText().toString();
                Intent i = new Intent(SettingsActivity.this, NameActivity.class);
                i.putExtra("txtName", txtName);
                startActivity(i);
            }
        });
    }
}