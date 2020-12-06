package com.ift604.bingo.fel.lobby;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.ift604.bingo.R;
import com.ift604.bingo.fel.waitlobby.WaitLobbyActivity;
import com.ift604.bingo.service.JoinLobbyService;
import com.ift604.bingo.util.Util;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link JoinLobbyFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class JoinLobbyFragment extends DialogFragment {
    private Intent joinLobbyService;
    private JoinLobbyReceiver joinLobbyReceiver;

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
                joinLobbyAction(lobbyIdEditText, dialog);
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

    private void joinLobbyAction(EditText lobbyIdEditText, DialogFragment dialog) {
        String textValue = lobbyIdEditText.getText().toString();
        if (validateNotEmpty(textValue)) {
            startJoinLobbyService(Integer.valueOf(lobbyIdEditText.getText().toString()));
            registerJoinLobbyReceiver();
        } else {
            Toast.makeText(dialog.getContext(), getResources().getString(R.string.error_empty_lobby_name), Toast.LENGTH_SHORT).show();
        }
    }

    private boolean validateNotEmpty(String textValue) {
        return textValue.length() > 0;
    }

    private void startWaitLobbyFragment(int lobbyId) {
        Intent lobbyIntent = new Intent(getActivity(), WaitLobbyActivity.class);
        lobbyIntent.putExtra(WaitLobbyActivity.LOBBY_ID, lobbyId);
        dismiss();
        startActivity(lobbyIntent);
    }

    private void showErrorJoiningLobby() {
        Toast.makeText(this.getContext(), R.string.error_joining, Toast.LENGTH_SHORT).show();
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
        if (joinLobbyService != null) {
            getActivity().stopService(joinLobbyService);
            getActivity().unregisterReceiver(joinLobbyReceiver);
        }
    }

    public class JoinLobbyReceiver extends BroadcastReceiver {

        public JoinLobbyReceiver() {

        }

        @Override
        public void onReceive(Context context, Intent intent) {
            int lobbyId = intent.getIntExtra(JoinLobbyService.LOBBY_ID, 0);
            boolean isSuccess = intent.getBooleanExtra(Util.IS_SUCCESS, false);
            if (isSuccess) {
                startWaitLobbyFragment(lobbyId);
            } else {
                showErrorJoiningLobby();
            }
        }
    }
}