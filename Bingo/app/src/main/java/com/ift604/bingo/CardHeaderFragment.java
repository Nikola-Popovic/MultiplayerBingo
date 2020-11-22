package com.ift604.bingo;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CardHeaderFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CardHeaderFragment extends Fragment {
    private static final String HEADER_LETTER = "headerLetter";
    public CardHeaderFragment() { }

    public static CardHeaderFragment newInstance(String headerLetter) {
        CardHeaderFragment fragment = new CardHeaderFragment();
        Bundle args = new Bundle();
        args.putString(HEADER_LETTER, headerLetter);
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
        View view = inflater.inflate(R.layout.fragment_card_header, container, false);
        TextView b = view.findViewById(R.id.header_letter_button);
        b.setText(String.valueOf(this.getArguments().getString(HEADER_LETTER)));
        return view;
    }
}