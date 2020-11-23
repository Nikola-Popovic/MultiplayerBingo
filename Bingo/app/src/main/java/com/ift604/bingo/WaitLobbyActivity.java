package com.ift604.bingo;

import android.os.Bundle;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.ift604.bingo.model.Lobby;

public class WaitLobbyActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wait_lobby);
        Lobby lobby = (Lobby) getIntent().getSerializableExtra("LOBBY_ID");
        FrameLayout waitLobbyListFrameLayout = findViewById(R.id.wait_lobby_participant_frame_layout);
        getSupportFragmentManager().beginTransaction().add(waitLobbyListFrameLayout.getId(), WaitLobbyParticipantListFragment.newInstance(lobby.getId()), "un autre joli tag").commit();

    }
}