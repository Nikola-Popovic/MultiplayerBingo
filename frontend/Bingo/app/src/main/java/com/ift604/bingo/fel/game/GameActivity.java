package com.ift604.bingo.fel.game;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ift604.bingo.R;
import com.ift604.bingo.controller.GameController;
import com.ift604.bingo.controller.IListener;
import com.ift604.bingo.model.Card;
import com.ift604.bingo.model.Coordinate;
import com.ift604.bingo.service.DrawnNumberService;
import com.ift604.bingo.service.PlayerCardService;

public class GameActivity extends AppCompatActivity implements IListener {

    FrameLayout playerCardFrameLayout;
    FrameLayout previousNumbersFrameLayout;
    public GameController gameController;
    GameActivity gameActivity;

    TextView previousNumbers;
    LinearLayout mainGameLayout;
    LinearLayout previousNumberLayout;
    RecyclerView previousRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gameActivity = this;
        Intent generateCardService = new Intent(this, PlayerCardService.class);
        Intent previousNumberService = new Intent(this, DrawnNumberService.class);
        setContentView(R.layout.activity_game);
        playerCardFrameLayout = findViewById(R.id.player_card_frame_layout);
        startService(generateCardService);
        startService(previousNumberService);
        registerPlayerCardReceiver();
        registerNewNumberReceiver();
        previousNumbersFrameLayout = findViewById(R.id.previous_numbers_frame_layout);
        previousNumbers = findViewById(R.id.previous_number_text);
        /*
         * Layouts to switch after a screen orientation change
         */
        mainGameLayout = findViewById(R.id.game_activity_layout);
        previousNumberLayout = findViewById(R.id.previous_number_layout);
        previousRecyclerView = findViewById(R.id.previous_number_recycler_view);
        int orientation = getResources().getConfiguration().orientation;

        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
           handleLandscapeOrientation();
        } else {
          handlePortraitOrientation();
        }

        //TODO WILL SUBSCRIBE HERE FOR WIN
        //TODO WHEN SOMEONE WIN, THE GAME ENDS FOR EVERYBODY

        //TODO WILL SUBSCRIBE HERE FOR NUMBER CALLED.
        //TODO WHEN NUMBER ARE CALLED, PREVIOUSNUMBERGRID SHOULD BE UPDATED
        //TODO WHEN NUMBER ARE CALLED, THE VALUE SHOULD BE SAVED
        //TODO WHEN NUMBER ARE CALLED, THE NUMBER SHOULD BE DISPLAYED
    }

    private void registerNewNumberReceiver() {
        PreviousNumberReceiver receiver = new PreviousNumberReceiver();
        IntentFilter intentFilter = new IntentFilter();
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

    public class PreviousNumberReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            handleLandscapeOrientation();
        } else {
            // Portrait
            handlePortraitOrientation();
        }
    }


    private void handlePortraitOrientation() {
        // Text field
        // Main Layout
        mainGameLayout.setOrientation(LinearLayout.VERTICAL);
        mainGameLayout.setGravity(Gravity.CENTER_HORIZONTAL);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, 0);
        lp.weight = 0.1f;
        lp.gravity = Gravity.CENTER_HORIZONTAL;
        previousNumberLayout.setLayoutParams(lp);
        previousNumberLayout.setGravity(Gravity.CENTER_HORIZONTAL);

        // Player Card Frame Layout
        LinearLayout.LayoutParams fp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0);
        fp.weight = 0.75f;
        fp.gravity = Gravity.CENTER_HORIZONTAL;
        playerCardFrameLayout.setLayoutParams(fp);
        playerCardFrameLayout.setForegroundGravity(Gravity.CENTER_HORIZONTAL);
        /** Todo: Change the orientation of recyclerview
        // Recycler view
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        previousRecyclerView.setLayoutManager(linearLayoutManager);
         **/
    }

    private void handleLandscapeOrientation() {
        // Text field
       //rotateTextSideways();
        // Main Layout
        //LinearLayout.LayoutParams mp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        //mp.gravity = Gravity.CENTER_HORIZONTAL;
        mainGameLayout.setOrientation(LinearLayout.HORIZONTAL);
        //mainGameLayout.setLayoutParams(mp);
        mainGameLayout.setGravity(Gravity.CENTER_HORIZONTAL);
        //Previous numbers layout
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.weight = 0.3f;
        lp.gravity = Gravity.CENTER_HORIZONTAL;
        previousNumberLayout.setLayoutParams(lp);
        previousNumberLayout.setGravity(Gravity.CENTER_HORIZONTAL);

        // Player Card Frame Layout
        LinearLayout.LayoutParams fp = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT);
        fp.weight = 0.7f;
        fp.gravity = Gravity.CENTER_HORIZONTAL;
        playerCardFrameLayout.setLayoutParams(fp);
        playerCardFrameLayout.setForegroundGravity(Gravity.CENTER_HORIZONTAL);
        /**
        // Recycler view
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        previousRecyclerView.setLayoutManager(linearLayoutManager);**/

    }

    // Confirm Exit
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //Display confirmation here, finish() activity.
            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder.setMessage("Are you sure you want to exit?")
                    .setCancelable(false)
                    .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            GameActivity.this.finish();
                        }
                    }).setNegativeButton(R.string.cancel,new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            return;
                        }});
            AlertDialog alert = builder.create();
            alert.show();
        }
        return super.onKeyDown(keyCode, event);
    }

    private void rotateTextSideways() {
        String text = previousNumbers.getText().toString();
        // Ajoute une new row pour chaque caractere
        String newText = text.replaceAll("(.{1})", "$1\n");
        previousNumbers.setText(newText);
    }

    private void resetPreviousNumberTextView() {
        previousNumbers.setText(R.string.previous_numbers);
    }

    @Override
    public void onBoxClick(Coordinate coordinate) {
        gameController.markBoxForPlayer(coordinate);
        if (gameController.verifyIfBingo(coordinate)) {
            findViewById(R.id.bingo_button).setEnabled(true);
        }
    }
}