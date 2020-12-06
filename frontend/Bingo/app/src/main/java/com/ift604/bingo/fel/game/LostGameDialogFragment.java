package com.ift604.bingo.fel.game;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.ift604.bingo.R;
import com.ift604.bingo.fel.lobby.MainActivity;
import com.ift604.bingo.model.Participant;
import com.ift604.bingo.util.Util;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LostGameDialogFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LostGameDialogFragment extends DialogFragment {
    private static final String PARTICIPANT_WINNER = "PARTICIPANT_WINNER";
    private Participant winner;

    public LostGameDialogFragment() {
        // Required empty public constructor
    }

    public static LostGameDialogFragment newInstance(Participant participantWinner) {
        LostGameDialogFragment fragment = new LostGameDialogFragment();
        Bundle args = new Bundle();
        args.putSerializable(PARTICIPANT_WINNER, participantWinner);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            winner = (Participant) getArguments().getSerializable(PARTICIPANT_WINNER);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_lost_game_dialog, container, false);
        TextView winnerText = view.findViewById(R.id.winner_dialog_text);
        winnerText.setText(Util.formatPlayerName(winner));
        Button confirmation = view.findViewById(R.id.lose_dialog_confirmation_button);
        confirmation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MainActivity.class);
                v.getContext().startActivity(intent);
            }
        });

        return view;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstance) {
        Dialog d = super.onCreateDialog(savedInstance);
        d.setCanceledOnTouchOutside(false);
        return d;
    }
}