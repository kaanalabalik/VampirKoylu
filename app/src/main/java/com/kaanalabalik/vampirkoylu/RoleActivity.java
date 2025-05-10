package com.kaanalabalik.vampirkoylu;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class RoleActivity extends AppCompatActivity {
    private List<String> roles = new ArrayList<>();
    private List<String> playerNames = new ArrayList<>();
    private int currentIndex = 0;
    private TextView tvRole;
    private Button btnShowRole;
    private ImageView ivCharacter;
    private boolean isRoleVisible = false;
    private ConstraintLayout layoutBackground;

    private List<String> playerRoles = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_role);

        tvRole = findViewById(R.id.tvRole);
        btnShowRole = findViewById(R.id.btnShowRole);
        ivCharacter = findViewById(R.id.ivCharacter);
        layoutBackground = findViewById(R.id.layoutBackground);

        Intent intent = getIntent();
        playerNames = intent.getStringArrayListExtra("playerNames");

        if (playerNames == null || playerNames.isEmpty()) {
            Toast.makeText(this, "Oyuncu isimleri alınamadı. Lütfen tekrar deneyin!", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        // RoleCounts'u Al ve Roller Listesini Oluştur
        HashMap<String, Integer> roleCounts = (HashMap<String, Integer>) intent.getSerializableExtra("roleCounts");
        if (roleCounts != null) {
            for (String role : roleCounts.keySet()) {
                int count = roleCounts.get(role);
                for (int i = 0; i < count; i++) {
                    roles.add(role);
                }
            }
        }

        // Eğer roller doğru şekilde eklenmediyse hata mesajı göster
        if (roles.size() > playerNames.size()) {
            Toast.makeText(this, "Rol sayısı oyuncu sayısından fazla olamaz!", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        // Kalan oyunculara köylü atanıyor
        int remainingPlayers = playerNames.size() - roles.size();
        for (int i = 0; i < remainingPlayers; i++) {
            roles.add("Köylü");
        }

        // Roller Karıştırılıyor
        Collections.shuffle(roles);

        // Oyuncular ve Roller SIRAYLA Eşleştiriliyor
        playerRoles.clear();
        for (int i = 0; i < playerNames.size(); i++) {
            playerRoles.add(roles.get(i));
        }

        // Kontrol Amacıyla Console'da Gösteriliyor
        for (int i = 0; i < playerNames.size(); i++) {
            System.out.println("Oyuncu: " + playerNames.get(i) + " - Rol: " + playerRoles.get(i));
        }

        updateButtonText();

        btnShowRole.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isRoleVisible) {
                    // Rolü göster
                    String role = playerRoles.get(currentIndex);

                    // Sadece Rolü Göster
                    tvRole.setText("Rolünüz: " + role);
                    tvRole.setVisibility(View.VISIBLE);
                    ivCharacter.setVisibility(View.VISIBLE);

                    // Butonun metnini değiştir
                    btnShowRole.setText("Rolü Gizle");
                    isRoleVisible = true;

                    // Rolün Görselini Ayarla
                    switch (role) {
                        case "Vampir":
                            ivCharacter.setImageResource(R.drawable.vampir_background);
                            break;
                        case "Köylü":
                            ivCharacter.setImageResource(R.drawable.koylu_background);
                            break;
                        case "Doktor":
                            ivCharacter.setImageResource(R.drawable.doktor_background);
                            break;
                        case "Gözcü":
                            ivCharacter.setImageResource(R.drawable.gozcu_background);
                            break;
                        case "Soytarı":
                            ivCharacter.setImageResource(R.drawable.soytari_background);
                            break;
                        default:
                            ivCharacter.setVisibility(View.GONE);
                            break;
                    }
                } else {
                    // Rolü gizle ve sıradaki oyuncuya geç
                    tvRole.setText("");
                    ivCharacter.setVisibility(View.GONE);
                    tvRole.setVisibility(View.GONE);

                    currentIndex++;

                    if (currentIndex < playerNames.size()) {
                        updateButtonText();
                    } else {
                        // Son oyuncuya da rol gösterildikten sonra oyun başlasın
                        Toast.makeText(RoleActivity.this, "Bütün roller gösterildi! Oyun başlıyor...", Toast.LENGTH_LONG).show();
                        Intent gameIntent = new Intent(RoleActivity.this, GameActivity.class);
                        gameIntent.putStringArrayListExtra("playerNames", new ArrayList<>(playerNames));
                        gameIntent.putStringArrayListExtra("roles", new ArrayList<>(playerRoles));
                        startActivity(gameIntent);
                        finish();
                    }

                    isRoleVisible = false;
                }
            }
        });
    }

    private void updateButtonText() {
        if (currentIndex < playerNames.size()) {
            String playerName = playerNames.get(currentIndex);

            // İsmin son harfini al
            char lastChar = playerName.toLowerCase().charAt(playerName.length() - 1);

            // Ünlü uyumuna göre doğru eki seç
            String suffix;
            if ("aıou".indexOf(lastChar) >= 0) {
                suffix = "'nın";
            } else if ("eiöü".indexOf(lastChar) >= 0) {
                suffix = "'nin";
            } else {
                // Eğer son harf ünsüzse varsayılan olarak 'nin kullan
                suffix = "'nin";
            }

            // Butonun metnini ayarla
            btnShowRole.setText(playerName + suffix + " rolünü göster");
        } else {
            btnShowRole.setText("Bütün Roller Gösterildi!");
        }
    } }

