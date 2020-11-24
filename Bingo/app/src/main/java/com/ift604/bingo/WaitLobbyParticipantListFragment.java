package com.ift604.bingo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ift604.bingo.model.Lobby;
import com.ift604.bingo.model.Participant;
import com.ift604.bingo.service.GetLobbyByAttributeService;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WaitLobbyParticipantListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WaitLobbyParticipantListFragment extends Fragment {
    private WaitLobbyAdapter adapter;
    private RecyclerView waitLobbyRecyclerView;
    private ArrayList<Participant> participants = new ArrayList<>();
    private int position;

    public WaitLobbyParticipantListFragment(int position) {
        this.position = position;
    }

    public WaitLobbyParticipantListFragment() {
    }

    public static WaitLobbyParticipantListFragment newInstance(int lobbyId) {
        WaitLobbyParticipantListFragment fragment = new WaitLobbyParticipantListFragment();
        Bundle args = new Bundle();
        args.putInt("LOBBY_ID", lobbyId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        int lobbyId = getArguments().getInt("LOBBY_ID");
        startGetLobbyService(lobbyId);
        registerGetLobbyReceiver();
        View view = inflater.inflate(R.layout.fragment_wait_lobby_participant_list, container, false);
        waitLobbyRecyclerView = view.findViewById(R.id.wait_lobby_participant_recycler_view);
        waitLobbyRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        adapter = new WaitLobbyAdapter(participants);
        waitLobbyRecyclerView.setAdapter(adapter);
        return view;
    }

    private void registerGetLobbyReceiver() {
        GetLobbyResponseReceiver receiver = new GetLobbyResponseReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(GetLobbyByAttributeService.GET_BY_ID_ACTION);
        getActivity().registerReceiver(receiver, intentFilter);
    }

    private void startGetLobbyService(int lobbyId) {
        Intent intent = new Intent(getActivity(), GetLobbyByAttributeService.class);
        intent.setAction(GetLobbyByAttributeService.GET_BY_ID_ACTION);
        intent.putExtra(GetLobbyByAttributeService.LOBBY_ID_PARAM, lobbyId);
        getActivity().startService(intent);
    }


    public class GetLobbyResponseReceiver extends BroadcastReceiver {

        public GetLobbyResponseReceiver() {
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            Lobby lobby = (Lobby) intent.getSerializableExtra(GetLobbyByAttributeService.LOBBY_EXTRA);
            adapter.setParticipants((ArrayList<Participant>) lobby.getParticipants());
            adapter.notifyItemRangeChanged(position, participants.size());
            adapter.notifyDataSetChanged();
        }
    }
}