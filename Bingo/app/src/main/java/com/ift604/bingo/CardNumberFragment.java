package com.ift604.bingo;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CardNumberFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CardNumberFragment extends Fragment {
    private static final String BOX_NUMBER = "boxNumber";


    public CardNumberFragment() {
        // Required empty public constructor
    }

    public static CardNumberFragment newInstance(Integer number) {
        CardNumberFragment fragment = new CardNumberFragment();
        Bundle args = new Bundle();
        args.putInt(BOX_NUMBER, number);
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
        View view = inflater.inflate(R.layout.fragment_card_number, container, false);
        final TextView b = view.findViewById(R.id.box_button);
        b.setText(String.valueOf(this.getArguments().getInt(BOX_NUMBER)));
        b.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                markBox(b);
            }
        });
        return view;
    }

    private void markBox(TextView b) {
        b.setBackgroundColor(Color.BLUE);
    }
}