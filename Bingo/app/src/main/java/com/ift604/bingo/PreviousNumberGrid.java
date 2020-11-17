package com.ift604.bingo;

import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.ift604.bingo.model.Card;
import com.ift604.bingo.model.Column;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PreviousNumberGrid#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PreviousNumberGrid extends DialogFragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String CARD = "card";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PreviousNumberGrid() {
        // Required empty public constructor
    }

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
        FrameLayout l = (FrameLayout) view.findViewById(R.id.prev);
        Card card = generateFullGrid();
        getChildFragmentManager().beginTransaction().add(l.getId(), CardFragment.newInstance(card), " ").commit();
        super.onViewCreated(view, savedInstanceState);
    }

    private Card generateFullGrid() {
        final int amountOfRowInBingo = 15;
        final ArrayList<String> bingo = new ArrayList<>(Arrays.asList("B", "I", "N", "G", "O"));
        ArrayList<Column> columns = new ArrayList();
//TODO EXTRACT METHODS
        for (int col = 1; col <= bingo.size(); col++) {
            ArrayList<Integer> numbers = new ArrayList<>();
            for (int row = 1; row <= amountOfRowInBingo; row++) {
                numbers.add(col * amountOfRowInBingo + row);
            }
            Column column = new Column(bingo.get(col - 1), numbers);
            columns.add(column);
        }
        return new Card(columns);
    }
}