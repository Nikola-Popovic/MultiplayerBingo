package com.ift604.bingo.fel.lobby;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentManager;

import com.ift604.bingo.R;
import com.ift604.bingo.dal.LocationProvider;
import com.ift604.bingo.model.Participant;
import com.ift604.bingo.service.CreateUserService;
import com.ift604.bingo.util.Util;

public class MainActivity extends AppCompatActivity implements ActivityCompat.OnRequestPermissionsResultCallback {

    private LocationProvider locationProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startCreateUserService();
        registerCreateUserReceiver();
        locationProvider = new LocationProvider(getApplicationContext(), this);


        Button findLobbyBtn = findViewById(R.id.menu_find_lobby_button);
        findLobbyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent lobbyIntent = new Intent(MainActivity.this, FindLobbyActivity.class);

                startActivity(lobbyIntent);
            }
        });


        Button createLobbyBtn = findViewById(R.id.menu_create_game_button);
        createLobbyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getSupportFragmentManager();
                CreateLobbyFragment createLobbyFragment = CreateLobbyFragment.newInstance(locationProvider);
                createLobbyFragment.show(fm, "");
            }
        });

        Button joinLobbyBtn = findViewById(R.id.menu_join_game_button);
        joinLobbyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getSupportFragmentManager();
                JoinLobbyFragment joinLobbyFragment = JoinLobbyFragment.newInstance();
                joinLobbyFragment.show(fm, "");
            }
        });

        EditText playerName = findViewById(R.id.player_name);
        // Get preferences
        SharedPreferences settings = getSharedPreferences("UserInfo", 0);
        String userName = settings.getString("Username", "Anonyme");
        playerName.setText(userName);

        playerName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                SharedPreferences settings = getSharedPreferences("UserInfo", 0);
                SharedPreferences.Editor editor = settings.edit();
                editor.putString("Username",editable.toString());
                editor.commit();
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
        lobbiesService.setAction(CreateUserService.CREATE_USER_ACTION);
        startService(lobbiesService);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            locationProvider.startListening();
        }
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