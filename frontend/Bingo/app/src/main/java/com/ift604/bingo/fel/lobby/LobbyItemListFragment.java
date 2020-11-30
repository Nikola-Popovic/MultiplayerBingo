package com.ift604.bingo.fel.lobby;

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
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.ift604.bingo.R;
import com.ift604.bingo.model.Lobby;
import com.ift604.bingo.service.FindLobbyNearMeService;

import java.util.ArrayList;

public class LobbyItemListFragment extends Fragment {

    int position;
    RecyclerView lobbyRecyclerView;
    ArrayList<Lobby> lobbies = new ArrayList<>();
    LobbyItemAdapter adapter;


    SwipeRefreshLayout swipeRefreshLayout;

    public LobbyItemListFragment() {

    }

    public LobbyItemListFragment(int position) {
        this.position = position;
    }

    public static LobbyItemListFragment newInstance() {
        LobbyItemListFragment fragment = new LobbyItemListFragment();
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
        View view =  inflater.inflate(R.layout.fragment_lobby_item_list, container, false);
        this.lobbyRecyclerView = view.findViewById(R.id.lobby_recycle_view);
        this.swipeRefreshLayout = view.findViewById(R.id.lobby_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //TODO REFRESH
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        lobbyRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        this.adapter = new LobbyItemAdapter(lobbies);

        this.lobbyRecyclerView.setAdapter(adapter);
        Intent lobbiesService = new Intent(getActivity(), FindLobbyNearMeService.class);
        getActivity().startService(lobbiesService);
        registerResponseReceiver();
        return view;
    }

    private void registerResponseReceiver() {
        LobbyResponseReceiver receiver = new LobbyResponseReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(FindLobbyNearMeService.GET_LOBBY_BY_LOCATION_ACTION);
        requireActivity().registerReceiver(receiver, intentFilter);
    }


    public class LobbyResponseReceiver extends BroadcastReceiver {

        public LobbyResponseReceiver() {
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle b = intent.getBundleExtra(FindLobbyNearMeService.LOBBY_NEAR_ME_EXTRA);
            lobbies = (ArrayList<Lobby>) b.getSerializable(FindLobbyNearMeService.LOBBY_NEAR_ME_BUNDLE);
            adapter.setLobbies(lobbies);
            adapter.notifyItemRangeChanged(position, lobbies.size());
            adapter.notifyDataSetChanged();
            //TODO REFRESH
      //      swipeRefreshLayout.setRefreshing(false);
        }
    }
}