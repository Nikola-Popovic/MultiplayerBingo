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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link JoinLobbyFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class JoinLobbyFragment extends DialogFragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    public JoinLobbyFragment() {
        // Required empty public constructor
    }
    // TODO: Rename and change types and number of parameters
    public static JoinLobbyFragment newInstance() {
        JoinLobbyFragment fragment = new JoinLobbyFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
        //    mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_join_lobby, container, false);
        final DialogFragment dialog = this;
        final EditText lobbyIdEditText = view.findViewById(R.id.join_lobby_id_value);
        view.findViewById(R.id.join_lobby_create_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO ADD LE PARTICIPANT
                Intent intent = new Intent(v.getContext(), WaitLobbyActivity.class);
                intent.putExtra("LOBBY_ID", Integer.valueOf(lobbyIdEditText.getText().toString()));
                v.getContext().startActivity(intent);
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
    //TODO THIS METHOD IS DUPLICATED WITH THE ONE IN CREATELOBBY
    private void startWaitLobbyFragment(Lobby lobby) {
        Intent lobbyIntent = new Intent(getActivity(), WaitLobbyActivity.class);
        lobbyIntent.putExtra("LOBBY", lobby);
        startActivity(lobbyIntent);
    }

    private void registerJoinLobbyReceiver() {
        JoinLobbyReceiver receiver = new JoinLobbyReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("LOBBY");
        requireActivity().registerReceiver(receiver, intentFilter);
    }

    public class JoinLobbyReceiver extends BroadcastReceiver {

        public JoinLobbyReceiver() {

        }

        @Override
        public void onReceive(Context context, Intent intent) {
            Lobby lobby  = (Lobby) intent.getSerializableExtra("JOINED_LOBBY");
            startWaitLobbyFragment(lobby);
        }
    }
}