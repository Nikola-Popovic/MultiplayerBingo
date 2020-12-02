package com.ift604.bingo.fel.game;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.ift604.bingo.R;
import com.ift604.bingo.controller.GameController;
import com.ift604.bingo.controller.IListener;
import com.ift604.bingo.model.Card;
import com.ift604.bingo.model.Coordinate;
import com.ift604.bingo.service.PlayerCardService;

public class GameActivity extends AppCompatActivity implements IListener {

    FrameLayout playerCardFrameLayout;
    public GameController gameController;
    GameActivity gameActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gameActivity = this;
        Intent generateCardService = new Intent(this, PlayerCardService.class);
        setContentView(R.layout.activity_game);
        Button previousNumberButton = findViewById(R.id.previous_number_button);
        previousNumberButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getSupportFragmentManager();
                PreviousNumberGrid previousNumberGrid = PreviousNumberGrid.newInstance();
                previousNumberGrid.show(fm, "");
            }
        });
        playerCardFrameLayout = findViewById(R.id.player_card_frame_layout);
        startService(generateCardService);
        registerPlayerCardReceiver();

        //TODO WILL SUBSCRIBE HERE FOR WIN
        //TODO WHEN SOMEONE WIN, THE GAME ENDS FOR EVERYBODY

        //TODO WILL SUBSCRIBE HERE FOR NUMBER CALLED.
        //TODO WHEN NUMBER ARE CALLED, PREVIOUSNUMBERGRID SHOULD BE UPDATED
        //TODO WHEN NUMBER ARE CALLED, THE VALUE SHOULD BE SAVED
        //TODO WHEN NUMBER ARE CALLED, THE NUMBER SHOULD BE DISPLAYED
    }

    private void registerPlayerCardReceiver() {
        PlayerCardReceiver receiver = new PlayerCardReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("PLAYER_CARD_RECEIVER");
        registerReceiver(receiver, intentFilter);
    }

    public class PlayerCardReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Card card = (Card) intent.getBundleExtra("playerCard").getSerializable("generatedCard");
            gameController = new GameController(card);
            getSupportFragmentManager().beginTransaction().add(playerCardFrameLayout.getId(), CardFragment.newInstance(card, gameActivity), "un autre joli tag").commit();
        }
    }

    @Override
    public void onBoxClick(Coordinate coordinate) {
        gameController.markBoxForPlayer(coordinate);
        if (gameController.verifyIfBingo(coordinate)) {
            findViewById(R.id.bingo_button).setEnabled(true);
        }
    }
}