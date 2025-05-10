package com.kaanalabalik.vampirkoylu;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;

public class RoleSelectionActivity extends AppCompatActivity {

    private GridView gvRoles;
    private Button btnContinue;
    private RoleAdapter adapter;
    private ArrayList<String> roleNames;
    private HashMap<String, Integer> roleCounts;
    private ArrayList<String> playerNames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_role_selection);

        // Oyuncu İsimlerini Al
        playerNames = getIntent().getStringArrayListExtra("playerNames");

        if (playerNames == null || playerNames.isEmpty()) {
            Toast.makeText(this, "Oyuncu isimleri alınamadı. Lütfen tekrar deneyin!", Toast.LENGTH_LONG).show();
            return;
        }

        // XML ile Bağlantılar
        gvRoles = findViewById(R.id.gvRoles);
        btnContinue = findViewById(R.id.btnContinue);

        // Roller ve Sayıları
        roleNames = new ArrayList<>();
        roleNames.add("Köylü");
        roleNames.add("Vampir");
        roleNames.add("Doktor");
        roleNames.add("Gözcü");
        roleNames.add("Soytarı");

        roleCounts = new HashMap<>();
        for (String role : roleNames) {
            roleCounts.put(role, 0);
        }

        // Custom Adapter Tanımlaması
        adapter = new RoleAdapter(this, roleNames, roleCounts);
        gvRoles.setAdapter(adapter);

        // Devam Et Butonu
        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int totalRoles = 0;
                for (int count : roleCounts.values()) {
                    totalRoles += count;
                }

                // Seçilen Toplam Rol Sayısının Oyuncu Sayısına Eşit Olması Gerekir
                if (totalRoles == playerNames.size()) {
                    Intent intent = new Intent(RoleSelectionActivity.this, RoleActivity.class);
                    intent.putExtra("roleCounts", roleCounts);
                    intent.putStringArrayListExtra("playerNames", new ArrayList<>(playerNames));
                    startActivity(intent);
                } else {
                    Toast.makeText(RoleSelectionActivity.this, "Seçilen rol sayısı oyuncu sayısına eşit olmalıdır!", Toast.LENGTH_SHORT).show();
                }
            }
   });
}
}
