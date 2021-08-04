package edu.neu.madcourse.crack_it_up;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A simple {@link Fragment} subclass.
 */
public class BehavioralProgressFragment extends Fragment {
    public BehavioralProgressFragment() {
        // Required empty public constructor
    }

    public static BehavioralProgressFragment newInstance() {
        BehavioralProgressFragment fragment = new BehavioralProgressFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_behavioral, container, false);
    }
}