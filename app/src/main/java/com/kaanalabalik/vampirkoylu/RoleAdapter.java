package com.kaanalabalik.vampirkoylu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class RoleAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<String> roleNames;
    private HashMap<String, Integer> roleCounts;

    public RoleAdapter(Context context, ArrayList<String> roleNames, HashMap<String, Integer> roleCounts) {
        this.context = context;
        this.roleNames = roleNames;
        this.roleCounts = roleCounts;
    }

    @Override
    public int getCount() {
        return roleNames.size();
    }

    @Override
    public Object getItem(int position) {
        return roleNames.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.grid_item_role, parent, false);
        }

        String roleName = roleNames.get(position);

        ImageView imgRoleIcon = convertView.findViewById(R.id.imgRoleIcon);
        TextView tvRoleName = convertView.findViewById(R.id.tvRoleName);
        TextView tvRoleCount = convertView.findViewById(R.id.tvRoleCount);
        TextView tvIncrease = convertView.findViewById(R.id.tvIncrease);
        TextView tvDecrease = convertView.findViewById(R.id.tvDecrease);

        tvRoleName.setText(roleName);
        tvRoleCount.setText(String.valueOf(roleCounts.get(roleName)));

        // İkonları Ayarla
        switch (roleName) {
            case "Vampir":
                imgRoleIcon.setImageResource(R.drawable.vampir_background);
                break;
            case "Köylü":
                imgRoleIcon.setImageResource(R.drawable.koylu_background);
                break;
            case "Doktor":
                imgRoleIcon.setImageResource(R.drawable.doktor_background);
                break;
            case "Gözcü":
                imgRoleIcon.setImageResource(R.drawable.gozcu_background);
                break;
            case "Soytarı":
                imgRoleIcon.setImageResource(R.drawable.soytari_background);
                break;
        }

        // Arttırma Butonu
        tvIncrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentCount = roleCounts.get(roleName);
                roleCounts.put(roleName, currentCount + 1);
                notifyDataSetChanged();
            }
        });

        // Azaltma Butonu
        tvDecrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentCount = roleCounts.get(roleName);
                if (currentCount > 0) {
                    roleCounts.put(roleName, currentCount - 1);
                    notifyDataSetChanged();
                }
            }
        });

        return convertView;
}
}
