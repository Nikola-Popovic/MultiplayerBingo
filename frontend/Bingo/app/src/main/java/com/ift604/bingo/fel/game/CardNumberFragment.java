package com.ift604.bingo.fel.game;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.ift604.bingo.R;
import com.ift604.bingo.controller.IListener;
import com.ift604.bingo.model.Box;
import com.ift604.bingo.model.Coordinate;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CardNumberFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CardNumberFragment extends Fragment {
    private static final String BOX = "box";
    private static final String COORDINATE = "coordinate";
    private static final String LISTENER = "listener";
    private Coordinate coordinate;
    private boolean isClicked = false;

    private IListener listener;

    public CardNumberFragment() {
        // Required empty public constructor
    }

    public static CardNumberFragment newInstance(Coordinate coordinate, Box box, IListener listener) {
        CardNumberFragment fragment = new CardNumberFragment();
        Bundle args = new Bundle();
        args.putSerializable(BOX, box);
        args.putSerializable(COORDINATE, coordinate);
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
        View view = inflater.inflate(R.layout.fragment_card_number, container, false);
        final TextView numberTextView = view.findViewById(R.id.box_button);

        Box box = (Box) this.getArguments().getSerializable(BOX);
        this.coordinate = (Coordinate) this.getArguments().getSerializable(COORDINATE);
        this.listener = (IListener) this.getArguments().getSerializable(LISTENER);
        numberTextView.setText(box.getNumber());
        numberTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                markBox(numberTextView);
            }
        });
        return view;
    }

    private void markBox(TextView b) {
        isClicked = !isClicked;
        int drawable = isClicked ? R.drawable.box_number_selected : R.drawable.box_number;
        b.setBackground(ContextCompat.getDrawable(getContext(), drawable));
        listener.onBoxClick(coordinate);
    }
}