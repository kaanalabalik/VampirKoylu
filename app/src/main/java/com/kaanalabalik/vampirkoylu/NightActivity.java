package com.kaanalabalik.vampirkoylu;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class NightActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_night);

        Button btnNextNight = findViewById(R.id.btnNextNight);

        btnNextNight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Geceyi bitir ve bir sonraki sayfaya geç
                Intent intent = new Intent(NightActivity.this, MainActivity.class);
                startActivity(intent);
                finish(); // Bu aktiviteyi kapat
            }
        });
    }
}
