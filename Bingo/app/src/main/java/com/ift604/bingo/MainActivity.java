package com.ift604.bingo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.ift604.bingo.model.Card;

public class MainActivity extends AppCompatActivity {

    FrameLayout playerCard;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //TODO THIS WILL BE CALLED QUAND LA GAME VA ETRE LANCÉ
        Intent generateCardService = new Intent(this, PlayerCardService.class);
        setContentView(R.layout.activity_main);
        Button previousNumberButton = findViewById(R.id.previous_number_button);
        previousNumberButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getSupportFragmentManager();
                PreviousNumberGrid previousNumberGrid = PreviousNumberGrid.newInstance();
                previousNumberGrid.show(fm, "");
            }
        });
        playerCard = findViewById(R.id.player_card_frame_layout);
        startService(generateCardService);
        registerPlayerCardReceiver();
        ImageView c = findViewById(R.id.player_game_background_image);
        c.setImageResource(R.drawable.player_game_background);
    }

    private void registerPlayerCardReceiver() {
        PlayerCardReceiver receiver = new PlayerCardReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("RECEIVE");
        registerReceiver(receiver, intentFilter);
    }

    public class PlayerCardReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Card card = (Card) intent.getBundleExtra("playerCard").getSerializable("generatedCard");
            getSupportFragmentManager().beginTransaction().add(playerCard.getId(), CardFragment.newInstance(card), "un autre joli tag").commit();
        }
    }
}