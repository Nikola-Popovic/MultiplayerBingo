package com.ift604.bingo.fel.game;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.ift604.bingo.R;

public class PreviousNumberItemFragment extends Fragment {
    public PreviousNumberItemFragment() {
    }

    public static PreviousNumberItemFragment newInstance(){
        PreviousNumberItemFragment fragment = new PreviousNumberItemFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_previous_number_item, container, false);
    }
}
