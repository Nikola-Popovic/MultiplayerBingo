package com.ift604.bingo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ift604.bingo.model.Participant;

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

    public static WaitLobbyParticipantListFragment newInstance(ArrayList<Participant> participants) {
        WaitLobbyParticipantListFragment fragment = new WaitLobbyParticipantListFragment();
        Bundle args = new Bundle();
        args.putSerializable("PARTICIPANTS", participants);
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
        participants = (ArrayList<Participant>) getArguments().getSerializable("PARTICIPANTS");
        View view = inflater.inflate(R.layout.fragment_wait_lobby_participant_list, container, false);
        waitLobbyRecyclerView = view.findViewById(R.id.wait_lobby_participant_recycler_view);
        waitLobbyRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        adapter = new WaitLobbyAdapter(participants);
        waitLobbyRecyclerView.setAdapter(adapter);
        return view;
    }

    public void updateRecyclerView() {
        adapter.notifyItemRangeChanged(position, participants.size());
        adapter.notifyDataSetChanged();
    }
}