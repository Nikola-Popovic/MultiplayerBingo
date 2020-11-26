package com.ift604.bingo.fel.waitlobby;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.ift604.bingo.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WaitLobbyParticipantItemFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WaitLobbyParticipantItemFragment extends Fragment {
    public WaitLobbyParticipantItemFragment() {
    }

    public static WaitLobbyParticipantItemFragment newInstance(String param1, String param2) {
        WaitLobbyParticipantItemFragment fragment = new WaitLobbyParticipantItemFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_wait_lobby_participant_item, container, false);
    }
}