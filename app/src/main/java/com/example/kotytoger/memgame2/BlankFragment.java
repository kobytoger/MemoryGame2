package com.example.kotytoger.memgame2;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import android.widget.TextView;

//score table
public class BlankFragment extends Fragment {

    private static TextView textView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_blank, container, false);

        textView = v.findViewById(R.id.textViewData);
        if (getArguments() == null) {
            textView.setText("sdfsdfsdf");
        } else {
            String data = getArguments().getString("data");
            textView.setText(data);
        }


        return v;
    }




}
