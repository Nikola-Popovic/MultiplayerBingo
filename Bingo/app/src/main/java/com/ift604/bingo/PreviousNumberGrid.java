package com.ift604.bingo;

import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.ift604.bingo.model.Box;
import com.ift604.bingo.model.Card;
import com.ift604.bingo.model.Coordinate;
import com.ift604.bingo.util.Util;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PreviousNumberGrid#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PreviousNumberGrid extends DialogFragment {
    public PreviousNumberGrid() { }

    public static PreviousNumberGrid newInstance() {
        PreviousNumberGrid fragment = new PreviousNumberGrid();
        Bundle args = new Bundle();
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
        View view = inflater.inflate(R.layout.fragment_previous_number_grid, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        FrameLayout previousNumberFrameLayout = (FrameLayout) view.findViewById(R.id.previous_number_frame_layout);
        Card card = generateFullGrid();
        getChildFragmentManager().beginTransaction().add(previousNumberFrameLayout.getId(), CardFragment.newInstance(card, null), "previous number").commit();
        super.onViewCreated(view, savedInstanceState);
    }

    private Card generateFullGrid() {
        final int amountOfRowInBingo = 15;
        ArrayList<String> bingo = Util.bingo;
        return new Card(createColumn(amountOfRowInBingo, bingo), Util.bingo.size(), amountOfRowInBingo);
    }

    private HashMap<Coordinate, Box> createColumn(int amountOfRowInBingo, ArrayList<String> bingo) {
        HashMap<Coordinate, Box> map = new HashMap();
        for (int col = 0; col < bingo.size(); col++) {
            map.putAll(createRow(amountOfRowInBingo, col));
        }
        return map;
    }

    private HashMap<Coordinate, Box> createRow(int amountOfRowInBingo, int col) {
        HashMap<Coordinate, Box> map = new HashMap<>();
        for (int row = 1; row <= amountOfRowInBingo; row++) {
            map.put(new Coordinate(col, row - 1), new Box(String.valueOf(col * amountOfRowInBingo + row), false));
        }
        return map;
    }
}