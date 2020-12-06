package com.ift604.bingo.service;

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.ift604.bingo.dal.dao.GameDatamapper;
import com.ift604.bingo.dal.dao.LobbyDatamapper;
import com.ift604.bingo.fel.game.GameActivity;
import com.ift604.bingo.model.Boule;
import com.ift604.bingo.model.Card;
import com.ift604.bingo.model.Participant;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    public static final String GENERATED_CARD_EXTRA = "GENERATED_CARD_EXTRA";
    public static final String PLAYER_MOVED_ACTION = "PLAYER_MOVED_ACTION";
    public static final String WIN_GAME_PUSH_ACTION = "WIN_GAME_ACTION";
    public static final String NEXT_BALL_ACTION = "NEXT_BALL_ACTION";
    private final String TYPE = "type";
    private final String PAYLOAD = "payload";

    private final String CARD = "carte";
    private final String WIN_GAME = "winner";
    private final String NEXT_BALL = "nextBoule";
    private final String LOBBY_ID = "lobbyId";
    private final String ADDED_PLAYER = "addedPlayer";
    private final String REMOVED_PLAYER = "removedPlayer";
    public final static String ADDED_PLAYER_EXTRA = "ADDED_PLAYER_EXTRA";
    public static final String NEXT_BALL_EXTRA = "NEXT_BALL_EXTRA";
    public final static String REMOVED_PLAYER_EXTRA = "REMOVED_PLAYER_EXTRA";
    public final static String WINNER_EXTRA = "WINNER_EXTRA";

    @Override
    public void onMessageReceived(final RemoteMessage remoteMessage) {
        Log.d("info", remoteMessage.getData().get("type"));
        Log.d("info", remoteMessage.getData().get("payload"));

        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            public void run() {
                String type = remoteMessage.getData().get(TYPE);
                String data = remoteMessage.getData().get(PAYLOAD);
                switch (type) {
                    case CARD:
                        Card card = GameDatamapper.buildCard(data);
                        startGameActivity(card);
                        break;
                    case ADDED_PLAYER:
                        Participant addedPlayer = LobbyDatamapper.buildParticipant(data);
                        playerAddedBroadcast(addedPlayer);
                        break;
                    case REMOVED_PLAYER:
                        Participant removedPlayer = LobbyDatamapper.buildParticipant(data);
                        playerRemovedBroadcast(removedPlayer);
                        break;
                    case WIN_GAME:
                        Participant winner = LobbyDatamapper.buildParticipant(data);
                        playerWonBroadcast(winner);
                        break;
                    case NEXT_BALL:
                        Boule ball = GameDatamapper.buildBoule(data);
                        nextBallBroadcast(ball);
                        break;
                }
            }
        });
    }

    private void nextBallBroadcast(Boule ball) {
        Intent i = new Intent();
        i.setAction(NEXT_BALL_ACTION);
        i.putExtra(NEXT_BALL_EXTRA, ball);
        LocalBroadcastManager.getInstance(this).sendBroadcast(i);
    }

    //TODO SI ON A DU TEMPS, BOUGER CA DANS STARTGAMEACTIVITY AVEC UN BROADCAST
    private void startGameActivity(Card card) {
        Intent gameIntent = new Intent(this, GameActivity.class);
        gameIntent.putExtra(GENERATED_CARD_EXTRA, card);
        gameIntent.setFlags(FLAG_ACTIVITY_NEW_TASK);
        startActivity(gameIntent);
    }

    private void playerAddedBroadcast(Participant p) {
        Intent i = new Intent();
        i.setAction(PLAYER_MOVED_ACTION);
        i.putExtra(ADDED_PLAYER_EXTRA, p);
        LocalBroadcastManager.getInstance(this).sendBroadcast(i);
    }

    private void playerRemovedBroadcast(Participant p) {
        Intent i = new Intent();
        i.setAction(PLAYER_MOVED_ACTION);
        i.putExtra(REMOVED_PLAYER_EXTRA, p);
        LocalBroadcastManager.getInstance(this).sendBroadcast(i);
    }

    private void playerWonBroadcast(Participant p) {
        Intent i = new Intent();
        i.setAction(WIN_GAME_PUSH_ACTION);
        i.putExtra(WINNER_EXTRA, p);
        LocalBroadcastManager.getInstance(this).sendBroadcast(i);
    }

}
