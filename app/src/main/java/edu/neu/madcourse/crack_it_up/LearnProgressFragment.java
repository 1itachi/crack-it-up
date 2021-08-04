package edu.neu.madcourse.crack_it_up;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LearnProgressFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LearnProgressFragment extends Fragment {
    public LearnProgressFragment() {
        // Required empty public constructor
    }

    public static LearnProgressFragment newInstance() {
        LearnProgressFragment fragment = new LearnProgressFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_learn, container, false);
    }
}