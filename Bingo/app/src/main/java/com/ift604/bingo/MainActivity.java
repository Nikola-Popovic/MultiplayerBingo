package com.ift604.bingo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.ift604.bingo.model.Participant;
import com.ift604.bingo.service.CreateUserService;
import com.ift604.bingo.util.Util;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startCreateUserService();
        registerCreateUserReceiver();


        Button findLobbyBtn = findViewById(R.id.menu_find_lobby_button);
        findLobbyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getSupportFragmentManager();
                // Get find lobby fragment
                LobbyItemListFragment lobbyItemListFragment = LobbyItemListFragment.newInstance();
                lobbyItemListFragment.show(fm, "");
            }
        });


        Button createLobbyBtn = findViewById(R.id.menu_create_game_button);
        createLobbyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getSupportFragmentManager();
                //TODO HOST ID ?
                CreateLobbyFragment createLobbyFragment = CreateLobbyFragment.newInstance(0);
                createLobbyFragment.show(fm, "");
            }
        });

        Button joinLobbyBtn = findViewById(R.id.menu_join_game_button);
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

    private void registerCreateUserReceiver() {
        CreateUserReceiver receiver = new CreateUserReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(CreateUserService.CREATE_USER_ACTION);
        registerReceiver(receiver, intentFilter);
    }

    private void startCreateUserService() {
        Intent lobbiesService = new Intent(this, CreateUserService.class);
        startService(lobbiesService);
    }


    public class CreateUserReceiver extends BroadcastReceiver {

        public CreateUserReceiver() {
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            Participant participant = (Participant) intent.getSerializableExtra(CreateUserService.CREATE_USER_EXTRA);
            Util.sharedPref(context).edit().putInt(Util.SharedPreferenceUserId, participant.getId()).apply();
        }
    }
}