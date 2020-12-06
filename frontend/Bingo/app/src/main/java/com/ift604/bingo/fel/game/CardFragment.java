package com.ift604.bingo.fel.game;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;

import androidx.fragment.app.Fragment;

import com.ift604.bingo.R;
import com.ift604.bingo.controller.IListener;
import com.ift604.bingo.model.Card;
import com.ift604.bingo.model.Coordinate;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CardFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CardFragment extends Fragment {

    private static final String CARD = "card";
    private static final String LISTENER = "listener";
    private IListener listener;
    public CardFragment() {
    }

    public static CardFragment newInstance(Card card, IListener listener) {
        CardFragment fragment = new CardFragment();
        Bundle args = new Bundle();
        args.putSerializable(CARD, card);
        args.putSerializable(LISTENER, listener);
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
        View cardFragment = inflater.inflate(R.layout.fragment_card, container, false);
        TableLayout table = cardFragment.findViewById(R.id.cardTable);

        Card card = (Card) getArguments().getSerializable(CARD);
        listener = (IListener) getArguments().getSerializable(LISTENER);
        createCard(table, card);
        return cardFragment;
    }

    private void createCard(TableLayout table, Card card) {
        int id = 123;
        createCardHeader(table, card, id);
        id++;
        createCardColumns(table, card, id);
    }


    private void createCardHeader(TableLayout table, Card card, int id) {
        for (int i = 0; i < card.getMaxX(); i++) {
            final ArrayList<String> bingo = new ArrayList<>(Arrays.asList("B", "I", "N", "G", "O"));
            TableRow tableRow = new TableRow(getContext());
            tableRow.setId(id);
            TableLayout.LayoutParams p = new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT, 1f);
            tableRow.setLayoutParams(p);
            getFragmentManager().beginTransaction().add(tableRow.getId(), CardHeaderFragment.newInstance(bingo.get(i)), "tableRow" + id).commit();
            table.addView(tableRow);
        }
    }

    private void createCardColumns(TableLayout table, Card card, int id) {
        for (int y = 0; y < card.getMaxY(); y++) {
            for (int x = 0; x < card.getMaxX(); x++) {
                TableRow tableRow = new TableRow(getContext());
                tableRow.setId(id);
                TableLayout.LayoutParams p = new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT);
                tableRow.setLayoutParams(p);
                Coordinate coordinate = new Coordinate(x, y);
                getFragmentManager().beginTransaction().add(tableRow.getId(), CardNumberFragment.newInstance(coordinate, card.getNumber().get(coordinate), listener), "tableRow" + id).commit();
                table.addView(tableRow);
            }
            id++;
        }
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}