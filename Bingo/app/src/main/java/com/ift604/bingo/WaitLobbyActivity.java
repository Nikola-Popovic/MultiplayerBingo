package com.ift604.bingo;

import android.os.Bundle;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;

public class WaitLobbyActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wait_lobby);
        int lobbyId = getIntent().getIntExtra("LOBBY_ID", 0);
        FrameLayout waitLobbyListFrameLayout = findViewById(R.id.wait_lobby_participant_frame_layout);
        getSupportFragmentManager().beginTransaction().add(waitLobbyListFrameLayout.getId(), WaitLobbyParticipantListFragment.newInstance(lobbyId), "un autre joli tag").commit();

    }
}