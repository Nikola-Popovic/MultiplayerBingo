package com.ift604.bingo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ift604.bingo.model.Lobby;
import com.ift604.bingo.service.CreateLobbyService;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CreateLobbyFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreateLobbyFragment extends DialogFragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CreateLobbyFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static CreateLobbyFragment newInstance(int hostId) {
        CreateLobbyFragment fragment = new CreateLobbyFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_lobby, container, false);
        final DialogFragment dialog = this;
        view.findViewById(R.id.create_lobby_create_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startCreateLobbyService(dialog);
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

    private void startCreateLobbyService(DialogFragment dialogFragment) {
        Intent createLobbyService = new Intent(dialogFragment.getActivity(), CreateLobbyService.class);
        dialogFragment.getActivity().startService(createLobbyService);
    }

    private void registerCreateLobbyReceiver(DialogFragment dialogFragment) {
        CreateLobbyReceiver receiver = new CreateLobbyReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("CREATE_LOBBY_RECEIVER");
        dialogFragment.requireActivity().registerReceiver(receiver, intentFilter);
    }

    private void startWaitLobbyFragment(Lobby lobby) {
        Intent lobbyIntent = new Intent(getActivity(), WaitLobbyActivity.class);
        lobbyIntent.putExtra("LOBBY", lobby);
        startActivity(lobbyIntent);
    }


    public class CreateLobbyReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Lobby lobby  = (Lobby) intent.getSerializableExtra(CreateLobbyService.CREATED_LOBBY_EXTRA);
            startWaitLobbyFragment(lobby);
        }
    }
}