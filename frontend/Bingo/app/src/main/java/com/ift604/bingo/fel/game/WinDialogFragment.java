package com.ift604.bingo.fel.game;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.ift604.bingo.R;
import com.ift604.bingo.fel.lobby.MainActivity;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WinDialogFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WinDialogFragment extends DialogFragment {
    public WinDialogFragment() {
        // Required empty public constructor
    }

    public static WinDialogFragment newInstance() {
        WinDialogFragment fragment = new WinDialogFragment();
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstance) {
        Dialog d = super.onCreateDialog(savedInstance);
        d.setCanceledOnTouchOutside(false);
        return d;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_win_dialog, container, false);
        Button confirmation = view.findViewById(R.id.win_dialog_confirmation_button);
        confirmation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                v.getContext().startActivity(intent);
            }
        });
        return view;
    }
}