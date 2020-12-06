package com.ift604.bingo.fel.waitlobby;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ift604.bingo.R;
import com.ift604.bingo.model.Participant;
import com.ift604.bingo.service.MyFirebaseMessagingService;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WaitLobbyParticipantListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WaitLobbyParticipantListFragment extends Fragment {
    private static String PARTICIPANTS_ARG = "PARTICIPANTS";
    private WaitLobbyAdapter adapter;
    private RecyclerView waitLobbyRecyclerView;
    private ArrayList<Participant> participants = new ArrayList<>();
    private int position;

    public WaitLobbyParticipantListFragment(int position) {
        this.position = position;
    }

    public WaitLobbyParticipantListFragment() {
    }

    public static WaitLobbyParticipantListFragment newInstance(ArrayList<Participant> participants) {
        WaitLobbyParticipantListFragment fragment = new WaitLobbyParticipantListFragment();
        Bundle args = new Bundle();
        args.putSerializable(PARTICIPANTS_ARG, participants);
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
        participants = (ArrayList<Participant>) getArguments().getSerializable(PARTICIPANTS_ARG);
        View view = inflater.inflate(R.layout.fragment_wait_lobby_participant_list, container, false);
        waitLobbyRecyclerView = view.findViewById(R.id.wait_lobby_participant_recycler_view);
        waitLobbyRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        adapter = new WaitLobbyAdapter(participants);
        waitLobbyRecyclerView.setAdapter(adapter);

        IntentFilter i = new IntentFilter(MyFirebaseMessagingService.PLAYER_MOVED_ACTION);
        LocalBroadcastManager.getInstance(this.getContext()).registerReceiver(new PlayerMovedReceiver(), i);
        return view;
    }

    public void updateRecyclerView() {
        adapter.notifyItemRangeChanged(position, participants.size());
        adapter.notifyDataSetChanged();
    }

    private class  PlayerMovedReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Participant addedParticipant = (Participant) intent.getSerializableExtra(MyFirebaseMessagingService.ADDED_PLAYER_EXTRA);
            Participant removedParticipant = (Participant) intent.getSerializableExtra(MyFirebaseMessagingService.REMOVED_PLAYER_EXTRA);

            if(addedParticipant != null && !participants.contains(addedParticipant)) {
                participants.add(addedParticipant);
            }

            if(removedParticipant != null) {
                participants.remove(removedParticipant);
            }
            updateRecyclerView();
        }
    }
}