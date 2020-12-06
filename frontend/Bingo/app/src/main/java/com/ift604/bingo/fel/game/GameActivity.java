package com.ift604.bingo.fel.game;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentManager;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.jinatonic.confetti.CommonConfetti;
import com.ift604.bingo.R;
import com.ift604.bingo.controller.GameController;
import com.ift604.bingo.controller.IListener;
import com.ift604.bingo.model.Card;
import com.ift604.bingo.model.Coordinate;
import com.ift604.bingo.model.Participant;
import com.ift604.bingo.service.MyFirebaseMessagingService;
import com.ift604.bingo.service.WinGameService;
import com.ift604.bingo.util.Util;


public class GameActivity extends AppCompatActivity implements IListener {

    private FrameLayout playerCardFrameLayout;
    private LinearLayout previousNumbersLinearLayout;
    private LinearLayout mainGameLayout;
    private LinearLayout previousNumberLayout;

    private GameActivity gameActivity;

    private TextView previousNumbers;
    private RecyclerView previousRecyclerView;

    private Intent winGameService;
    private WinGameReceiver winGameReceiver;

    private int lobbyId;
    public GameController gameController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gameActivity = this;
        setContentView(R.layout.activity_game);
        Card card = (Card) getIntent().getSerializableExtra(MyFirebaseMessagingService.GENERATED_CARD_EXTRA);

        gameController = new GameController(card);
        lobbyId = card.getLobbyId();

        playerCardFrameLayout = findViewById(R.id.player_card_frame_layout);
        previousNumbersLinearLayout = findViewById(R.id.previous_numbers_frame_layout);
        previousNumbers = findViewById(R.id.previous_number_text);
        Button button = findViewById(R.id.bingo_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startWinService();
                registerWinService();
            }
        });

        initSwitchableLayout();
        getSupportFragmentManager().beginTransaction().add(playerCardFrameLayout.getId(), CardFragment.newInstance(card, gameActivity), "playerCardFrameLayout").commit();

        IntentFilter i = new IntentFilter(MyFirebaseMessagingService.WIN_GAME_PUSH_ACTION);
        LocalBroadcastManager.getInstance(this).registerReceiver(new WinGamePushReceiver(), i);

        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            handleLandscapeOrientation();
        } else {
            handlePortraitOrientation();
        }
    }

    private void initSwitchableLayout() {
        mainGameLayout = findViewById(R.id.game_activity_layout);
        previousNumberLayout = findViewById(R.id.previous_number_layout);
        previousRecyclerView = findViewById(R.id.previous_number_recycler_view);
    }

    private void startWinService() {
        winGameService = new Intent(this, WinGameService.class);
        winGameService.setAction(WinGameService.WIN_GAME_ACTION);
        winGameService.putExtra(WinGameService.LOBBY_ID_EXTRA, gameController.getPlayerCard().getLobbyId());
        winGameService.putExtra(WinGameService.WINNER_ID_EXTRA, Util.getConnectedUserId(this));
        winGameService.putExtra(WinGameService.CARD_ID_EXTRA, gameController.getPlayerCard().getId());
        startService(winGameService);
    }

    public int getLobbyId() {
        return lobbyId;
    }

    private void registerWinService() {
        winGameReceiver = new WinGameReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(MyFirebaseMessagingService.WIN_GAME_PUSH_ACTION);
        this.registerReceiver(winGameReceiver, intentFilter);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            handleLandscapeOrientation();
        } else {
            handlePortraitOrientation();
        }
    }

    private void handlePortraitOrientation() {
        // Main Layout
        mainGameLayout.setOrientation(LinearLayout.VERTICAL);
        mainGameLayout.setGravity(Gravity.CENTER_HORIZONTAL);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, 0);
        lp.weight = 0.20f;
        lp.gravity = Gravity.CENTER_HORIZONTAL;
        previousNumberLayout.setLayoutParams(lp);
        previousNumberLayout.setGravity(Gravity.CENTER_HORIZONTAL);

        // Player Card Frame Layout
        LinearLayout.LayoutParams fp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0);
        fp.weight = 0.7f;
        fp.gravity = Gravity.CENTER_HORIZONTAL;
        playerCardFrameLayout.setLayoutParams(fp);
        playerCardFrameLayout.setForegroundGravity(Gravity.CENTER_HORIZONTAL);
        previousNumberLayout.setOrientation(LinearLayout.VERTICAL);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        previousRecyclerView.setLayoutManager(linearLayoutManager);
    }

    @Override
    public void onDestroy() {
        if (winGameService != null) {
            stopService(winGameService);
            unregisterReceiver(winGameReceiver);
        }
        super.onDestroy();
    }

    private void handleLandscapeOrientation() {
        mainGameLayout.setOrientation(LinearLayout.HORIZONTAL);
        //mainGameLayout.setLayoutParams(mp);
        mainGameLayout.setGravity(Gravity.CENTER_HORIZONTAL);
        //Previous numbers layout
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.weight = 0.25f;
        lp.gravity = Gravity.CENTER_HORIZONTAL;
        previousNumberLayout.setLayoutParams(lp);
        //previousNumberLayout.setOrientation(LinearLayout.HORIZONTAL);
        previousNumberLayout.setGravity(Gravity.CENTER_HORIZONTAL);

        // Player Card Frame Layout
        LinearLayout.LayoutParams fp = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT);
        fp.weight = 0.7f;
        fp.gravity = Gravity.CENTER_HORIZONTAL;
        playerCardFrameLayout.setLayoutParams(fp);
        playerCardFrameLayout.setForegroundGravity(Gravity.CENTER_HORIZONTAL);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        previousRecyclerView.setLayoutManager(linearLayoutManager);

    }

    // Confirm Exit
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //Display confirmation here, finish() activity.
            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder.setMessage(R.string.exit_game_confirmation)
                    .setCancelable(false)
                    .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            GameActivity.this.finish();
                        }
                    }).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    return;
                }
            });
            AlertDialog alert = builder.create();
            alert.show();
            return true;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }

    @Override
    public void onBoxClick(Coordinate coordinate) {
        gameController.markBoxForPlayer(coordinate);
        boolean bingo = gameController.verifyIfBingo(coordinate);
        findViewById(R.id.bingo_button).setEnabled(bingo);
    }

    void showWinnerDialog() {
        CommonConfetti.rainingConfetti((ConstraintLayout) findViewById(R.id.main_screen), new int[]{Color.GREEN, Color.YELLOW, Color.RED, Color.LTGRAY})
                .infinite();
        FragmentManager fm = getSupportFragmentManager();
        WinDialogFragment winDialogFragment = WinDialogFragment.newInstance();
        winDialogFragment.show(fm, "");
    }

    void showLoserDialog(Participant winner) {
        if (winner.getId() != Util.getConnectedUserId(this)) {
            FragmentManager fm = getSupportFragmentManager();
            LostGameDialogFragment lostDialogFragment = LostGameDialogFragment.newInstance(winner);
            lostDialogFragment.show(fm, "");
        }
    }

    void showInvalidCard() {
        Toast.makeText(this, R.string.invalid_bingo, Toast.LENGTH_SHORT).show();
    }

    private class WinGamePushReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Participant participantWinner = (Participant) intent.getSerializableExtra(MyFirebaseMessagingService.WINNER_EXTRA);
            showLoserDialog(participantWinner);
        }
    }

    private class WinGameReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Boolean isValid = intent.getBooleanExtra(WinGameService.IS_VALID_EXTRA, false);
            if (isValid)
                showWinnerDialog();
            else
                showInvalidCard();

        }
    }
}