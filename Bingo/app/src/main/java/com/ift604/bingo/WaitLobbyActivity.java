package com.ift604.bingo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.ift604.bingo.controller.GameController;
import com.ift604.bingo.model.Card;
import com.ift604.bingo.model.Coordinate;
import com.ift604.bingo.model.Lobby;

import java.util.ArrayList;

public class WaitLobbyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wait_lobby);
    }
}