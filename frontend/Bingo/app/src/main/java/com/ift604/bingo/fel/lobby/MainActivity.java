package com.ift604.bingo.fel.lobby;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.ift604.bingo.R;
import com.ift604.bingo.model.Participant;
import com.ift604.bingo.service.CreateUserService;
import com.ift604.bingo.service.UpdateUserService;
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
                Intent lobbyIntent = new Intent(MainActivity.this, FindLobbyActivity.class);
                startActivity(lobbyIntent);
            }
        });


        Button createLobbyBtn = findViewById(R.id.menu_create_game_button);
        createLobbyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getSupportFragmentManager();
                CreateLobbyFragment createLobbyFragment = CreateLobbyFragment.newInstance();
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

        final EditText playerName = findViewById(R.id.player_name);
        // Get preferences
        SharedPreferences settings = getSharedPreferences("UserInfo", 0);
        final String userName = settings.getString("Username", "Anonyme");
        playerName.setText(userName);
        playerName.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
                if (keyEvent.getAction() == KeyEvent.ACTION_DOWN)
                {
                    switch (keyCode)
                    {
                        case KeyEvent.KEYCODE_DPAD_CENTER:
                        case KeyEvent.KEYCODE_ENTER:
                            SharedPreferences settings = getSharedPreferences("UserInfo", 0);
                            SharedPreferences.Editor editor = settings.edit();
                            editor.putString("Username",playerName.getText().toString());
                            editor.commit();
                            Intent updateUserService = new Intent(getApplicationContext(), UpdateUserService.class);
                            updateUserService.putExtra(UpdateUserService.UPDATE_USER_ID_EXTRA, Util.getConnectedUserId(getApplicationContext()));
                            updateUserService.putExtra(UpdateUserService.UPDATE_USER_NAME_EXTRA, playerName.toString());
                            updateUserService.setAction(UpdateUserService.UPDATE_USER_ACTION);
                            startService(updateUserService);
                            return true;
                        default:
                            break;
                    }
                }
                return false;
            }
        });

        playerName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus) {
                    SharedPreferences settings = getSharedPreferences("UserInfo", 0);
                    String username = settings.getString("Username", "Anonyme");
                    playerName.setText(username);
                } else {
                }
            }});
    }

    private void registerCreateUserReceiver() {
        CreateUserReceiver receiver = new CreateUserReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(CreateUserService.CREATE_USER_ACTION);
        registerReceiver(receiver, intentFilter);
    }

    private void startCreateUserService() {
        //String userName = settings.getString("Username", "Anonyme");
        SharedPreferences sharedPreferences =
                getSharedPreferences("UserInfo", MODE_PRIVATE);

        // If we switch backend to a NOSQL database. (after 2020)
        boolean isCreated = sharedPreferences.getBoolean("IS_CREATED", false);

        Intent createUserService = new Intent(this, CreateUserService.class);
        createUserService.setAction(CreateUserService.CREATE_USER_ACTION);
        startService(createUserService);
    }


    public class CreateUserReceiver extends BroadcastReceiver {

        public CreateUserReceiver() {
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            Participant participant = (Participant) intent.getSerializableExtra(CreateUserService.CREATE_USER_EXTRA);
            // Save User ID
            Util.sharedPref(context).edit().putInt(Util.SharedPreferenceUserId, participant.getId()).apply();
        }
    }
}