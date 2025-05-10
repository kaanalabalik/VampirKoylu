package com.kaanalabalik.vampirkoylu;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class PlayerEntryActivity extends AppCompatActivity {

    private EditText etPlayerName;
    private FloatingActionButton fabAddPlayer;
    private Button btnStartGame;
    private GridView gvPlayers;
    private ArrayList<String> playerNames;
    private CustomAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_entry);

        // XML ile Bağlantılar
        etPlayerName = findViewById(R.id.etPlayerName);
        fabAddPlayer = findViewById(R.id.fabAddPlayer);
        btnStartGame = findViewById(R.id.btnStartGame);
        gvPlayers = findViewById(R.id.gvPlayers);

        // Oyuncu Listesi Başlangıcı
        playerNames = new ArrayList<>();

        // Custom Adapter Tanımlaması
        adapter = new CustomAdapter();
        gvPlayers.setAdapter(adapter);

        // FloatingActionButton ile Oyuncu Ekleme
        fabAddPlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etPlayerName.getText().toString().trim();

                if (!name.isEmpty()) {
                    playerNames.add(name);
                    adapter.notifyDataSetChanged();
                    etPlayerName.setText("");
                    gvPlayers.setSelection(playerNames.size() - 1);
                } else {
                    Toast.makeText(PlayerEntryActivity.this, "Lütfen bir isim girin!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Oyuna Başla Butonu - RoleSelectionActivity'e Geçiş
        btnStartGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!playerNames.isEmpty()) {
                    Intent intent = new Intent(PlayerEntryActivity.this, RoleSelectionActivity.class);
                    intent.putStringArrayListExtra("playerNames", new ArrayList<>(playerNames));
                    startActivity(intent);
                } else {
                    Toast.makeText(PlayerEntryActivity.this, "Lütfen en az 1 oyuncu ekleyin!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // Custom Adapter Sınıfı
    private class CustomAdapter extends ArrayAdapter<String> {

        public CustomAdapter() {
            super(PlayerEntryActivity.this, R.layout.grid_item_player, playerNames);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.grid_item_player, parent, false);
            }

            // Görsel ve Metin Bağlantıları
            ImageView imgAvatar = convertView.findViewById(R.id.imgAvatar);
            TextView tvPlayerName = convertView.findViewById(R.id.tvPlayerName);

            // Verileri Yerleştirme
            tvPlayerName.setText(playerNames.get(position));
            tvPlayerName.setTextColor(getResources().getColor(android.R.color.white));
            imgAvatar.setImageResource(R.drawable.ic_avatar);

            return convertView;
}
    }
}
