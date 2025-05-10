package com.kaanalabalik.vampirkoylu;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class GameActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        Button btnEndGame = findViewById(R.id.btnEndGame);

        btnEndGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Oyunu bitir ve ana ekrana dön
                Intent intent = new Intent(GameActivity.this, NightActivity.class);
                startActivity(intent);
                finish(); // Bu aktiviteyi kapat
            }
   });
}
}
