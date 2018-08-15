package com.dongduk.hangeul.hangeul_test1;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

/**
 * Created by yoon1 on 2017-07-30.
 */

public class page_listing extends android.support.v4.app.Fragment{

    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        LinearLayout linearLayout = (LinearLayout)inflater.inflate(R.layout.card_listing,container,false);

        //LinearLayout background=(LinearLayout)linearLayout.findViewById(R.id.back);

        return linearLayout;
    }
}
