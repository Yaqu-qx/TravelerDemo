package com.example.travelor.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.travelor.AttractionCollectActivity;
import com.example.travelor.LoginActivity;
import com.example.travelor.R;
import com.example.travelor.SignInActivity;

public class MineFragment extends Fragment {
    private Button btLogout;
    public ViewGroup vgCollect;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_mine, container, false);

        btLogout = rootView.findViewById(R.id.logout);
        vgCollect = rootView.findViewById(R.id.mine_collect);

        initEvent();

        return rootView;
    }

    private void initEvent() {
        btLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
            }
        });

        vgCollect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AttractionCollectActivity.class);
                startActivity(intent);
            }
        });
    }
}