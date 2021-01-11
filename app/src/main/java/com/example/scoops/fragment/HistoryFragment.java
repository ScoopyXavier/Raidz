package com.example.scoops.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.scoops.R;
//import com.example.scoops.adapter.PostAdapter;
//import com.example.scoops.model.PostModel;
//import com.example.scoops.rest.ApiClient;
//import com.example.scoops.rest.services.UserInterface;

import butterknife.ButterKnife;

public class    HistoryFragment extends Fragment {
    Context context;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history, container, false);


        return view;
    }

}
