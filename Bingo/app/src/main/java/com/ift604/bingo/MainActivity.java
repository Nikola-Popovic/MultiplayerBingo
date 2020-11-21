package com.ift604.bingo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button createLobbyBtn = findViewById(R.id.lobby_create_game_button);
        createLobbyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getSupportFragmentManager();
                //TODO HOST ID ?
                CreateLobbyFragment createLobbyFragment = CreateLobbyFragment.newInstance(0);
                createLobbyFragment.show(fm, "");
            }
        });
        Button joinLobbyBtn = findViewById(R.id.lobby_join_game_button);
        joinLobbyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getSupportFragmentManager();
                //TODO PLAYER ID ?
                JoinLobbyFragment joinLobbyFragment = JoinLobbyFragment.newInstance(0);
                joinLobbyFragment.show(fm, "");
            }
        });
    }
}