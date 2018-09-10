package com.example.administrator.access_school_client.Fragments.FragmentSQ;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.administrator.access_school_client.R;

public class FragmentQJ extends Fragment {
    private Button qj_button;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_qj,container,false);
        qj_button = view.findViewById(R.id.qj_button);
        qj_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "请假申请已提交", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}
