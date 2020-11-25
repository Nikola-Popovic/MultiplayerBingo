package com.ift604.bingo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.ift604.bingo.model.Lobby;
import com.ift604.bingo.service.JoinLobbyService;
import com.ift604.bingo.util.Util;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link JoinLobbyFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class JoinLobbyFragment extends DialogFragment {
    Intent joinLobbyService;
    JoinLobbyReceiver joinLobbyReceiver;
    public JoinLobbyFragment() {
    }

    public static JoinLobbyFragment newInstance() {
        JoinLobbyFragment fragment = new JoinLobbyFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_join_lobby, container, false);
        final DialogFragment dialog = this;
        final EditText lobbyIdEditText = view.findViewById(R.id.join_lobby_id_value);
        view.findViewById(R.id.join_lobby_create_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startJoinLobbyService(Integer.valueOf(lobbyIdEditText.getText().toString()));
                registerJoinLobbyReceiver();
            }
        });

        view.findViewById(R.id.join_lobby_cancel_button).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        return view;
    }

    private void startWaitLobbyFragment(Lobby lobby) {
        Intent lobbyIntent = new Intent(getActivity(), WaitLobbyActivity.class);
        lobbyIntent.putExtra("LOBBY", lobby);
        startActivity(lobbyIntent);
    }

    private void startJoinLobbyService(int lobbyId) {
        joinLobbyService = new Intent(getActivity(), JoinLobbyService.class);
        joinLobbyService.putExtra(JoinLobbyService.LOBBY_ID, lobbyId);
        joinLobbyService.putExtra(JoinLobbyService.USER_ID, Util.getConnectedUserId(getContext()));
        joinLobbyService.setAction(JoinLobbyService.JOIN_LOBBY_ACTION);
        getActivity().startService(joinLobbyService);

    }

    private void registerJoinLobbyReceiver() {
        joinLobbyReceiver = new JoinLobbyReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(JoinLobbyService.JOIN_LOBBY_ACTION);
        requireActivity().registerReceiver(joinLobbyReceiver, intentFilter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().stopService(joinLobbyService);
        getActivity().unregisterReceiver(joinLobbyReceiver);
    }

    public class JoinLobbyReceiver extends BroadcastReceiver {

        public JoinLobbyReceiver() {

        }

        @Override
        public void onReceive(Context context, Intent intent) {
            Lobby lobby = (Lobby) intent.getSerializableExtra("JOINED_LOBBY");
            startWaitLobbyFragment(lobby);
        }
    }
}