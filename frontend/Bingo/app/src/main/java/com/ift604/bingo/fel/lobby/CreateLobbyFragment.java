package com.ift604.bingo.fel.lobby;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.fragment.app.DialogFragment;

import com.ift604.bingo.R;
import com.ift604.bingo.fel.waitlobby.WaitLobbyActivity;
import com.ift604.bingo.model.Lobby;
import com.ift604.bingo.service.CreateLobbyService;
import com.ift604.bingo.dal.LocationProvider;
import com.ift604.bingo.util.Util;

public class CreateLobbyFragment extends DialogFragment {
    Intent createLobbyService;
    CreateLobbyReceiver createLobbyReceiver;

    LocationProvider locationProvider;

    public CreateLobbyFragment() {
    }

    public static CreateLobbyFragment newInstance(LocationProvider locationProvider) {
        CreateLobbyFragment fragment = new CreateLobbyFragment();
        fragment.setLocationProvider(locationProvider);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_lobby, container, false);
        final EditText lobbyName = view.findViewById(R.id.create_lobby_name_value);
        final DialogFragment dialog = this;
        view.findViewById(R.id.create_lobby_create_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startCreateLobbyService(dialog, lobbyName.getText().toString());
                registerCreateLobbyReceiver(dialog);
            }
        });

        view.findViewById(R.id.create_lobby_cancel_button).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        return view;
    }

    private void startCreateLobbyService(DialogFragment dialogFragment, String lobbyName) {
        createLobbyService = new Intent(dialogFragment.getActivity(), CreateLobbyService.class);
        createLobbyService.putExtra(CreateLobbyService.LOBBY_NAME, lobbyName);
        createLobbyService.putExtra(CreateLobbyService.USER_ID, Util.getConnectedUserId(dialogFragment.getContext()));
        Location location = locationProvider.getLocation();
        createLobbyService.putExtra(CreateLobbyService.LONGITUDE, location.getLongitude());
        createLobbyService.putExtra(CreateLobbyService.LATITUDE, location.getLatitude());
        dialogFragment.getActivity().startService(createLobbyService);
    }

    private void registerCreateLobbyReceiver(DialogFragment dialogFragment) {
        createLobbyReceiver = new CreateLobbyReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(CreateLobbyService.CREATE_LOBBY_ACTION);
        dialogFragment.getActivity().registerReceiver(createLobbyReceiver, intentFilter);
    }

    private void startWaitLobbyFragment(Lobby lobby) {
        Intent lobbyIntent = new Intent(getActivity(), WaitLobbyActivity.class);
        lobbyIntent.putExtra(WaitLobbyActivity.LOBBY_ID, lobby.getId());
        startActivity(lobbyIntent);
    }

    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().stopService(createLobbyService);
        getActivity().unregisterReceiver(createLobbyReceiver);
    }

    private void setLocationProvider(LocationProvider locationProvider)
    {
        this.locationProvider = locationProvider;
    }

    public class CreateLobbyReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Lobby lobby = (Lobby) intent.getSerializableExtra(CreateLobbyService.CREATED_LOBBY_EXTRA);
            startWaitLobbyFragment(lobby);
        }
    }
}