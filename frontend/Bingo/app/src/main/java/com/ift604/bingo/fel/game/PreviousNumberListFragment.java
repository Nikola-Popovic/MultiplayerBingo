package com.ift604.bingo.fel.game;

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
import com.ift604.bingo.fel.decorator.HorizontalSpaceItemDecoration;
import com.ift604.bingo.service.MyFirebaseMessagingService;
import com.ift604.bingo.util.CollectionUtil;

import java.util.ArrayList;
import java.util.List;

public class PreviousNumberListFragment extends Fragment {

    final static int MAX_ITEM = 4;
    RecyclerView previousNumberRecyclerView;
    List<String> previousNumbers = new ArrayList<>();
    PreviousNumberAdapter adapter;

    public PreviousNumberListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_previous_number_list, container, false);
        this.previousNumberRecyclerView = view.findViewById(R.id.previous_number_recycler_view);
        previousNumberRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        previousNumberRecyclerView.addItemDecoration(new HorizontalSpaceItemDecoration((int) getResources().getDimension(R.dimen.default_padding)));
        this.adapter = new PreviousNumberAdapter(previousNumbers);

        this.previousNumberRecyclerView.setAdapter(adapter);


        IntentFilter i = new IntentFilter(MyFirebaseMessagingService.NEXT_BALL_ACTION);
        LocalBroadcastManager.getInstance(this.getContext()).registerReceiver(new PreviousNumberReceiver(), i);

        return view;
    }

    private void updatePreviousNumbers(String newNumber) {
        if(previousNumbers.size() >= MAX_ITEM) {
            previousNumbers.remove(0);
        }
        previousNumbers.add(newNumber);
    }

    public class PreviousNumberReceiver extends BroadcastReceiver {

        public PreviousNumberReceiver() {
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            String newNumber = (String) intent.getStringExtra(MyFirebaseMessagingService.NEXT_BALL_EXTRA);
            updatePreviousNumbers(newNumber);
            adapter.setPreviousNumbers(CollectionUtil.reverse(previousNumbers));
            adapter.notifyItemRangeChanged(0, MAX_ITEM);
            adapter.notifyDataSetChanged();
        }
    }
}
