package com.example.aferyannie.learningapp;

import android.graphics.Color;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class ResultFragment extends Fragment{
    private Bundle bundle;

    FirebaseAuth mAuth;
    TextView txtNyemangatin;
    Button btnNext;

    // Replacing PercentFormatter with DecimalRemover function.
    public class DecimalRemover extends PercentFormatter {
        protected DecimalFormat mFormat;

        public DecimalRemover(DecimalFormat format) {
            this.mFormat = format;
        }

        @Override
        public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
            return mFormat.format(value) + "%";
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_result, null, false);

        /** Initialize Firebase Auth. */
        mAuth = FirebaseAuth.getInstance();
        /** Get Firebase CurrentUser. */
        final FirebaseUser user = mAuth.getCurrentUser();

        /** Initialize PieChart. */
        PieChart chart = view.findViewById(R.id.chart);
        chart.setDescription(null);
        chart.setHoleColor(Color.TRANSPARENT);
        chart.setTouchEnabled(false);
        chart.setDrawEntryLabels(false);

        ArrayList<PieEntry> entries = new ArrayList<>();
        // TODO: Define score manually -> supposed to take from ML.
        final double c = 7.6;
        double i = 10 - c;
        // Convert double into integer.
        // MPAndroidCharts unable to use double value, must be float or int.
        int percentageCorrect = (int) Math.ceil(c * 10);
        int percentageIncorrect = (int) Math.floor(i * 10);
        entries.add(new PieEntry(percentageCorrect, "Poin yang didapat"));
        entries.add(new PieEntry(percentageIncorrect, "Sisa poin menuju sempurna"));

        PieDataSet pieDataSet = new PieDataSet(entries, null);
        pieDataSet.setSliceSpace(3);
        pieDataSet.setColors(ColorTemplate.PASTEL_COLORS);
        pieDataSet.setValueTextSize(12);
        pieDataSet.setValueTextColor(Color.WHITE);

        PieData pieData = new PieData(pieDataSet);
        pieData.setValueFormatter(new DecimalRemover(new DecimalFormat("###,###,###")));
        chart.setData(pieData);
        chart.setCenterText(String.valueOf(percentageCorrect));
        chart.setCenterTextSize(32);

        txtNyemangatin = view.findViewById(R.id.txtNyemangatin);
        // TODO: set tulisan sesuai hasil akurasi
        // x < 5 disuru latian lagi, 5 < x < 8 disuru ditingkatkan lagi, x > 8 disuru maintain dan rajin belajar

        btnNext = view.findViewById(R.id.btnNext);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(user != null){
                    bundle = new Bundle();
                    bundle.putDouble("Skor", c);
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    NicknameDialog nicknameScore = new NicknameDialog();
                    nicknameScore.setArguments(bundle);
                    nicknameScore.show(fragmentManager, "NicknameDialog");
                } else {
                    showFragment(new HomeFragment(), R.id.fragment_container);
                }
            }
        });
        return view;
    }

    public void showFragment(Fragment fragment, int fragmentResourceID) {
        if (fragment != null) {
            FragmentManager fragmentManager = this.getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(fragmentResourceID, fragment);
            fragmentTransaction.detach(fragment);
            fragmentTransaction.attach(fragment);
            fragmentTransaction.commit();
        }
    }

}