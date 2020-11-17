package com.ift604.bingo;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.TableLayout;
import android.widget.TableRow;

import com.ift604.bingo.model.Card;
import com.ift604.bingo.model.Column;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CardFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CardFragment extends Fragment {

    private static final String CARD = "card";

    public CardFragment() {
        // Required empty public constructor
    }

    public static CardFragment newInstance(Card card) {
        CardFragment fragment = new CardFragment();
        Bundle args = new Bundle();
        args.putSerializable(CARD, card);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Card card = DummyCard.generateDummyCard();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View cardFragment = inflater.inflate(R.layout.fragment_card, container, false);
        TableLayout table = cardFragment.findViewById(R.id.cardTable);

        Card card = (Card) getArguments().getSerializable(CARD);
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
        for (Column col : card.getColumns()) {
            TableRow row = new TableRow(getContext());
            row.setId(id);
            TableLayout.LayoutParams p = new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT, 1f);
            row.setLayoutParams(p);
            getFragmentManager().beginTransaction().add(row.getId(), CardHeaderFragment.newInstance(col.getLetter()), "joli tag" + id).commit();
            table.addView(row);
        }
    }

    private void createCardColumns(TableLayout table, Card card, int id) {
        for (int cardColumnIndex = 0; cardColumnIndex < card.getColumns().get(0).getNumber().size(); cardColumnIndex++) {
            TableRow row = createCardRows(card, id, cardColumnIndex);
            table.addView(row);
            id++;
        }
    }

    private TableRow createCardRows(Card card, int id, int cardColumnIndex) {
        TableRow row = new TableRow(getContext());
        row.setId(id);
        TableLayout.LayoutParams p = new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT);
        row.setLayoutParams(p);
        for (int cardRowIndex = 0; cardRowIndex < card.getColumns().size(); cardRowIndex++) {
            Integer number = card.getColumns().get(cardRowIndex).getNumber().get(cardColumnIndex);
            getFragmentManager().beginTransaction().add(row.getId(), CardNumberFragment.newInstance(number), "joli tag" + id).commit();
        }
        return row;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}