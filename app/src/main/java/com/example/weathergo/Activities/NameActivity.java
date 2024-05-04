package com.example.weathergo.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.weathergo.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class NameActivity extends AppCompatActivity {
    TextView nameTxt, tempTxt, realFeelTxt, conditionTxt, humidTxt, windSpeedTxt, cloudTxt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name);

        tempTxt = findViewById(R.id.tempTxt2);
        realFeelTxt = findViewById(R.id.realfeelTxt2);
        conditionTxt = findViewById(R.id.conditionTxt2);
        humidTxt = findViewById(R.id.humidTxt2);
        windSpeedTxt = findViewById(R.id.windspeedTxt2);
        cloudTxt = findViewById(R.id.cloudTxt2);
        nameTxt = findViewById(R.id.nameTxt);
        Intent i = getIntent();
        if (i != null) {
            String txtName = i.getStringExtra("txtName");

            // Hiển thị thông điệp chào mừng
            TextView greetingTextView = findViewById(R.id.nameTxt);
            greetingTextView.setText("Xin chào " + txtName + "!");
        }
        getWeatherData();
    }

    public void getWeatherData(){
        String url = "https://api.weatherapi.com/v1/current.json?key=3e863d90628d41b2a6e72023232709&q=Hanoi&aqi=no";
        RequestQueue queue = Volley.newRequestQueue(this);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, response -> {
            try {
                // nhiet do o khung chinh
                JSONObject currentObject = response.getJSONObject("current");

                // lay nhiet do
                Integer temperature = currentObject.getInt("temp_c");

                tempTxt.setText("Nhiệt độ hiện tại: "+temperature + "°");
                // do F
                Integer temperatureF = temperature*9/5+32;


                // doan nay de lay tinh trang thoi tiet
                JSONObject conditionObject = currentObject.getJSONObject("condition");
                String textCondition = conditionObject.getString("text");

                // Cai nay de anh xa gia tri voi api (hoi hoi na na if else)
                HashMap<String, String> weatherMapping = new HashMap<>();

                // vi du cai nay kieu nhu la "neu gia tri tu api la "clear" thi minh se hien thi len man hinh la "troi quang"
                weatherMapping.put("Clear", "Trời quang");
                weatherMapping.put("Rainy", "Mưa");
                weatherMapping.put("Cloudy", "Nhiều mây");
                weatherMapping.put("Partly cloudy", "Mây rải rác");
                weatherMapping.put("Sunny", "Nắng");
                weatherMapping.put("Moderate rain", "Mưa vừa");
                weatherMapping.put("Light rain", "Mưa nhỏ");
                weatherMapping.put("Overcast", "Âm u");
                weatherMapping.put("Patchy rain possible", "Có thể mưa");
                weatherMapping.put("Patchy light rain with thunder", "Mưa có sấm sét");
                weatherMapping.put("Moderate or heavy rain with thunder", "Mưa nặng hạt có sấm sét");
                weatherMapping.put("Mist", "Sương mù");
                String displayValue = weatherMapping.get(textCondition);
                conditionTxt.setText(displayValue);
                switch (displayValue){
                    case "Mưa nặng hạt có sấm sét":
                        conditionTxt.setTextSize(14);
                        break;
                    case "Mưa có sấm sét":
                        conditionTxt.setTextSize(15);
                        break;
                    case"Có thể mưa":
                        conditionTxt.setTextSize(20);
                        break;
                    case "Âm u":
                        break;
                    case "Mưa nhỏ":
                        break;
                    case "Mưa vừa":
                        break;
                    case "Mây rải rác":
                        Log.d("Result","Load gif thành công");
                        break;
                    case "Trời quang":
                        Log.d("Result","Load gif thành công");
                        break;
                    case "Nắng":
                        break;
                    case "Mưa":
                        break;
                    case "Sương mù":
                        break;
                }


                // nhiet do cam nhan
                int realFeelTemp = currentObject.getInt("feelslike_c");
                int realFeelTemperatureF = realFeelTemp*9/5+32;
                realFeelTxt.setText("Nhiệt độ cảm nhận: "+realFeelTemp+""+"°C");

                // do am
                int humidity = currentObject.getInt("humidity");
                humidTxt.setText("Độ ẩm: "+humidity+""+"%");

                // toc do gio
                double windSpeed = currentObject.getDouble("wind_kph");
                windSpeedTxt.setText("Tốc độ gió: "+windSpeed+""+"km/h");

                // luong may
                int cloud = currentObject.getInt("cloud");
                cloudTxt.setText("Lượng mây"+cloud+""+"%");
            } catch (JSONException e) {
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }, error -> Toast.makeText(this, error.toString(), Toast.LENGTH_SHORT).show());
        queue.add(request);
    }
}